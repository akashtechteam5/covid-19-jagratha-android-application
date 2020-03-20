package com.ioss.covid.utilities;

import android.util.Log;

public class Constants {

    public static final boolean isLogEnabled = true;
    public static final String PREF_NAME = "covid_preference";
    public static final String BASE_PREF_NAME = "base_preference!@";
    public static final int DEFAULT_MAP_ZOOM = 15;
    public static final String CURRENCY = "$";

    public static final String URL = "http://teamioss.in/covid/";

    public static final String STATIC_KEY = "AKASHRAJATHNEBEEDNITHINBIJILABHIJITHAKHIL";


    public static void makeLog(String message) {
        if (isLogEnabled) {
            Log.e("http_", message);
        }
    }

}
