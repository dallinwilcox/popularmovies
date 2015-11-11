package com.dallinwilcox.popularmovies.inf;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by dcwilcox on 11/10/2015.
 * reference http://developer.android.com/training/volley/requestqueue.html
 */
public class RequestManager {
    private static RequestManager instance;
    private RequestQueue requestQueue;
    private static Context context;


    private RequestManager(Context context) {
        //only store a reference to applicationContext
        this.context = context.getApplicationContext();
        requestQueue = getRequestQueue();
    }

    public static synchronized RequestManager getInstance(Context context) {
        if (null == instance) {
            instance = new RequestManager(context);
        }
        return instance;
    }

    public RequestQueue getRequestQueue() {
        if (null == requestQueue) {
            requestQueue = Volley.newRequestQueue(context);
        }
        return requestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req) {
        getRequestQueue().add(req);
    }
}