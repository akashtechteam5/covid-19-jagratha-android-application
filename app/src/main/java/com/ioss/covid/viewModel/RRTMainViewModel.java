package com.ioss.covid.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.ioss.covid.core.CoreViewModel;
import com.ioss.covid.model.ViewMembersModel.ViewUserModel;
import com.ioss.covid.model.searchMemberModel.SearchUserModel;
import com.ioss.covid.utilities.retrofit.ApiRequest;
import com.ioss.covid.utilities.retrofit.RetrofitRequest;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RRTMainViewModel extends CoreViewModel {

    public MutableLiveData<String> mobile=new MutableLiveData<>("");
    public MutableLiveData<SearchUserModel> searchUserResp =new MutableLiveData<>();
//    public MutableLiveData<String> loginId=new MutableLiveData<>("");

    public RRTMainViewModel(@NonNull Application application) {
        super(application);
    }

    public void search_user() {

        setLoading(true);
        HashMap<String,String> params=preferenceManager.getBaseParams();
        params.put("mobile",mobile.getValue());

        ApiRequest apiRequest = RetrofitRequest.getRetrofitInstance().create(ApiRequest.class);
        apiRequest.search_user(preferenceManager.getToken(),params)
                .enqueue(new Callback<SearchUserModel>() {
                    @Override
                    public void onResponse(Call<SearchUserModel> call, Response<SearchUserModel> response) {
                        setLoading(false);
                        responseCodeLiveData.setValue(response.code());
                        if (response.code() == 403) {
                            return;
                        }

                        if(response.isSuccessful()){
                            searchUserResp.setValue(response.body());
                        }else {
                            searchUserResp.setValue(null);
                        }
                    }

                    @Override
                    public void onFailure(Call<SearchUserModel> call, Throwable t) {
                        setLoading(false);
                        searchUserResp.setValue(null);
                    }
                });
    }

}
