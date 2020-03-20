package com.ioss.covid.view;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.ioss.covid.R;
import com.ioss.covid.core.AppConfig;
import com.ioss.covid.core.CoreActivity;
import com.ioss.covid.databinding.ActivityLoginBinding;
import com.ioss.covid.model.loginModel.LoginModel;
import com.ioss.covid.utilities.Utility;
import com.ioss.covid.utilities.Validation;
import com.ioss.covid.viewModel.LoginViewModel;

public class LoginActivity extends CoreActivity {

    private LoginViewModel viewModel;
    private ActivityLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bindView();

        Observer<? super LoginModel> loginObserver=new Observer<LoginModel>() {
            @Override
            public void onChanged(LoginModel model) {
                if(model==null){
                    return;
                }
                if(model.getStatus()){
                    Intent intent=new Intent(context,RRTMainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    preferenceManager.setUserType("RRT");
                    preferenceManager.setToken(model.getData().getAccessToken());
                    startActivity(intent);
                }else {
                    Toast.makeText(context,model.getMessage(),Toast.LENGTH_SHORT).show();
                }
            }
        };
        viewModel.loginResp.observe(this,loginObserver);
    }

    private void bindView() {
        viewModel = ViewModelProviders.of(this).get(LoginViewModel.class);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        binding.setLifecycleOwner(this);
        binding.setViewModel(viewModel);
        setProgress(viewModel);
        Utility.setFonts(AppConfig.openSansRegular,binding.userNameTIL,binding.passwordTIL);
        Utility.setFonts(AppConfig.openSansLight,binding.loginBT);
    }

    public void onClickLogin(View view) {
        if(validation()){
            viewModel._login();
        }
    }
    private boolean validation() {

        Validation validation=new Validation(context);

        if(!validation.isEmptyValidation( binding.userNameTIL,viewModel.userName.getValue(),getString(R.string.message_err_username_empty))) return false;
        if(!validation.isEmptyValidation(binding.passwordTIL,viewModel.password.getValue(),getString(R.string.message_err_password_empty))) return false;
        return true;
    }


}
