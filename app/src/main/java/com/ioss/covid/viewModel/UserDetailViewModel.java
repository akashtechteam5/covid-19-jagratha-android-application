package com.ioss.covid.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.ioss.covid.core.CoreViewModel;
import com.ioss.covid.model.userDetailsModel.UserDetailItem;
import com.ioss.covid.model.userDetailsModel.UserDetailsModel;
import com.ioss.covid.utilities.retrofit.ApiRequest;
import com.ioss.covid.utilities.retrofit.RetrofitRequest;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserDetailViewModel extends CoreViewModel {

    public MutableLiveData<UserDetailItem> userDetails=new MutableLiveData<>();

    public UserDetailViewModel(@NonNull Application application) {
        super(application);
    }

/*
    public void getQuestionnaire() {

        if(userDetails == null) return;
        setLoading(true);
        HashMap<String,String> params=preferenceManager.getBaseParams();
        params.put("user_id",userDetails.getValue().getUserId());
        params.put("access-token",preferenceManager.getToken());

        ApiRequest apiRequest = RetrofitRequest.getWebRetrofitInstance().create(ApiRequest.class);
        apiRequest.questionnaire(params)
                .enqueue(new Callback<UserDetailsModel>() {
                    @Override
                    public void onResponse(Call<UserDetailsModel> call, Response<UserDetailsModel> response) {
                        setLoading(false);
                        responseCodeLiveData.setValue(response.code());
                        if (response.code() == 403) {
                            return;
                        }

//                        if(response.isSuccessful()){
//                            userDetailsResp.setValue(response.body());
//                        }else {
//                            userDetailsResp.setValue(null);
//                        }
                    }

                    @Override
                    public void onFailure(Call<UserDetailsModel> call, Throwable t) {
                        setLoading(false);
//                        userDetailsResp.setValue(null);
                    }
                });
    }
*/

}
