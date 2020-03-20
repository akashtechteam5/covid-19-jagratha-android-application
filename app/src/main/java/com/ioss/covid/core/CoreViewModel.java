package com.ioss.covid.core;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.ioss.covid.utilities.PreferenceManager;

public class CoreViewModel extends AndroidViewModel {

    public MutableLiveData<Boolean> isLoading=new MutableLiveData<>(false);
    public MutableLiveData<Integer> responseCodeLiveData =new MutableLiveData<>();
    public Context context;
    public PreferenceManager preferenceManager;

    public CoreViewModel(@NonNull Application application) {
        super(application);
        context=application;
        preferenceManager=new PreferenceManager(context);
    }

    public MutableLiveData<Boolean> getProgress(){
        if(isLoading==null){
            isLoading= new MutableLiveData<>(false);
        }
        return isLoading;
    }

    public void setLoading(boolean isLoading){
        if(this.isLoading==null){
            this.isLoading= new MutableLiveData<>();
        }
        this.isLoading.setValue(isLoading);
    }
}
