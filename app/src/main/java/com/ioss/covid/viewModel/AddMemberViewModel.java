package com.ioss.covid.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.ioss.covid.core.CoreViewModel;
import com.ioss.covid.model.chcModel.CHCModel;
import com.ioss.covid.model.districtModel.DistrictModel;
import com.ioss.covid.model.panchayathModel.PanchayathModel;
import com.ioss.covid.model.searchMemberModel.SearchUserModel;
import com.ioss.covid.model.stateModel.StateModel;
import com.ioss.covid.model.vulnerabilityModel.VulnerabilityModel;
import com.ioss.covid.utilities.retrofit.ApiRequest;
import com.ioss.covid.utilities.retrofit.RetrofitRequest;

import org.json.JSONArray;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddMemberViewModel extends CoreViewModel {

    public MutableLiveData<String> loginId=new MutableLiveData<>("");

//    public MutableLiveData<String> mobile=new MutableLiveData<>("");
    public MutableLiveData<String> mobile1=new MutableLiveData<>("");
    public MutableLiveData<String> mobile2=new MutableLiveData<>("");
    public MutableLiveData<String> mobile3=new MutableLiveData<>("");
//    public MutableLiveData<String> password=new MutableLiveData<>("");
//    public MutableLiveData<String> confirm_password=new MutableLiveData<>("");
    public MutableLiveData<String> name=new MutableLiveData<>("");
    public MutableLiveData<String> age=new MutableLiveData<>("");
    public MutableLiveData<String> gender=new MutableLiveData<>("");
    public MutableLiveData<String> state=new MutableLiveData<>("");
    public MutableLiveData<String> district=new MutableLiveData<>("");
    public MutableLiveData<String> panchayath=new MutableLiveData<>("");
    public MutableLiveData<String> chc=new MutableLiveData<>("");
    public MutableLiveData<String> address=new MutableLiveData<>("");

    public MutableLiveData<StateModel> stateResp =new MutableLiveData<>();
    public MutableLiveData<DistrictModel> districtResp =new MutableLiveData<>();
    public MutableLiveData<PanchayathModel> panchayathResp =new MutableLiveData<>();
    public MutableLiveData<CHCModel> chcResp =new MutableLiveData<>();

    public MutableLiveData<SearchUserModel> addUserResp =new MutableLiveData<>();
    public MutableLiveData<VulnerabilityModel> vulnerabilityResp=new MutableLiveData<>();

    public JSONArray vulnerabilityArray=new JSONArray();


    public AddMemberViewModel(@NonNull Application application) {
        super(application);
    }



    public void _getState() {

        setLoading(true);
        HashMap<String,String> params=preferenceManager.getBaseParams();

        ApiRequest apiRequest = RetrofitRequest.getRetrofitInstance().create(ApiRequest.class);
        apiRequest.getState(params)
                .enqueue(new Callback<StateModel>() {
                    @Override
                    public void onResponse(Call<StateModel> call, Response<StateModel> response) {
                        setLoading(false);
                        responseCodeLiveData.setValue(response.code());
                        if (response.code() == 403) {
                            return;
                        }

                        if(response.isSuccessful()){
                            stateResp.setValue(response.body());
                        }else {
                            stateResp.setValue(null);
                        }
                    }

                    @Override
                    public void onFailure(Call<StateModel> call, Throwable t) {
                        setLoading(false);
                        stateResp.setValue(null);
                    }
                });
    }

    public void _getDistrict(String state_id) {

        setLoading(true);
        HashMap<String,String> params=preferenceManager.getBaseParams();
        params.put("state_id",state_id);

        ApiRequest apiRequest = RetrofitRequest.getRetrofitInstance().create(ApiRequest.class);
        apiRequest.getDistrict(params)
                .enqueue(new Callback<DistrictModel>() {
                    @Override
                    public void onResponse(Call<DistrictModel> call, Response<DistrictModel> response) {
                        setLoading(false);
                        responseCodeLiveData.setValue(response.code());
                        if (response.code() == 403) {
                            return;
                        }

                        if(response.isSuccessful()){
                            districtResp.setValue(response.body());
                        }else {
                            districtResp.setValue(null);
                        }
                    }

                    @Override
                    public void onFailure(Call<DistrictModel> call, Throwable t) {
                        setLoading(false);
                        districtResp.setValue(null);
                    }
                });
    }

    public void _getPanchayath(String district_id) {

        setLoading(true);
        HashMap<String,String> params=preferenceManager.getBaseParams();
        params.put("district_id",district_id);

        ApiRequest apiRequest = RetrofitRequest.getRetrofitInstance().create(ApiRequest.class);
        apiRequest.getPanchayaths(params)
                .enqueue(new Callback<PanchayathModel>() {
                    @Override
                    public void onResponse(Call<PanchayathModel> call, Response<PanchayathModel> response) {
                        setLoading(false);
                        responseCodeLiveData.setValue(response.code());
                        if (response.code() == 403) {
                            return;
                        }

                        if(response.isSuccessful()){
                            panchayathResp.setValue(response.body());
                        }else {
                            panchayathResp.setValue(null);
                        }
                    }

                    @Override
                    public void onFailure(Call<PanchayathModel> call, Throwable t) {
                        setLoading(false);
                        panchayathResp.setValue(null);
                    }
                });
    }

    public void _getCHC(String district_id,String panchayat_id) {

        setLoading(true);
        HashMap<String,String> params=preferenceManager.getBaseParams();
        params.put("district_id",district_id);
        params.put("panchayat_id",panchayat_id);

        ApiRequest apiRequest = RetrofitRequest.getRetrofitInstance().create(ApiRequest.class);
        apiRequest.getCHC(params)
                .enqueue(new Callback<CHCModel>() {
                    @Override
                    public void onResponse(Call<CHCModel> call, Response<CHCModel> response) {
                        setLoading(false);
                        responseCodeLiveData.setValue(response.code());
                        if (response.code() == 403) {
                            return;
                        }

                        if(response.isSuccessful()){
                            chcResp.setValue(response.body());
                        }else {
                            chcResp.setValue(null);
                        }
                    }

                    @Override
                    public void onFailure(Call<CHCModel> call, Throwable t) {
                        setLoading(false);
                        chcResp.setValue(null);
                    }
                });
    }

    public void _getVulnerabilities() {

        setLoading(true);
        HashMap<String,String> params=preferenceManager.getBaseParams();
        ApiRequest apiRequest = RetrofitRequest.getRetrofitInstance().create(ApiRequest.class);
        apiRequest.vulnerabilities()
                .enqueue(new Callback<VulnerabilityModel>() {
                    @Override
                    public void onResponse(Call<VulnerabilityModel> call, Response<VulnerabilityModel> response) {
                        setLoading(false);
                        responseCodeLiveData.setValue(response.code());
                        if (response.code() == 403) {
                            return;
                        }
                        if(response.isSuccessful()){
                            vulnerabilityResp.setValue(response.body());
                        }else {
                            vulnerabilityResp.setValue(null);
                        }
                    }

                    @Override
                    public void onFailure(Call<VulnerabilityModel> call, Throwable t) {
                        setLoading(false);
                        vulnerabilityResp.setValue(null);
                    }
                });
    }


    public void _addUser(String login_id) {

        setLoading(true);
        HashMap<String,String> params=preferenceManager.getBaseParams();

//      params.put("mobile",mobile.getValue());
//      params.put("password",password.getValue());
//      params.put("confirm_password",confirm_password.getValue());
        params.put("name",name.getValue());
        params.put("age",age.getValue());
        params.put("gender",gender.getValue());
        params.put("state",state.getValue());
        params.put("district",district.getValue());
        params.put("panchayat",panchayath.getValue());
        params.put("chc",chc.getValue());
        params.put("address",address.getValue());
        params.put("mobile_number_1",mobile1.getValue());
        params.put("mobile_number_2",mobile2.getValue());
        params.put("mobile_number_3",mobile3.getValue());
        params.put("login_id",login_id);

        if(vulnerabilityArray.length()>0) {
            params.put("vulnerability_details", vulnerabilityArray.toString());
        }

        ApiRequest apiRequest = RetrofitRequest.getRetrofitInstance().create(ApiRequest.class);
        apiRequest.add_user(preferenceManager.getToken(),params)
                .enqueue(new Callback<SearchUserModel>() {
                    @Override
                    public void onResponse(Call<SearchUserModel> call, Response<SearchUserModel> response) {
                        setLoading(false);

                        responseCodeLiveData.setValue(response.code());

                        if (response.code() == 403) {
                            return;
                        }

                        if(response.isSuccessful()){
                            addUserResp.setValue(response.body());
                        }else {
                            addUserResp.setValue(null);
                        }
                    }

                    @Override
                    public void onFailure(Call<SearchUserModel> call, Throwable t) {
                        setLoading(false);
                        addUserResp.setValue(null);
                    }
                });
    }

}
