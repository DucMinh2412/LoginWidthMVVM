package com.example.loginwidthmvvm.viewmodel;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class UserViewModelFactory extends ViewModelProvider.NewInstanceFactory {
    private Context context;

    public UserViewModelFactory(Context context){
        this.context = context;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        // check class viewModel
        if(modelClass.isAssignableFrom(UserViewModel.class)){
            return (T) new UserViewModel(context);
        }
        throw new IllegalArgumentException("Unknown ViewModel class: " + modelClass.getName());
    }
}
