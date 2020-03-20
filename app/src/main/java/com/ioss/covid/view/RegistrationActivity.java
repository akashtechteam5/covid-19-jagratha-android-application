package com.ioss.covid.view;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.Toast;
import com.ioss.covid.R;
import com.ioss.covid.adapters.CustomSpinner.CustomSpinnerAdapter;
import com.ioss.covid.adapters.CustomSpinner.SpinnerItem;
import com.ioss.covid.core.AppConfig;
import com.ioss.covid.core.CoreActivity;
import com.ioss.covid.databinding.ActivityRegistrationBinding;
import com.ioss.covid.model.chcModel.CHCModel;
import com.ioss.covid.model.chcModel.Chc;
import com.ioss.covid.model.districtModel.District;
import com.ioss.covid.model.districtModel.DistrictModel;
import com.ioss.covid.model.panchayathModel.Panchayath;
import com.ioss.covid.model.panchayathModel.PanchayathModel;
import com.ioss.covid.model.registerModel.RegisterModel;
import com.ioss.covid.model.stateModel.State;
import com.ioss.covid.model.stateModel.StateModel;
import com.ioss.covid.utilities.Utility;
import com.ioss.covid.utilities.Validation;
import com.ioss.covid.viewModel.RegistrationViewModel;

public class RegistrationActivity extends CoreActivity {

    private RegistrationViewModel viewModel;
    private ActivityRegistrationBinding binding;
    private CustomSpinnerAdapter stateAdapter,districtAdapter,panchayathAdapter,phcAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bindView();
        getSupportActionBar().setTitle(getString(R.string.registration));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setHideMenuOptions(true);

        animateView();
        setGenderAdapter();
        setStateAdapter();
        setDistrictAdapter();
        setPanchayathAdapter();
        setPHCAdapter();
        Utility.setFonts(AppConfig.openSansLight,binding.registerBT);

        viewModel._getState();

        viewModel.stateResp.observe(this,stateObserver);
        viewModel.districtResp.observe(this,districtObserver);
        viewModel.panchayathResp.observe(this,panchayathObserver);
        viewModel.chcResp.observe(this,chcObserver);
        viewModel.regResp.observe(this,regObserver);

