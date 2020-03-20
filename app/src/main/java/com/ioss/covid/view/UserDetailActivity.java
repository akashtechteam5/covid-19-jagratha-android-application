package com.ioss.covid.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ioss.covid.R;
import com.ioss.covid.core.CoreActivity;
import com.ioss.covid.databinding.ActivityMainBinding;
import com.ioss.covid.model.userDetailsModel.UserDetailItem;
import com.ioss.covid.utilities.Constants;
import com.ioss.covid.viewModel.UserDetailViewModel;

import java.lang.reflect.Type;

public class UserDetailActivity extends CoreActivity {

    private static final int REQ_SYMPTOMS = 100;
    private static final int REQ_QUESTION = 101;
    private UserDetailViewModel viewModel;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bindView();

        if(getIntent().hasExtra("details")){

            Gson gson1 = new Gson();
            String details = getIntent().getStringExtra("details");

            if (details != null) {
                Type type = new TypeToken<UserDetailItem>() {
                }.getType();
                UserDetailItem item = gson1.fromJson(details, type);
                viewModel.userDetails.setValue(item);
                getSupportActionBar().setTitle(viewModel.userDetails.getValue().getName());
            }
        }
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void bindView() {
        viewModel = ViewModelProviders.of(this).get(UserDetailViewModel.class);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.setLifecycleOwner(this);
        binding.setViewmodel(viewModel);
        setProgress(viewModel);
    }

    public void onClicksymptoms(View view) {
        if(viewModel.userDetails == null) return;
        Gson gson = new Gson();
        Type type = new TypeToken<UserDetailItem>() {}.getType();
        String details = gson.toJson(viewModel.userDetails.getValue(), type);

        Intent intent = new Intent(context, SymptomsActivity.class);
        intent.putExtra("details",details);
        startActivityForResult(intent,REQ_SYMPTOMS);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == REQ_SYMPTOMS && resultCode == RESULT_OK){
            if(data.hasExtra("details")){

                Gson gson1 = new Gson();
                String details =data.getStringExtra("details");

                if (details != null) {
                    Type type = new TypeToken<UserDetailItem>() {
                    }.getType();
                    UserDetailItem item = gson1.fromJson(details, type);
                    viewModel.userDetails.setValue(item);
                    getSupportActionBar().setTitle(viewModel.userDetails.getValue().getName());
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void onClickQuestions(View view) {

//      openNextActivity(QuestionsActivity.class);
        Intent intent=new Intent(context,QuestionsWebViewActivity.class);
        String userId=viewModel.userDetails.getValue().getUserId();
        String url= Constants.URL +"login/questionnaire_web_view?"+"user_id="+userId+"&access_token="+preferenceManager.getToken();
        intent.putExtra("url",url);
        startActivity(intent);
//      startActivityForResult(intent,REQ_QUESTION);

    }
}
