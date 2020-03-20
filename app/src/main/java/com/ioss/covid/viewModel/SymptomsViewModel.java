package com.ioss.covid.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.ioss.covid.core.CoreViewModel;
import com.ioss.covid.model.registerModel.RegisterModel;
import com.ioss.covid.model.userDetailsModel.UserDetailItem;
import com.ioss.covid.model.userDetailsModel.UserDetailsModel;
import com.ioss.covid.utilities.retrofit.ApiRequest;
import com.ioss.covid.utilities.retrofit.RetrofitRequest;

import org.json.JSONArray;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SymptomsViewModel extends CoreViewModel {

    public MutableLiveData<UserDetailItem> userDetails=new MutableLiveData<>();
    public MutableLiveData<UserDetailsModel> submitSymptomRep=new MutableLiveData<>();

    public SymptomsViewModel(@NonNull Application application) {
        super(application);
    }

    public void update_user_symptoms(JSONArray symptonArray) {

        if(userDetails == null) return;
        setLoading(true);
        HashMap<String,String> params=preferenceManager.getBaseParams();
        params.put("answers",symptonArray.toString());
      params.put("user_id",userDetails.getValue().getUserId());

        ApiRequest apiRequest = RetrofitRequest.getRetrofitInstance().create(ApiRequest.class);
        apiRequest.update_user_symptoms(preferenceManager.getToken(),params)
                .enqueue(new Callback<UserDetailsModel>() {
                    @Override
                    public void onResponse(Call<UserDetailsModel> call, Response<UserDetailsModel> response) {
                        setLoading(false);
                        responseCodeLiveData.setValue(response.code());
                        if (response.code() == 403) {
                            return;
                        }

                        if(response.isSuccessful()){
                            submitSymptomRep.setValue(response.body());
                        }else {
                            submitSymptomRep.setValue(null);
                        }
                    }

                    @Override
                    public void onFailure(Call<UserDetailsModel> call, Throwable t) {
                        setLoading(false);
                        submitSymptomRep.setValue(null);
                    }
                });
    }

}
