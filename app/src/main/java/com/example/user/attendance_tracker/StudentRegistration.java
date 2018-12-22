package com.example.user.attendance_tracker;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.content.ContentValues.TAG;

public class StudentRegistration extends Activity {
    EditText regid,password,confirmPassword,sem,sec;
    Button register;
    Spinner SpinBranch;
    String server_url=URLs.ROOT_URL+"studentRegistration.php";
    ProgressDialog progressDialog;

ArrayAdapter BranchArrayAdapter;
List<String> BranchList=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.student_registration);
        SpinBranch=findViewById(R.id.spinnerBranch);
        BranchList.add("Computer Science");
        BranchList.add("Electrical Engineering");
        BranchList.add("Mechanical Engineering");
        BranchList.add("Electronics And Telecommunication");
        BranchList.add("Information Technology");
        BranchList.add("Civil Engineering");
        BranchList.add("Applied Electronics");
        BranchArrayAdapter=new ArrayAdapter(this,android.R.layout.simple_spinner_dropdown_item,BranchList);
        SpinBranch.setAdapter(BranchArrayAdapter);

        regid=findViewById(R.id.editRegnNo);
        password=findViewById(R.id.editPass);
        confirmPassword=findViewById(R.id.editPassre);
        sem=findViewById(R.id.editSem);
        sec=findViewById(R.id.editSec);
        register=findViewById(R.id.registerbtn);
        progressDialog= new ProgressDialog(StudentRegistration.this);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String branch=SpinBranch.getSelectedItem().toString();
                if(  TextUtils.isEmpty(regid.getText())){
                    regid.setError("Field Empty");
                    return;
                }
                if(  TextUtils.isEmpty(password.getText())){
                    password.setError("Field Empty");
                    return;
                }
                if(  TextUtils.isEmpty(confirmPassword.getText())){
                    confirmPassword.setError("Field Empty");
                    return;
                }
                if(  TextUtils.isEmpty(sem.getText())){
                    sem.setError("Field Empty");
                    return;
                }if(  TextUtils.isEmpty(sec.getText())){
                    sec.setError("Field Empty");
                    return;
                }
                if((confirmPassword.getText().toString()).equals(password.getText().toString())){
                    Log.e("matched", "password matched");

                     class Asynck extends AsyncTask<Void,Void,Void>{

                         @Override
                         protected void onPreExecute() {
                             progressDialog.setMessage("Registering , please wait!!");
                             progressDialog.show();Log.e("in pre","pre");
                         }

                         @Override
                        protected Void doInBackground(Void... voids) {
                             Log.e("in doIN","INDOIN");

                        final StringRequest stringRequest = new StringRequest(Request.Method.POST, server_url, new Response.Listener<String>() {

                            @Override
                            public void onResponse(String response) {
                                Log.e(TAG, response.toString());

                                try {
                                    JSONArray jsonArray = new JSONArray(response);
                                    JSONObject jsonObject = jsonArray.getJSONObject(0);
                                    String code = jsonObject.getString("code");
                                    progressDialog.dismiss();
                                    if (code.equals("welcome")) {
                                        Log.e("welcome restration ", "onResponse: in welcome");
                                        Intent intent = new Intent(StudentRegistration.this, MainActivity.class);
                                        startActivity(intent);

                                    } else {
                                            Log.e("not registered","error:"+code);
                                        Toast.makeText(StudentRegistration.this, code, Toast.LENGTH_SHORT).show();
                                    }


                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(StudentRegistration.this, error.toString(), Toast.LENGTH_SHORT).show();
                            }
                        }) {
                            @Override
                            protected Map<String, String> getParams() throws AuthFailureError {
                                Map<String, String> params = new HashMap<String, String>();
                                params.put("regid", regid.getText().toString());
                                params.put("password", password.getText().toString());
                                params.put("branch", branch);
                                params.put("sem", sem.getText().toString());
                                params.put("sec", sec.getText().toString());
                                return params;
                            }
                        };
                        SingletonVolley.getInstance(getApplicationContext()).addToRequestqueue(stringRequest);



                        return null;
                        }


                     }
                     Asynck asynck =new Asynck();
                     asynck.execute();


                }


                else{
                    Toast.makeText(StudentRegistration.this, "Password and confirmed password does not match", Toast.LENGTH_SHORT).show();
                    return;
                }



            }
        });

        return;
    }
}
