package com.ioss.covid.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.badge.BadgeDrawable;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ioss.covid.R;
import com.ioss.covid.adapters.ViewMemberAdapter;
import com.ioss.covid.interfaces.ViewUsersListener;
import com.ioss.covid.core.CoreActivity;
import com.ioss.covid.databinding.ActivityRrtviewMembersBinding;
import com.ioss.covid.model.ViewMembersModel.MemberItem;
import com.ioss.covid.model.ViewMembersModel.ViewUserModel;
import com.ioss.covid.model.userDetailsModel.UserDetailItem;
import com.ioss.covid.model.userDetailsModel.UserDetailsModel;
import com.ioss.covid.viewModel.ViewMemberViewModel;

import java.lang.reflect.Type;
import java.util.List;

public class ViewMembersActivity extends CoreActivity implements ViewUsersListener {

    private static final int REQ_ADD_NEW_USER = 100;
    private ViewMemberViewModel viewModel;
    private ActivityRrtviewMembersBinding binding;
    private ViewMemberAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bindView();
        getSupportActionBar().setTitle(getString(R.string.view_members));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setAdapter();

        setData(getIntent());

        viewModel.viewUserResp.observe(this, viewUserRespObserver);
        viewModel.userDetailsResp.observe(this, userDetailsRespObserver);
    }

    private void setData(Intent intent){
        if(intent == null) return;

        if (intent.hasExtra("data")) {

            Gson gson1 = new Gson();
            String data = intent.getStringExtra("data");

            if (data != null) {
                Type type = new TypeToken<List<MemberItem>>() {
                }.getType();
                List<MemberItem> list = gson1.fromJson(data, type);
                adapter.setList(list);
            }
        } else {
            viewModel._viewMembers();
        }

        if (getIntent().hasExtra("loginId")) {
            viewModel.loginId.setValue(getIntent().getStringExtra("loginId"));
        }
    }

    private void bindView() {
        viewModel = ViewModelProviders.of(this).get(ViewMemberViewModel.class);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_rrtview_members);
        binding.setLifecycleOwner(this);
        binding.setViewModel(viewModel);
        setProgress(viewModel);
    }

    Observer<? super ViewUserModel> viewUserRespObserver = new Observer<ViewUserModel>() {
        @Override
        public void onChanged(ViewUserModel viewUserModel) {

            if (viewUserModel == null) {
                Toast.makeText(context, getString(R.string.message_err_internal_error), Toast.LENGTH_SHORT).show();
                return;
            }

            if (viewUserModel.getStatus()) {
                adapter.setList(viewUserModel.getData());
            } else {
                Toast.makeText(context, viewUserModel.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    };

    Observer<? super UserDetailsModel> userDetailsRespObserver = new Observer<UserDetailsModel>() {
        @Override
        public void onChanged(UserDetailsModel userDetailsModel) {
            if (userDetailsModel == null) {
                Toast.makeText(context, "Internal error occured", Toast.LENGTH_SHORT).show();
                return;
            }
            if (userDetailsModel.getStatus()) {
                Gson gson = new Gson();
                Type type = new TypeToken<UserDetailItem>() {}.getType();
                String details = gson.toJson(userDetailsModel.getData(), type);

                Intent intent = new Intent(context, UserDetailActivity.class);
                intent.putExtra("details",details);
                startActivity(intent);

            } else {
                Toast.makeText(context, userDetailsModel.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    };

    private void setAdapter() {
        binding.addBT.shrink();
        adapter = new ViewMemberAdapter(context).setListener(this);
        binding.membersRV.setLayoutManager(new LinearLayoutManager(context));
        binding.membersRV.setAdapter(adapter);
        binding.membersRV.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                if(newState==RecyclerView.SCROLL_STATE_IDLE){
                    binding.addBT.shrink();
                }else {
                    binding.addBT.extend();
                }
                super.onScrollStateChanged(recyclerView, newState);
            }
        });
    }

    public void onClickAddUser(View view) {
        Intent intent = new Intent(context, AddMemberActivity.class);
        if (viewModel.loginId.getValue().length() != 0) {
            intent.putExtra("loginId", viewModel.loginId.getValue());
        }
        startActivityForResult(intent,REQ_ADD_NEW_USER);
    }

    @Override
    public void onClickUserItem(MemberItem item) {
        if (item == null) return;
        viewModel.get_user_details(item.getUserId());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == REQ_ADD_NEW_USER && resultCode == RESULT_OK){
            setData(data);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
