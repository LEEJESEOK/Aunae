package kr.co.company.aunae;

import android.content.Context;

public class SharedPrefManager {
    private static SharedPrefManager instance;
    private static Context context;

    private SharedPrefManager(Context context) {
        this.context = context;
    }

    public static synchronized SharedPrefManager getInstance(Context context) {
        if (instance == null) {
            instance = new SharedPrefManager(context);
        }
        return instance;
    }

}