        binding.stateSP.setOnItemSelectedListener(stateSelectioListener);
        binding.districtSP.setOnItemSelectedListener(districtSelectioListener);
        binding.panchayathSP.setOnItemSelectedListener(panchayathSelectioListener);
    }

    private void bindView() {
        viewModel = ViewModelProviders.of(this).get(RegistrationViewModel.class);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_registration);
        binding.setLifecycleOwner(this);
        binding.setViewModel(viewModel);
        setProgress(viewModel);
    }

    Observer<? super StateModel> stateObserver=new Observer<StateModel>() {
        @Override
        public void onChanged(StateModel stateModel) {
            if(stateModel ==null)return;

            if(stateModel.getStatus()){
                stateAdapter.clear();
                stateAdapter.addItem(new SpinnerItem(getString(R.string.select_state),"")).hasTitle(true);
                for(int i=0;i<stateModel.getData().getStates().size();i++){
                    State state = stateModel.getData().getStates().get(i);
                    stateAdapter.addItem(new SpinnerItem(state.getStateName(),state.getStateId()));
                }
            }

        }
    };

    Observer<? super DistrictModel> districtObserver=new Observer<DistrictModel>() {
        @Override
        public void onChanged(DistrictModel districtModel) {
            if(districtModel ==null)return;

            if(districtModel.getStatus()){
                districtAdapter.clear();
                districtAdapter.addItem(new SpinnerItem(getString(R.string.select_district),"")).hasTitle(true);
                for(int i=0;i<districtModel.getData().getDistricts().size();i++){
                    District dist = districtModel.getData().getDistricts().get(i);
                    districtAdapter.addItem(new SpinnerItem(dist.getDistrictName(),dist.getDistrictId()));
                }
            }
        }
    };

    Observer<? super PanchayathModel> panchayathObserver=new Observer<PanchayathModel>() {
        @Override
        public void onChanged(PanchayathModel panchayathModel) {
            if(panchayathModel ==null)return;

            if(panchayathModel.getStatus()){
                panchayathAdapter.clear();
                panchayathAdapter.addItem(new SpinnerItem(getString(R.string.select_villege),"")).hasTitle(true);
                for(int i=0;i<panchayathModel.getData().getPanchayath().size();i++){
                    Panchayath panch = panchayathModel.getData().getPanchayath().get(i);
                    panchayathAdapter.addItem(new SpinnerItem(panch.getPanchayatName(),panch.getPanchayatId()));
                }
            }
        }
    };

    Observer<? super CHCModel> chcObserver=new Observer<CHCModel>() {
        @Override
        public void onChanged(CHCModel chcModel) {
            if(chcModel ==null)return;

            if(chcModel.getStatus()){
                phcAdapter.clear();
                phcAdapter.addItem(new SpinnerItem(getString(R.string.select_chc),"")).hasTitle(true);
                for(int i=0;i<chcModel.getData().getChc().size();i++){
                    Chc chc = chcModel.getData().getChc().get(i);
                    phcAdapter.addItem(new SpinnerItem(chc.getChcName(),chc.getChcId()));
                }
            }
        }
    };

    Observer<? super RegisterModel> regObserver=new Observer<RegisterModel>() {
        @Override
        public void onChanged(RegisterModel registerModel) {

            if(registerModel == null) return;
            if(registerModel.getStatus()){
                if(registerModel.getData()==null){
                    setResult(RESULT_OK);
                    Toast.makeText(context,registerModel.getMessage(),Toast.LENGTH_SHORT).show();
                    finish();
                }else {
                    Intent intent = new Intent(context, ViewMembersActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    preferenceManager.setUserType("LOCAL");
                    preferenceManager.setToken(registerModel.getData().getAccessToken());
                    startActivity(intent);
                    Toast.makeText(context,registerModel.getMessage(),Toast.LENGTH_SHORT).show();
                }
            }else {
                Toast.makeText(context,registerModel.getMessage(),Toast.LENGTH_SHORT).show();
            }
        }
    };



    AdapterView.OnItemSelectedListener stateSelectioListener=new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            SpinnerItem item=(SpinnerItem) binding.stateSP.getSelectedItem();
            viewModel._getDistrict(item.getId());
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };

    AdapterView.OnItemSelectedListener districtSelectioListener=new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            SpinnerItem item=(SpinnerItem) binding.districtSP.getSelectedItem();
            viewModel._getPanchayath(item.getId());
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };

    AdapterView.OnItemSelectedListener panchayathSelectioListener=new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            SpinnerItem distritem=(SpinnerItem) binding.districtSP.getSelectedItem();

            SpinnerItem panchitem=(SpinnerItem) binding.districtSP.getSelectedItem();

            viewModel._getCHC(distritem.getId(),panchitem.getId());
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };

    private void setGenderAdapter(){
        CustomSpinnerAdapter genderAdapter=new CustomSpinnerAdapter(context)
                .addItem(new SpinnerItem(getString(R.string.select_gender),"")).hasTitle(true);

        genderAdapter
                .addItem(new SpinnerItem("Male","male"))
                .addItem(new SpinnerItem("Female","female"))
                .addItem(new SpinnerItem("Other","other"));

        binding.genderSP.setAdapter(genderAdapter);
    }

    private void setStateAdapter(){
         stateAdapter=new CustomSpinnerAdapter(context)
                .addItem(new SpinnerItem(getString(R.string.select_state),"")).hasTitle(true);

        binding.stateSP.setAdapter(stateAdapter);
    }



    private void setDistrictAdapter(){
         districtAdapter=new CustomSpinnerAdapter(context)
                .addItem(new SpinnerItem(getString(R.string.select_district),"")).hasTitle(true);

        binding.districtSP.setAdapter(districtAdapter);
    }

    private void setPanchayathAdapter(){
         panchayathAdapter=new CustomSpinnerAdapter(context)
                .addItem(new SpinnerItem(getString(R.string.select_villege),"")).hasTitle(true);

        binding.panchayathSP.setAdapter(panchayathAdapter);
    }

    private void setPHCAdapter(){

         phcAdapter=new CustomSpinnerAdapter(context)
                .addItem(new SpinnerItem(getString(R.string.select_chc),"")).hasTitle(true);

        binding.chcSP.setAdapter(phcAdapter);
    }

    private void animateView() {

        TranslateAnimation animateButton = new TranslateAnimation(
                0,                 // fromXDelta
                0,                 // toXDelta
                200,  // fromYDelta
                binding.registerBT.getHeight());                // toYDelta

        animateButton.setDuration(2000);
        animateButton.setFillAfter(false);
        binding.registerBT.startAnimation(animateButton);

    }

    public void onClickRegister(View view) {
        if(validation()){
            if(preferenceManager.getToken().length()>0){
                viewModel._rrtReg();
            }else {
                viewModel._userReg();
            }
        }
    }

    private boolean validation() {

        Validation validation=new Validation(context);
        if(!validation.isMobileValid( binding.mobileET,viewModel.mobile.getValue())) return false;
        if(!validation.isEmptyValidation(binding.passwordET,viewModel.password.getValue(),getString(R.string.message_err_password_empty))) return false;
        if(!validation.isConfirmPwdValid(binding.confirmPasswordET,viewModel.password.getValue(),viewModel.confirm_password.getValue())) return false;
        if(!validation.isNameValid( binding.nameET,viewModel.name.getValue())) return false;
        if(!validation.isEmptyValidation(binding.ageET,viewModel.age.getValue(),getString(R.string.message_err_age_empty))) return false;

        if(binding.genderSP.getSelectedItemPosition()==0){
            Toast.makeText(context,getString(R.string.select_gender),Toast.LENGTH_SHORT).show();
            return false;

        }else {
            viewModel.gender.setValue(((SpinnerItem)binding.genderSP.getSelectedItem()).getId());
        }

        if(binding.stateSP.getSelectedItemPosition()==0){
            Toast.makeText(context,getString(R.string.select_state),Toast.LENGTH_SHORT).show();
            return false;

        }else {
            viewModel.state.setValue(((SpinnerItem)binding.stateSP.getSelectedItem()).getId());
        }

        if(binding.districtSP.getSelectedItemPosition()==0){
            Toast.makeText(context,getString(R.string.select_district),Toast.LENGTH_SHORT).show();
            return false;

        }else {
            viewModel.district.setValue(((SpinnerItem)binding.districtSP.getSelectedItem()).getId());
        }

        if(binding.panchayathSP.getSelectedItemPosition()==0){
            Toast.makeText(context,getString(R.string.select_villege),Toast.LENGTH_SHORT).show();
            return false;

        }else {
            viewModel.panchayath.setValue(((SpinnerItem)binding.panchayathSP.getSelectedItem()).getId());
        }

        viewModel.chc.setValue(((SpinnerItem)binding.chcSP.getSelectedItem()).getId());
      /*  if(binding.chcSP.getSelectedItemPosition()==0){

        }else {
            viewModel.chc.setValue(((SpinnerItem)binding.chcSP.getSelectedItem()).getId());
        }*/
        if(!validation.isEmptyValidation(binding.addressET,viewModel.address.getValue(),getString(R.string.message_err_address_empty))) return false;

        return true;
    }

}
