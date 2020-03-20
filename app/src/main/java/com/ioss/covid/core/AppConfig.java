package com.ioss.covid.core;

import android.content.Context;
import android.graphics.Typeface;

import androidx.multidex.MultiDex;
import androidx.multidex.MultiDexApplication;

public class AppConfig extends MultiDexApplication {

    public static Typeface openSansRegular, openSansLight, openSansSemiBold, openSansBold;
    @Override
    public void onCreate() {
        super.onCreate();

        openSansRegular = Typeface.createFromAsset(getAssets(), "fonts/OpenSans-Regular.ttf");
        openSansLight = Typeface.createFromAsset(getAssets(), "fonts/OpenSans-Light.ttf");
        openSansSemiBold = Typeface.createFromAsset(getAssets(), "fonts/OpenSans-Semibold.ttf");
        openSansBold = Typeface.createFromAsset(getAssets(), "fonts/OpenSans-Bold.ttf");
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}
