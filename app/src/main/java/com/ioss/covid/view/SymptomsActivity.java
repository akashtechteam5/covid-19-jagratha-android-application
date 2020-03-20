package com.ioss.covid.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ioss.covid.R;
import com.ioss.covid.adapters.SymptomsAdapter;
import com.ioss.covid.core.CoreActivity;
import com.ioss.covid.databinding.ActivitySymptomsBinding;
import com.ioss.covid.itemClass.SymptomsItem;
import com.ioss.covid.model.userDetailsModel.Symptom;
import com.ioss.covid.model.userDetailsModel.UserDetailItem;
import com.ioss.covid.model.userDetailsModel.UserDetailsModel;
import com.ioss.covid.utilities.Constants;
import com.ioss.covid.viewModel.SymptomsViewModel;

import java.lang.reflect.Type;
import java.util.List;

public class SymptomsActivity extends CoreActivity {

    private SymptomsViewModel viewModel;
    private ActivitySymptomsBinding binding;
    private SymptomsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bindView();
        getSupportActionBar().setTitle(getString(R.string.symptoms));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if(getIntent().hasExtra("details")){

            Gson gson1 = new Gson();
            String details = getIntent().getStringExtra("details");

            if (details != null) {
                Type type = new TypeToken<UserDetailItem>() {
                }.getType();

                UserDetailItem item = gson1.fromJson(details, type);
                viewModel.userDetails.setValue(item);
                setAdapter();
            }
        }

        viewModel.submitSymptomRep.observe(this,submitRespObserver);
    }

    private void bindView() {
        viewModel = ViewModelProviders.of(this).get(SymptomsViewModel.class);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_symptoms);
        binding.setLifecycleOwner(this);
        binding.setViewmodel(viewModel);
        setProgress(viewModel);
    }

    Observer<? super UserDetailsModel> submitRespObserver = new Observer<UserDetailsModel>() {
        @Override
        public void onChanged(UserDetailsModel userDetailsModel) {
            if (userDetailsModel == null) {
                Toast.makeText(context, "Internal error occured", Toast.LENGTH_SHORT).show();
                return;
            }
            if (userDetailsModel.getStatus()) {
                Toast.makeText(context, userDetailsModel.getMessage(), Toast.LENGTH_SHORT).show();
                Gson gson = new Gson();
                Type type = new TypeToken<UserDetailItem>() {}.getType();
                String details = gson.toJson(userDetailsModel.getData(), type);

                Intent intent = new Intent();
                intent.putExtra("details",details);
                setResult(RESULT_OK,intent);
                finish();

            } else {
                Toast.makeText(context, userDetailsModel.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    };


    private void setAdapter(){
        if(viewModel.userDetails == null) return;

        List<Symptom> symptoms=viewModel.userDetails.getValue().getSymptoms();

        binding.symptomsRV.setLayoutManager(new LinearLayoutManager(context));
        adapter=new SymptomsAdapter(context);

        for(int i=0;i<symptoms.size();i++){
            Symptom symptom=symptoms.get(i);
            adapter.addItem(new SymptomsItem(symptom.getSymptomId(),symptom.getSymptom(),symptom.getValue()));
        }

        binding.symptomsRV.setAdapter(adapter);
    }

    public void onClicksubmit(View view) {
        if(adapter.getSymptonArray() == null) return;
        Constants.makeLog("sympt....."+adapter.getSymptonArray());
        viewModel.update_user_symptoms(adapter.getSymptonArray());
    }
}
