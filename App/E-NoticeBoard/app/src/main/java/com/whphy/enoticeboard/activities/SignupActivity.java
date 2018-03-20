package com.whphy.enoticeboard.activities;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.messaging.FirebaseMessaging;
import com.whphy.enoticeboard.models.CollegeModel;
import com.whphy.enoticeboard.R;
import com.whphy.enoticeboard.utility.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Jaykishan on 26/7/2017.
 */

public class SignupActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    Spinner clgSpinner, branchSpinner;
    Button signup;
    EditText email, lname, fname, enrollment, password;
    String str_email, str_lname, str_fname, str_enrollment, str_password, str_collage, str_branch;
    CollegeModel collegeModel;
    ArrayList<CollegeModel> classArrayList = new ArrayList<>();
    MyAdapter myAdapter;
    MyBranchAdapter myBranchAdapter;
    ArrayList<String> branches = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_layout);

        getColleges();
        getBranches();

        clgSpinner = (Spinner) findViewById(R.id.spinner_clg);
        branchSpinner = (Spinner) findViewById(R.id.spinner_branch);

        signup = (Button) findViewById(R.id.btn_create_account);

        email = (EditText) findViewById(R.id.email);
        lname = (EditText) findViewById(R.id.lname);
        fname = (EditText) findViewById(R.id.fname);
        enrollment = (EditText) findViewById(R.id.enroll);
        password = (EditText) findViewById(R.id.password);


        clgSpinner.setOnItemSelectedListener(this);
        branchSpinner.setOnItemSelectedListener(this);


         myAdapter = new MyAdapter(classArrayList);
        myBranchAdapter = new MyBranchAdapter(branches);


//        ArrayAdapter branchAdapter = new ArrayAdapter(this,
//                android.R.layout.simple_spinner_item, CollegeModel.all());

