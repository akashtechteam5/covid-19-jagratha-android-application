package com.ioss.covid.view;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ioss.covid.R;
import com.ioss.covid.adapters.CustomSpinner.CustomSpinnerAdapter;
import com.ioss.covid.adapters.CustomSpinner.SpinnerItem;
import com.ioss.covid.core.AppConfig;
import com.ioss.covid.core.CoreActivity;
import com.ioss.covid.databinding.ActivityAddMemberBinding;
import com.ioss.covid.model.ViewMembersModel.MemberItem;
import com.ioss.covid.model.chcModel.CHCModel;
import com.ioss.covid.model.chcModel.Chc;
import com.ioss.covid.model.districtModel.District;
import com.ioss.covid.model.districtModel.DistrictModel;
import com.ioss.covid.model.panchayathModel.Panchayath;
import com.ioss.covid.model.panchayathModel.PanchayathModel;
import com.ioss.covid.model.searchMemberModel.SearchUserModel;
import com.ioss.covid.model.stateModel.State;
import com.ioss.covid.model.stateModel.StateModel;
import com.ioss.covid.model.vulnerabilityModel.VulnerabilityItem;
import com.ioss.covid.model.vulnerabilityModel.VulnerabilityModel;
import com.ioss.covid.utilities.Constants;
import com.ioss.covid.utilities.Utility;
import com.ioss.covid.utilities.Validation;
import com.ioss.covid.viewModel.AddMemberViewModel;

