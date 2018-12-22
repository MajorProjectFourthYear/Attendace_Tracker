package com.example.user.attendance_tracker;

import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import static android.content.ContentValues.TAG;

public class ForgetPasswordActivity extends AppCompatActivity {
    EditText regNo;
    Button reset;
    String server_url = URLs.ROOT_URL+"foreget_password.php";
    Context context;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        regNo = findViewById(R.id.regNo);
        reset = findViewById(R.id.sendResetLink);
       // String urlCheck = serverAddress.concat("/admin/checkStatus.php");
        context = this;
        progressDialog = new ProgressDialog(this);
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                    class Asynck extends AsyncTask<Void,Void,Void>{

                        @Override
                        protected void onPreExecute() {
                            progressDialog.setMessage("Sending Reset Link!");
                            progressDialog.show();

                        }

                        @Override
                        protected Void doInBackground(Void... voids) {
                            if(TextUtils.isEmpty(regNo.getText()))
                            {
                                regNo.requestFocus();
                                regNo.setError("Please Provide Your Registration Id!");
                        }else {
                        final String registrationNumber = regNo.getText().toString();
                    Log.e("Coming for","params");
                    Map<String,String> params = new HashMap<>();
                    params.put("reg_id",registrationNumber);
                    JSONObject parameters = new JSONObject(params);
                    Log.e("PARAMS",params.get("reg_id"));
                    final StringRequest request =new StringRequest(Request.Method.POST, server_url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                progressDialog.dismiss();

                                JSONObject jsonObject = new JSONObject(response);
                                if(!jsonObject.getBoolean("error"))
                                {
                                    Log.e("ERROR : FALSE",jsonObject.getString("msg"));
                                    String message = jsonObject.getString("msg");
                                    Toast.makeText(getApplicationContext(),message,Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(getApplicationContext(),ShowReset.class));
                                    finish();
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }


                        }
                    }, new Response.ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError error) {

                            progressDialog.dismiss();

                            Toast.makeText(getApplicationContext(),error.toString(), Toast.LENGTH_SHORT).show();
                            // requestQueue.stop();
                        }
                    }
                    ){
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String,String>params=new HashMap<String,String>();
                            params.put("reg_id",registrationNumber);
                            return  params;
                        }
                    };
                    //SingletonVolley.getInstance(getApplicationContext()).addToRequestqueue(stringRequest);
                                request.setRetryPolicy(new DefaultRetryPolicy(90002, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                    SingletonVolley.getInstance(context).addToRequestqueue(request);}
                            return null;
                        }
                }
                Asynck asynck = new Asynck();
                    asynck.execute();
            }});
        }

    }

