package com.example.user.attendance_tracker;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;

import static android.content.ContentValues.TAG;

public class StudentLogin extends Fragment {
    EditText studentId;
    EditText studentPass;
    Button loginBtn;
    String server_url=URLs.ROOT_URL+"studentLogin.php";
    TextView newregister;
    TextView forgetPassword;

    public  StudentLogin(){

    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.activity_student_login,container,false);

        // setContentView(R.layout.activity_teacher_login);
        studentId=view.findViewById(R.id.regNo);
        studentPass=view.findViewById(R.id.pass);
        loginBtn=view.findViewById(R.id.loginBtn);
        newregister=view.findViewById(R.id.newRegister);
        forgetPassword = view.findViewById(R.id.forgetPassword);
        forgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(),ForgetPasswordActivity.class));
            }
        });
        newregister.setOnClickListener(new View.OnClickListener(){
                                           @Override
                                           public void onClick(View view) {
                                               Intent intent = new Intent(getContext(),StudentRegistration.class);
                                               startActivity(intent);
                                           }
                                       });
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              if(  TextUtils.isEmpty(studentId.getText())){
                    studentId.setError("Field Empty");
                    return;
                }
                if(  TextUtils.isEmpty(studentPass.getText())){
                    studentPass.setError("Field Empty");
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
                                String confirmed=jsonObject.getString("confirmed");

                                String regNo=jsonObject.getString("regNo");
                                if(confirmed.equals("1")){
                                Toast.makeText(getContext(), "welcome "+regNo+" ", Toast.LENGTH_SHORT).show();
                                Intent intent=new Intent(getContext(),StudentHomeActivity.class);
                                intent.putExtra("regNo",regNo);
                                startActivity(intent);}
                                else{
                                    Toast.makeText(getContext(), "welcome "+regNo+" please activate your account", Toast.LENGTH_SHORT).show();
                                }

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
                        String username=(studentId.getText()).toString();
                        String pass=(studentPass.getText()).toString();
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
