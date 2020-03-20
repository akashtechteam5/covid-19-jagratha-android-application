package com.ioss.covid.viewModel;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.ioss.covid.core.CoreViewModel;
import com.ioss.covid.model.loginModel.LoginModel;
import com.ioss.covid.utilities.retrofit.ApiRequest;
import com.ioss.covid.utilities.retrofit.RetrofitRequest;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginViewModel extends CoreViewModel {

    public MutableLiveData<String> userName=new MutableLiveData<>("");
    public MutableLiveData<String> password=new MutableLiveData<>("");
    public MutableLiveData<LoginModel> loginResp=new MutableLiveData<>();
    public LoginViewModel(@NonNull Application application) {
        super(application);
    }

    public void _login() {

        setLoading(true);
        HashMap<String,String> params=preferenceManager.getBaseParams();
        params.put("username",userName.getValue());
        params.put("password",password.getValue());

        ApiRequest apiRequest = RetrofitRequest.getRetrofitInstance().create(ApiRequest.class);
        apiRequest.loginRRT(params)
                .enqueue(new Callback<LoginModel>() {
                    @Override
                    public void onResponse(Call<LoginModel> call, Response<LoginModel> response) {
                        setLoading(false);
                        responseCodeLiveData.setValue(response.code());
                        if (response.code() == 403) {
                            return;
                        }

                        if(response.isSuccessful()){
                            loginResp.setValue(response.body());
                        }else {
                            loginResp.setValue(null);
                        }
                    }

                    @Override
                    public void onFailure(Call<LoginModel> call, Throwable t) {
                        setLoading(false);
                        loginResp.setValue(null);
                    }
                });
    }
}
