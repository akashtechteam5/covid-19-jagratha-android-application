package com.ioss.covid.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.ioss.covid.core.CoreViewModel;
import com.ioss.covid.model.ViewMembersModel.ViewUserModel;
import com.ioss.covid.model.userDetailsModel.UserDetailsModel;
import com.ioss.covid.utilities.retrofit.ApiRequest;
import com.ioss.covid.utilities.retrofit.RetrofitRequest;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewMemberViewModel extends CoreViewModel {

    public MutableLiveData<String> loginId=new MutableLiveData<>("");
    public MutableLiveData<ViewUserModel> viewUserResp =new MutableLiveData<>();
    public MutableLiveData<UserDetailsModel> userDetailsResp =new MutableLiveData<>();

    public ViewMemberViewModel(@NonNull Application application) {
        super(application);
    }

    public void _viewMembers() {

        setLoading(true);
        HashMap<String,String> params=preferenceManager.getBaseParams();

        ApiRequest apiRequest = RetrofitRequest.getRetrofitInstance().create(ApiRequest.class);
        apiRequest.viewMembers(preferenceManager.getToken(),params)
                .enqueue(new Callback<ViewUserModel>() {
                    @Override
                    public void onResponse(Call<ViewUserModel> call, Response<ViewUserModel> response) {
                        setLoading(false);
                        responseCodeLiveData.setValue(response.code());
                        if (response.code() == 403) {
                            return;
                        }

                      if(response.isSuccessful()){
                            viewUserResp.setValue(response.body());
                        }else {
                            viewUserResp.setValue(null);
                        }
                    }

                    @Override
                    public void onFailure(Call<ViewUserModel> call, Throwable t) {
                        setLoading(false);
                        viewUserResp.setValue(null);
                    }
                });
    }

    public void get_user_details(String user_id) {

        setLoading(true);
        HashMap<String,String> params=preferenceManager.getBaseParams();
        params.put("user_id",user_id);

        ApiRequest apiRequest = RetrofitRequest.getRetrofitInstance().create(ApiRequest.class);
        apiRequest.get_user_details(preferenceManager.getToken(),params)
                .enqueue(new Callback<UserDetailsModel>() {
                    @Override
                    public void onResponse(Call<UserDetailsModel> call, Response<UserDetailsModel> response) {
                        setLoading(false);
                        responseCodeLiveData.setValue(response.code());
                        if (response.code() == 403) {
                            return;
                        }

                      if(response.isSuccessful()){
                            userDetailsResp.setValue(response.body());
                        }else {
                            userDetailsResp.setValue(null);
                        }
                    }

                    @Override
                    public void onFailure(Call<UserDetailsModel> call, Throwable t) {
                        setLoading(false);
//                        userDetailsResp.setValue(null);
                    }
                });
    }


}
