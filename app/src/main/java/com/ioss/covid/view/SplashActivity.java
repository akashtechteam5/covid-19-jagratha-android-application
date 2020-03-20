package com.ioss.covid.view;

import android.os.Bundle;
import com.ioss.covid.R;
import com.ioss.covid.core.CoreActivity;

public class SplashActivity extends CoreActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        initViews();

        openNextPage();
    }


    private void openNextPage(){

        if(preferenceManager.isUserLogged()){
            if(preferenceManager.getUserType().equalsIgnoreCase("RRT")){
                openNextActivity(RRTMainActivity.class);
            }else {
                openNextActivity(ViewMembersActivity.class);
            }
            finish();
        }else {
            openNextActivity(MemberLoginActivity.class);
            finish();
        }
    }


}
