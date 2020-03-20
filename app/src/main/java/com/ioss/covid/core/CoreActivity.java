package com.ioss.covid.core;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;


import com.ioss.covid.R;
import com.ioss.covid.adapters.CustomAlertDialog;
import com.ioss.covid.adapters.CustomProgressDialog;
import com.ioss.covid.utilities.Constants;
import com.ioss.covid.utilities.PreferenceManager;
import com.ioss.covid.view.LoginActivity;
import com.ioss.covid.view.MemberLoginActivity;

import java.util.Locale;

public class CoreActivity extends AppCompatActivity {

    protected PreferenceManager preferenceManager;
    protected Context context;
    private CustomProgressDialog progressBar;
    private SharedPreferences basePreferences;
    private SharedPreferences.Editor baseEditor;
    private final String PREF_LOCALE = "base_locale!@";
    private boolean isHideMenuOptions=false;

    @Override
    protected void attachBaseContext(Context newBase) {

        basePreferences = newBase.getSharedPreferences(Constants.BASE_PREF_NAME, newBase.MODE_PRIVATE);
        baseEditor = basePreferences.edit();
        Locale locale = new Locale(getCurrentLocale());
        Context context = MyContextWrapper.wrap(newBase, locale);
        super.attachBaseContext(context);
    }

    @Override
    public void applyOverrideConfiguration(Configuration overrideConfiguration) {
        if (Build.VERSION.SDK_INT >= 21&& Build.VERSION.SDK_INT <= 25) {
            //Use you logic to update overrideConfiguration locale
            Locale locale =  new Locale(getCurrentLocale());//your own implementation here;
            overrideConfiguration.setLocale(locale);
        }
        super.applyOverrideConfiguration(overrideConfiguration);
    }

    public String getCurrentLocale() {
        return basePreferences.getString(PREF_LOCALE, "en");
    }

    public void setLocale(String languageCode) {
        preferenceManager.setSelectedLanguage(languageCode);
        baseEditor.putString(PREF_LOCALE,languageCode);
        baseEditor.commit();
        recreate();
    }

    protected void initViews() {
        progressBar = new CustomProgressDialog(this);
    }

    protected void setProgress(CoreViewModel viewModel){
        progressBar = new CustomProgressDialog(this);
        viewModel.getProgress().observe(this,progressObserver);
        viewModel.responseCodeLiveData.observe(this,responseCodeObserver);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        context = this;
        preferenceManager = new PreferenceManager(this);

        ViewGroup activityBaseView = this.getWindow().getDecorView().findViewById(android.R.id.content);
        activityBaseView.setOnTouchListener(onTouchOutsideListener);
    }

    public final Observer<Boolean> progressObserver = new Observer<Boolean>() {
        @Override
        public void onChanged(@Nullable Boolean isLoading) {
            if(isLoading==null)return;
            if (!isLoading) {
                hideProgress();
            } else {
                showProgress();
            }
        }
    };

    Observer<? super Integer> responseCodeObserver=new Observer<Integer>() {
        @Override
        public void onChanged(Integer code) {
            if(code == 403){
                sessionOut();
            }
        }
    };


    protected void showProgress() {
        if (progressBar != null) {
            progressBar.show();
        }
    }

    protected void hideProgress() {
        if (progressBar != null) {
            progressBar.hide();
        }
    }

    View.OnTouchListener onTouchOutsideListener=new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            hideSoftKeyboard();
            return false;
        }
    };

    public void hideSoftKeyboard() {

        try {
            if( this.getCurrentFocus().getWindowToken()==null) return;
            InputMethodManager inputMethodManager =
                    (InputMethodManager) this.getSystemService(
                            Activity.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(
                    this.getCurrentFocus().getWindowToken(), 0);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void openNextActivity(Class nextActivity){

        Intent intent=new Intent(context,nextActivity);
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        
        if(item.getItemId()==android.R.id.home) onBackPressed();
        if(item.getItemId()== R.id.action_logout){
            logout();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if(!isHideMenuOptions) {
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.menu_main, menu);
        }
        return super.onCreateOptionsMenu(menu);
    }


    protected void logout() {

        CustomAlertDialog.create(context).setMessage(getString(R.string.message_logout_confirmation))
                .setOkButton(getString(R.string.logout), new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        preferenceManager.clearSession();
                        baseEditor.clear();
                        baseEditor.commit();
                        Intent intent = new Intent(context, MemberLoginActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish();
                    }
                }).setCancelButton(getString(R.string.cancel), null).show();

    }



    private void sessionOut(){

        CustomAlertDialog.create(context).setMessage(getString(R.string.message_login_expired))
                .setCancelable(false)
                .setOkButton(getString(R.string.ok), new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        preferenceManager.clearSession();
                        baseEditor.clear();
                        baseEditor.commit();
                        Intent intent = new Intent(context, MemberLoginActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK| Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish();
                    }
                }).show();
    }

    public void setHideMenuOptions(boolean isHide){
        isHideMenuOptions=isHide;
    }

}
