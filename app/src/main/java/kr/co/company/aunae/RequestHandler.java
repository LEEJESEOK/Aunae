package kr.co.company.aunae;

import android.content.Context;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

class RequestHandler {
    private static RequestHandler instance;
    private static Context mContext;
    private RequestQueue requestQueue;

    private RequestHandler(Context context) {
        mContext = context;
        requestQueue = getRequestQueue();
    }

    static synchronized RequestHandler getInstance(Context context) {
        if (instance == null) {
            instance = new RequestHandler(context);
        }

        return instance;
    }

    private RequestQueue getRequestQueue() {
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(mContext.getApplicationContext());
        }

        return requestQueue;
    }

    <T> void addToRequestQueue(Request<T> request) {
        getRequestQueue().add(request);
    }
}
