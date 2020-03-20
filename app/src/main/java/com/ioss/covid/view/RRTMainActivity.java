package com.ioss.covid.view;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ioss.covid.R;
import com.ioss.covid.core.AppConfig;
import com.ioss.covid.core.CoreActivity;
import com.ioss.covid.databinding.ActivityRrtmainBinding;
import com.ioss.covid.model.ViewMembersModel.MemberItem;
import com.ioss.covid.model.ViewMembersModel.ViewUserModel;
import com.ioss.covid.model.searchMemberModel.SearchUserModel;
import com.ioss.covid.utilities.Utility;
import com.ioss.covid.utilities.Validation;
import com.ioss.covid.viewModel.RRTMainViewModel;

import java.lang.reflect.Type;
import java.util.List;

public class RRTMainActivity extends CoreActivity {

    private static final int REQ_NEW_REQ = 100;
    private RRTMainViewModel viewModel;
    private ActivityRrtmainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bindView();
        animateView();
        getSupportActionBar().setTitle("RRT Home");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Utility.setFonts(AppConfig.openSansRegular,binding.newUserTV,binding.serachBT,binding.mobileET);

        viewModel.searchUserResp.observe(this,searchUserRespObserever);
    }

    private void bindView() {
        viewModel = ViewModelProviders.of(this).get(RRTMainViewModel.class);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_rrtmain);
        binding.setLifecycleOwner(this);
        binding.setViewModel(viewModel);
        setProgress(viewModel);
    }

    Observer<? super SearchUserModel> searchUserRespObserever=new Observer<SearchUserModel>() {
        @Override
        public void onChanged(SearchUserModel searchUserModel) {

            if(searchUserModel == null) {
                Toast.makeText(context, getString(R.string.message_err_internal_error), Toast.LENGTH_SHORT).show();
                return;
            }

            if(searchUserModel.getStatus()){
//                viewModel.loginId.setValue(searchUserModel.getData().getLoginId());

                Gson gson = new Gson();
                Type type = new TypeToken<List<MemberItem>>() {}.getType();
                String data = gson.toJson(searchUserModel.getData().getUsers(), type);
                Intent intent=new Intent(context,ViewMembersActivity.class);
                intent.putExtra("data",data);
                intent.putExtra("loginId",searchUserModel.getData().getLoginId());
                startActivity(intent);
                viewModel.mobile.setValue("");
            }else {
                Toast.makeText(context, searchUserModel.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    };



    private void animateView() {

        TranslateAnimation animateButton = new TranslateAnimation(
                0,                 // fromXDelta
                0,                 // toXDelta
                600,  // fromYDelta
                binding.userRegBT.getHeight());                // toYDelta

        animateButton.setDuration(800);
        animateButton.setFillAfter(false);
        binding.userRegBT.startAnimation(animateButton);

        TranslateAnimation animateButton2 = new TranslateAnimation(
                0,                 // fromXDelta
                0,                 // toXDelta
                800,  // fromYDelta
                binding.searchCV.getHeight());                // toYDelta

        animateButton2.setDuration(1000);
        animateButton2.setFillAfter(false);
        binding.searchCV.startAnimation(animateButton2);
    }

    public void onClickNewRegister(View view) {

        Intent intent=new Intent(context,RegistrationActivity.class);
        intent.putExtra("from_rrt",true);
        startActivityForResult(intent,REQ_NEW_REQ);
    }

    public void onClickSearch(View view) {
//      openNextActivity(ViewMembersActivity.class);
        Validation validation=new Validation(context);
       if(!validation.isMobileValid(binding.mobileET,viewModel.mobile.getValue())) return;

       viewModel.search_user();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if(requestCode == REQ_NEW_REQ && resultCode ==RESULT_OK){

        }
        super.onActivityResult(requestCode, resultCode, data);

    }
}
