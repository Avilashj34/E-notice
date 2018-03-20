package com.whphy.enoticeboard.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.whphy.enoticeboard.R;
import com.whphy.enoticeboard.utility.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Jaykishan on 28/7/2017.
 */

public class ChangePasswordActivity extends AppCompatActivity {

    EditText email,oldPass,newPass;
    Button changePass;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.change_password_activity);

        email = (EditText)findViewById(R.id.email_change);
        oldPass = (EditText)findViewById(R.id.pass_change);
        newPass = (EditText)findViewById(R.id.newpass_change);

        changePass = (Button)findViewById(R.id.btn_changepass);
        changePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changePass();
            }
        });

        getSupportActionBar().setTitle("Change Password");
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void changePass() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("please wait..");
        progressDialog.setCancelable(false);
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Utils.CHANGEPASS, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                try {

                    JSONObject resultObject = new JSONObject(response);
                    if (resultObject.getString("success").equals("true")) {

                        Toast.makeText(getApplicationContext(), resultObject.getString("message"), Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(ChangePasswordActivity.this, LoginActivity.class));
                        Toast.makeText(getApplicationContext(),"Login Again", Toast.LENGTH_SHORT).show();
                        Utils.saveUserId(getApplicationContext(),"null");
                        MainActivity.ma.finish();
                        finish();
                    } else {
                        Utils.showErrorDialog(ChangePasswordActivity.this, "Operation failed", resultObject.getString("message"));
                    }

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
                params.put("email", email.getText().toString());
                params.put("password", oldPass.getText().toString());
                params.put("newpassword", newPass.getText().toString());
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
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
