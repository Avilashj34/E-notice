package com.whphy.enoticeboard.activities;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.messaging.FirebaseMessaging;
import com.whphy.enoticeboard.DBAdapter;
import com.whphy.enoticeboard.NotifAdapter;
import com.whphy.enoticeboard.models.NotifModel;
import com.whphy.enoticeboard.R;
import com.whphy.enoticeboard.utility.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    ArrayList<NotifModel> classArrayList = new ArrayList<>();
    RecyclerView notifList;
    NotifAdapter notifAdapter;
    String collegeId, branch;
    LinearLayout emptyLayout;
    DBAdapter dbAdapter;
    static MainActivity ma;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        emptyLayout = (LinearLayout) findViewById(R.id.empty_layout);
        emptyLayout.setVisibility(View.GONE);

        ma = this;
        dbAdapter = new DBAdapter(getApplicationContext());

        collegeId = Utils.getCollageId(getApplicationContext());
        branch = Utils.getBranch(getApplicationContext());

        branch = branch.replace(" ", "");
        branch = branch.replace("&", "");
        branch = branch.replace(".", "");


        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.action_title);

        if (collegeId.equals("null")) {
            getInfo();
        } else {


            Log.e("SUB", "onCreate: " + collegeId + "_" + branch);
        }


        dbAdapter = new DBAdapter(this);
        classArrayList = dbAdapter.getNotifList();
        Collections.reverse(classArrayList);

        if (classArrayList.size() == 0) {
            emptyLayout.setVisibility(View.VISIBLE);
        }

        Log.e("DEBUG SIZE", "" + classArrayList.size());

        notifAdapter = new NotifAdapter(this, classArrayList);

        notifList = (RecyclerView) findViewById(R.id.notif_list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        linearLayoutManager.scrollToPosition(0);
        notifList.setLayoutManager(linearLayoutManager);

        notifList.setAdapter(notifAdapter);

    }

    private void getInfo() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("please wait..");
        progressDialog.setCancelable(false);
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Utils.GETCOLLEGESBYNAME, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    JSONObject object = jsonArray.getJSONObject(0);
                    Utils.saveCollageId(getApplicationContext(), object.getString("id"));
                    Utils.saveBranch(getApplicationContext(), object.getString("branch"));

                    Utils.saveEnroll(getApplicationContext(),object.getString("enroll"));
                    Utils.saveUserName(getApplicationContext(),object.getString("fname")+" "+object.getString("lname"));


                    collegeId = Utils.getCollageId(getApplicationContext());
                    branch = Utils.getBranch(getApplicationContext());

                    branch = branch.replace(" ", "");
                    branch = branch.replace("&", "");
                    branch = branch.replace(".", "");

                    Log.e("FREBASE SWAG", "onResponse: "+collegeId + "_" + branch);
                    FirebaseMessaging.getInstance().subscribeToTopic(collegeId + "_" + branch);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("uid", Utils.getUserId(getApplicationContext()));
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                8000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(stringRequest);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menufile, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.logout:

                AlertDialog.Builder builder = new AlertDialog.Builder(this)
                        .setTitle("Logout")
                        .setMessage("Are you sure you wanna Logout?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(getApplicationContext(), "Logged Out!", Toast.LENGTH_SHORT).show();
                                Utils.logOut(getApplicationContext());
                                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                                dbAdapter.clear();
                                finish();
                            }
                        }).setNegativeButton("No", null);
                builder.create();
                builder.show();


                break;

            case R.id.changepass:
                startActivity(new Intent(this, ChangePasswordActivity.class));


                break;
            case R.id.feedback:
                startActivity(new Intent(this, SendFeedbackActivity.class));
                break;
        }

        return true;
    }
}