//        branchAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);






        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validate()) {
                    doSignUp();

                    for (int i = 0; i <classArrayList.size() ; i++) {

                        if (classArrayList.get(i).getName().contains(clgSpinner.getSelectedItem().toString())){
                            Utils.saveCollageId(getApplicationContext(),classArrayList.get(i).getId());
                        }
                    }
                    Utils.saveBranch(getApplicationContext(),branchSpinner.getSelectedItem().toString());

                   String collegeId = Utils.getCollageId(getApplicationContext());
                    String branch = Utils.getBranch(getApplicationContext());

                    branch = branch.replace(" ", "");
                    branch = branch.replace("&", "");
                    branch = branch.replace(".", "");
                    FirebaseMessaging.getInstance().subscribeToTopic(collegeId + "_" + branch);
                    Log.e("FREBASE SWAG", "signup: "+collegeId + "_" + branch);
                }
                Log.e("DEBUG", "onClick: "+Utils.getCollageId(getApplicationContext()) );
            }
        });

    }

    private boolean validate() {
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email.getText()).matches()) {
            email.setError("Please enter valid email");
            return false;
        } else if (password.getText().toString().length() == 0) {
            password.setError("Please enter password");
            return false;
        } else if (fname.getText().toString().length() == 0) {
            fname.setError("Please enter first name");
            return false;
        } else if (lname.getText().toString().length() == 0) {
            lname.setError("Please enter last name");
            return false;
        } else if (enrollment.getText().toString().length() == 0) {
            enrollment.setError("Please enter enrollment number");
            return false;
        }
        return true;
    }

    private void doSignUp() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Utils.LOGIN,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        progressDialog.dismiss();
                        try {
                            JSONObject loginResponse = new JSONObject(response);
                            if (loginResponse.getString("success").equalsIgnoreCase("true")) {
                                Toast.makeText(getApplicationContext(), loginResponse.getString("message"), Toast.LENGTH_SHORT).show();

                                Utils.saveEnroll(getApplicationContext(),enrollment.getText().toString());
                                Utils.saveUserName(getApplicationContext(),fname.getText().toString()+" "+lname.getText().toString());

                                finish();
                            } else if (loginResponse.getString("success").equalsIgnoreCase("false")) {
                                Toast.makeText(getApplicationContext(), loginResponse.getString("message"), Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getApplicationContext(), "Something went wrong..", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(SignupActivity.this, "Error: " + error, Toast.LENGTH_SHORT).show();
                Log.e("DEBUG", "onResponse: " + error);
                progressDialog.dismiss();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("case", "signup");
                params.put("email", email.getText().toString());
                params.put("password", password.getText().toString());
                params.put("fname", fname.getText().toString());
                params.put("lname", lname.getText().toString());
                params.put("enrollment", enrollment.getText().toString());
                params.put("college", clgSpinner.getSelectedItem().toString());
                params.put("branch", branchSpinner.getSelectedItem().toString());


                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(8000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(stringRequest);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
    if (view != null) {
        switch (view.getId()) {
            case R.id.spinner_clg:
                str_collage = parent.getItemAtPosition(position).toString();
                break;
            case R.id.spinner_branch:
                str_branch = parent.getItemAtPosition(position).toString();
                break;
        }
    }
    }

    private void getColleges() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("please wait..");
        progressDialog.setCancelable(false);
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Utils.GETCOLLEGES, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                try {
                    JSONArray jsonArray = new JSONArray(response);


                    for (int i = 0; i < jsonArray.length(); i++) {
                        collegeModel = new CollegeModel();
                        JSONObject college = jsonArray.getJSONObject(i);
                        collegeModel.setId(college.getString("id"));
                        collegeModel.setName(college.getString("college"));
                        classArrayList.add(collegeModel);
                    }
                    clgSpinner.setAdapter(myAdapter);
                    myAdapter.notifyDataSetChanged();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                8000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(stringRequest);
    }

    private void getBranches() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("please wait..");
        progressDialog.setCancelable(false);
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Utils.GETBRANCHES, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                try {
                    JSONArray jsonArray = new JSONArray(response);

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject object = jsonArray.getJSONObject(i);
                        branches.add(object.getString("category"));
                    }
                    branchSpinner.setAdapter(myBranchAdapter);
                    myBranchAdapter.notifyDataSetChanged();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("SIGNUP", "onErrorResponse: "+error );
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                8000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(stringRequest);
    }


    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private class MyAdapter extends BaseAdapter implements SpinnerAdapter {


        private final ArrayList<CollegeModel> data;

        public MyAdapter(ArrayList<CollegeModel> data) {
            this.data = data;
        }


        @Override
        public int getCount() {
            return data.size();
        }


        @Override
        public Object getItem(int position) {
            return data.get(position);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int position, View recycle, ViewGroup parent) {
            TextView text;
            if (recycle != null) {
                // Re-use the recycled view here!
                text = (TextView) recycle;
            } else {
                // No recycled view, inflate the "original" from the platform:
                text = (TextView) getLayoutInflater().inflate(
                        android.R.layout.simple_dropdown_item_1line, parent, false
                );
            }
            text.setTextColor(Color.BLACK);
            text.setText(data.get(position).name);
            return text;
        }
    }

    private class MyBranchAdapter extends BaseAdapter implements SpinnerAdapter {


        private final ArrayList<String> data;

        public MyBranchAdapter(ArrayList<String> data) {
            this.data = data;
        }


        @Override
        public int getCount() {
            return data.size();
        }


        @Override
        public Object getItem(int position) {
            return data.get(position);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int position, View recycle, ViewGroup parent) {
            TextView text;
            if (recycle != null) {
                // Re-use the recycled view here!
                text = (TextView) recycle;
            } else {
                // No recycled view, inflate the "original" from the platform:
                text = (TextView) getLayoutInflater().inflate(
                        android.R.layout.simple_dropdown_item_1line, parent, false
                );
            }
            text.setTextColor(Color.BLACK);
            text.setText(data.get(position));
            return text;
        }
    }
}
