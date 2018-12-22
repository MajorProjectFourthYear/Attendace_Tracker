package com.example.user.attendance_tracker;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by USER on 05-09-2018.
 */

public class SingletonVolley {
    private static SingletonVolley mInstance;
    private RequestQueue requestQueue;
    private static Context mcontext;

    private  SingletonVolley(Context context){
        mcontext=context;
        requestQueue=getRequestQueue();
    }

    public RequestQueue getRequestQueue()
    {
        if(requestQueue==null)
        {
            requestQueue = Volley.newRequestQueue(mcontext.getApplicationContext());

        }
        return requestQueue;
    }
   public static synchronized SingletonVolley getInstance(Context context)
   {
       if(mInstance==null){
           mInstance=new SingletonVolley(context);
       }
       return mInstance;
   }

   public <T>void addToRequestqueue(Request<T> request){
        requestQueue.add(request);
   }
}
