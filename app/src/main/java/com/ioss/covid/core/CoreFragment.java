package com.ioss.covid.core;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import com.ioss.covid.adapters.CustomProgressDialog;
import com.ioss.covid.utilities.PreferenceManager;

/**
 * Created by ioss on 13/12/17.
 */

public class CoreFragment extends Fragment {

    private final String PREF_LOCALE = "base_locale";
    protected Context context;
    protected PreferenceManager preferenceManager;
    protected CustomProgressDialog progressBar;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getContext();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        context = getContext();
        preferenceManager = new PreferenceManager(context);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return super.onCreateView(inflater, container, savedInstanceState);
    }

    protected void setProgress(CoreViewModel viewModel){
        progressBar = new CustomProgressDialog(getActivity());
        viewModel.getProgress().observe(this,progressObserver);
    }

    public final Observer<Boolean> progressObserver = new Observer<Boolean>() {
        @Override
        public void onChanged(@Nullable Boolean isLoading) {
            if(isLoading==null)return;
            if (!isLoading) {
                hideProgress();
            } else {
                showProgress();
            }
        }
    };

    protected void showProgress() {
        if (progressBar != null) {
            progressBar.show();
        }
    }

    protected void hideProgress() {
        if (progressBar != null) {
            progressBar.hide();
        }
    }
}