import org.json.JSONArray;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class AddMemberActivity extends CoreActivity {

    private AddMemberViewModel viewModel;
    private ActivityAddMemberBinding binding;
    private CustomSpinnerAdapter stateAdapter, districtAdapter, panchayathAdapter, phcAdapter;

    private List<CheckBox> vulnerabilityBoxList=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bindView();
        getSupportActionBar().setTitle(getString(R.string.add_member));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        animateView();
        setGenderAdapter();
        setStateAdapter();
        setDistrictAdapter();
        setPanchayathAdapter();
        setPHCAdapter();
        Utility.setFonts(AppConfig.openSansLight, binding.addBT);

        if (getIntent().hasExtra("loginId")) {
            viewModel.loginId.setValue(getIntent().getStringExtra("loginId"));
        }

        viewModel._getState();

        viewModel.stateResp.observe(this, stateObserver);
        viewModel.districtResp.observe(this, districtObserver);
        viewModel.panchayathResp.observe(this, panchayathObserver);
        viewModel.chcResp.observe(this, chcObserver);
        viewModel.addUserResp.observe(this, addUserRespObserver);
        viewModel.vulnerabilityResp.observe(this, vulnerabilityRespObserver);

        binding.stateSP.setOnItemSelectedListener(stateSelectioListener);
        binding.districtSP.setOnItemSelectedListener(districtSelectioListener);
        binding.panchayathSP.setOnItemSelectedListener(panchayathSelectioListener);


        binding.valnrbilityRG.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId==R.id.yesRB){
                    if(viewModel.vulnerabilityResp.getValue() == null) {
                        viewModel._getVulnerabilities();
                    }
                        binding.valnrbilityLL.setVisibility(View.VISIBLE);
                }else {
                    binding.valnrbilityLL.setVisibility(View.GONE);
                }
            }
        });
    }

    private void bindView() {
        viewModel = ViewModelProviders.of(this).get(AddMemberViewModel.class);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_member);
        binding.setLifecycleOwner(this);
        binding.setViewModel(viewModel);
        setProgress(viewModel);
    }

    Observer<? super StateModel> stateObserver = new Observer<StateModel>() {
        @Override
        public void onChanged(StateModel stateModel) {
            if (stateModel == null) return;
            if (stateModel.getStatus()) {
                stateAdapter.clear();
                stateAdapter.addItem(new SpinnerItem(getString(R.string.select_state), "")).hasTitle(true);
                for (int i = 0; i < stateModel.getData().getStates().size(); i++) {
                    State state = stateModel.getData().getStates().get(i);
                    stateAdapter.addItem(new SpinnerItem(state.getStateName(), state.getStateId()));
                }
            }
        }
    };

    Observer<? super VulnerabilityModel> vulnerabilityRespObserver=new Observer<VulnerabilityModel>() {
        @Override
        public void onChanged(VulnerabilityModel vulnerabilityModel) {
            if(vulnerabilityModel == null) return;
            if(vulnerabilityModel.getStatus()){

                for (int i = 0; i < vulnerabilityModel.getData().size(); i++) {

                    VulnerabilityItem item=vulnerabilityModel.getData().get(i);

                    CheckBox checkBox = new CheckBox(context);
                    checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                        }
                    });
                    checkBox.setId(i);
                    checkBox.setTag(item.getId());
                    checkBox.setText(item.getName());
                    binding.valnrbilityLL.addView(checkBox);
                    vulnerabilityBoxList.add(checkBox);
                }
            }

        }
    };


    Observer<? super DistrictModel> districtObserver = new Observer<DistrictModel>() {
        @Override
        public void onChanged(DistrictModel districtModel) {
            if (districtModel == null) return;

            if (districtModel.getStatus()) {
                districtAdapter.clear();
                districtAdapter.addItem(new SpinnerItem(getString(R.string.select_district), "")).hasTitle(true);
                for (int i = 0; i < districtModel.getData().getDistricts().size(); i++) {
                    District dist = districtModel.getData().getDistricts().get(i);
                    districtAdapter.addItem(new SpinnerItem(dist.getDistrictName(), dist.getDistrictId()));
                }
            }
        }
    };

    Observer<? super PanchayathModel> panchayathObserver = new Observer<PanchayathModel>() {
        @Override
        public void onChanged(PanchayathModel panchayathModel) {
            if (panchayathModel == null) return;

            if (panchayathModel.getStatus()) {
                panchayathAdapter.clear();
                panchayathAdapter.addItem(new SpinnerItem(getString(R.string.select_villege), "")).hasTitle(true);
                for (int i = 0; i < panchayathModel.getData().getPanchayath().size(); i++) {
                    Panchayath panch = panchayathModel.getData().getPanchayath().get(i);
                    panchayathAdapter.addItem(new SpinnerItem(panch.getPanchayatName(), panch.getPanchayatId()));
                }
            }
        }
    };

    Observer<? super CHCModel> chcObserver = new Observer<CHCModel>() {
        @Override
        public void onChanged(CHCModel chcModel) {
            if (chcModel == null) return;

            if (chcModel.getStatus()) {
                phcAdapter.clear();
                phcAdapter.addItem(new SpinnerItem(getString(R.string.select_chc), "")).hasTitle(true);
                for (int i = 0; i < chcModel.getData().getChc().size(); i++) {
                    Chc chc = chcModel.getData().getChc().get(i);
                    phcAdapter.addItem(new SpinnerItem(chc.getChcName(), chc.getChcId()));
                }
            }
        }
    };

    Observer<? super SearchUserModel> addUserRespObserver = new Observer<SearchUserModel>() {
        @Override
        public void onChanged(SearchUserModel model) {
            if (model == null) {
                Toast.makeText(context, getString(R.string.message_err_internal_error), Toast.LENGTH_SHORT).show();
                return;
            }

            if(model.getStatus()){
//                viewModel.loginId.setValue(searchUserModel.getData().getLoginId());
                Intent intent=new Intent();

                Gson gson = new Gson();
                Type type = new TypeToken<List<MemberItem>>() {}.getType();
                String data = gson.toJson(model.getData().getUsers(), type);
                if(data.length()>0) {
                    intent.putExtra("data", data);
                }
//                intent.putExtra("loginId",model.getData().getLoginId());
//                startActivity(intent);
                setResult(RESULT_OK,intent);
                finish();
            }else {
                Toast.makeText(context, model.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    };


    AdapterView.OnItemSelectedListener stateSelectioListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            SpinnerItem item = (SpinnerItem) binding.stateSP.getSelectedItem();
            viewModel._getDistrict(item.getId());
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };

    AdapterView.OnItemSelectedListener districtSelectioListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            SpinnerItem item = (SpinnerItem) binding.districtSP.getSelectedItem();
            viewModel._getPanchayath(item.getId());
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };

    AdapterView.OnItemSelectedListener panchayathSelectioListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            SpinnerItem distritem = (SpinnerItem) binding.districtSP.getSelectedItem();

            SpinnerItem panchitem = (SpinnerItem) binding.districtSP.getSelectedItem();

            viewModel._getCHC(distritem.getId(), panchitem.getId());
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };

    private void setGenderAdapter() {
        CustomSpinnerAdapter genderAdapter = new CustomSpinnerAdapter(context)
                .addItem(new SpinnerItem(getString(R.string.select_gender), "")).hasTitle(true);

        genderAdapter
                .addItem(new SpinnerItem("Male", "male"))
                .addItem(new SpinnerItem("Female", "female"))
                .addItem(new SpinnerItem("Other", "other"));

        binding.genderSP.setAdapter(genderAdapter);
    }

    private void setStateAdapter() {
        stateAdapter = new CustomSpinnerAdapter(context)
                .addItem(new SpinnerItem(getString(R.string.select_state), "")).hasTitle(true);

        binding.stateSP.setAdapter(stateAdapter);
    }


    private void setDistrictAdapter() {
        districtAdapter = new CustomSpinnerAdapter(context)
                .addItem(new SpinnerItem(getString(R.string.select_district), "")).hasTitle(true);

        binding.districtSP.setAdapter(districtAdapter);
    }

    private void setPanchayathAdapter() {
        panchayathAdapter = new CustomSpinnerAdapter(context)
                .addItem(new SpinnerItem(getString(R.string.select_villege), "")).hasTitle(true);

        binding.panchayathSP.setAdapter(panchayathAdapter);
    }

    private void setPHCAdapter() {

        phcAdapter = new CustomSpinnerAdapter(context)
                .addItem(new SpinnerItem(getString(R.string.select_chc), "")).hasTitle(true);

        binding.chcSP.setAdapter(phcAdapter);
    }

    private void animateView() {

        TranslateAnimation animateButton = new TranslateAnimation(
                0,                 // fromXDelta
                0,                 // toXDelta
                200,  // fromYDelta
                binding.addBT.getHeight());                // toYDelta

        animateButton.setDuration(2000);
        animateButton.setFillAfter(false);
        binding.addBT.startAnimation(animateButton);

    }

    public void onClickRegister(View view) {
        if (validation()) {
            viewModel._addUser(viewModel.loginId.getValue());
        }
    }

    private boolean validation() {

        Validation validation = new Validation(context);
//        if(!validation.isMobileValid( binding.mobileET,viewModel.mobile.getValue())) return false;
//        if(!validation.isEmptyValidation(binding.passwordET,viewModel.password.getValue(),getString(R.string.message_err_password_empty))) return false;
//        if(!validation.isConfirmPwdValid(binding.confirmPasswordET,viewModel.password.getValue(),viewModel.confirm_password.getValue())) return false;
        if (!validation.isNameValid(binding.nameET, viewModel.name.getValue())) return false;
        if (!validation.isEmptyValidation(binding.ageET, viewModel.age.getValue(), getString(R.string.message_err_age_empty)))
            return false;

        if (binding.genderSP.getSelectedItemPosition() == 0) {
            Toast.makeText(context, getString(R.string.select_gender), Toast.LENGTH_SHORT).show();
            return false;

        } else {
            viewModel.gender.setValue(((SpinnerItem) binding.genderSP.getSelectedItem()).getId());
        }

        if (binding.stateSP.getSelectedItemPosition() == 0) {
            Toast.makeText(context, getString(R.string.select_state), Toast.LENGTH_SHORT).show();
            return false;

        } else {
            viewModel.state.setValue(((SpinnerItem) binding.stateSP.getSelectedItem()).getId());
        }

        if (binding.districtSP.getSelectedItemPosition() == 0) {
            Toast.makeText(context, getString(R.string.select_district), Toast.LENGTH_SHORT).show();
            return false;

        } else {
            viewModel.district.setValue(((SpinnerItem) binding.districtSP.getSelectedItem()).getId());
        }

        if (binding.panchayathSP.getSelectedItemPosition() == 0) {
            Toast.makeText(context, getString(R.string.select_villege), Toast.LENGTH_SHORT).show();
            return false;

        } else {
            viewModel.panchayath.setValue(((SpinnerItem) binding.panchayathSP.getSelectedItem()).getId());
        }

        viewModel.chc.setValue(((SpinnerItem) binding.chcSP.getSelectedItem()).getId());
      /*  if(binding.chcSP.getSelectedItemPosition()==0){

        }else {
            viewModel.chc.setValue(((SpinnerItem)binding.chcSP.getSelectedItem()).getId());
        }*/
        if (!validation.isEmptyValidation(binding.addressET, viewModel.address.getValue(), getString(R.string.message_err_address_empty)))
            return false;

        if(binding.yesRB.isChecked()){

            for(int i=0;i<vulnerabilityBoxList.size();i++){
                CheckBox checkBox=vulnerabilityBoxList.get(i);
                if(checkBox.isChecked()){
                    viewModel.vulnerabilityArray.put(checkBox.getTag());
                }
            }

            if(viewModel.vulnerabilityArray.length() == 0){
                Toast.makeText(context, "Please mark atleast one vulnerability", Toast.LENGTH_SHORT).show();
                return false;
            }
        }else {
            viewModel.vulnerabilityArray=new JSONArray();
        }

        return true;
    }
}
