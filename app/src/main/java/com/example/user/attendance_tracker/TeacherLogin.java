package com.example.user.attendance_tracker;

import android.app.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.zip.Inflater;

import static android.content.ContentValues.TAG;

public class TeacherLogin extends Fragment {
    EditText teacherId;
    EditText teacherPass;
    Button loginBtn;
    RequestQueue myQueue;
String server_url=URLs.ROOT_URL+"teacherLogin.php";
public TeacherLogin(){}

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.activity_teacher_login,container,false);

       // setContentView(R.layout.activity_teacher_login);
        teacherId=view.findViewById(R.id.regNo);
        teacherPass=view.findViewById(R.id.pass);
        loginBtn=view.findViewById(R.id.loginBtn);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(  TextUtils.isEmpty(teacherId.getText())){
                    teacherId.setError("Field Empty");
                    return;
                }
                if(  TextUtils.isEmpty(teacherPass.getText())){
                    teacherPass.setError("Field Empty");
                    return;
                }
               // final RequestQueue requestQueue= Volley.newRequestQueue(TeacherLogin.this);
                final StringRequest stringRequest=new StringRequest(Request.Method.POST, server_url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                      Log.d(TAG, response.toString());
                        try {
                            JSONArray jsonArray=new JSONArray(response);
                            JSONObject jsonObject=jsonArray.getJSONObject(0);
                            String code=jsonObject.getString("code");
                            if(code.equals("successfull")){
                                String empId=jsonObject.getString("empId");
                                Toast.makeText(getContext(), "welcome "+empId+" ", Toast.LENGTH_SHORT).show();
                                Intent intent=new Intent(getContext(),TeacherHomeActivity.class);
                                intent.putExtra("empId",empId);
                                startActivity(intent);

                            }
                            else {

                                Toast.makeText(getContext(), code, Toast.LENGTH_SHORT).show();
                                //requestQueue.stop();}
                            }
                        }catch (JSONException e){e.printStackTrace();}

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getContext(),error.toString(), Toast.LENGTH_SHORT).show();
                       // requestQueue.stop();
                    }
                }
                ){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String,String>params=new HashMap<String,String>();
                        String username=(teacherId.getText()).toString();
                        String pass=(teacherPass.getText()).toString();
                        params.put("username",username);
                        Log.d(TAG,"username"+ username); Log.d(TAG,"password"+ pass);
                        params.put("password",pass);
                        return  params;
                    }
                };
              SingletonVolley.getInstance(getActivity().getApplicationContext()).addToRequestqueue(stringRequest);

            }
        });
   return view;
    }
}
