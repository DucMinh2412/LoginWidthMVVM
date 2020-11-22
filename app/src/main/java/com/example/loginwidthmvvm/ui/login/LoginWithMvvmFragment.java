package com.example.loginwidthmvvm.ui.login;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.loginwidthmvvm.R;
import com.example.loginwidthmvvm.databinding.FragmentLoginWithMvvmBinding;
import com.example.loginwidthmvvm.model.Error;
import com.example.loginwidthmvvm.model.User;
import com.example.loginwidthmvvm.viewmodel.UserViewModel;
import com.example.loginwidthmvvm.viewmodel.UserViewModelFactory;


public class LoginWithMvvmFragment extends Fragment {
    FragmentLoginWithMvvmBinding binding;
    UserViewModel viewModel;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentLoginWithMvvmBinding.inflate(inflater, container, false);
        binding.setLifecycleOwner(requireActivity());
        viewModel = new ViewModelProvider(this, new UserViewModelFactory(requireContext())).get(UserViewModel.class);
        binding.setUserViewModel(viewModel);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        NavController navController = Navigation.findNavController(view);

        //get user
        viewModel.getUser().observe(getViewLifecycleOwner(), user -> {
                    if (user != null) {
                        Toast.makeText(getContext(),"Login Success !",Toast.LENGTH_SHORT).show();
                        navController.navigate(R.id.action_loginWithMvvmFragment_to_showListFragment);
                    }
                    else {
                        Toast.makeText(getContext(),"Login Falled !",Toast.LENGTH_SHORT).show();
                    }
                }
        );


        binding.tvSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navController.navigate(R.id.action_loginWithMvvmFragment_to_signUpFragment);
            }
        });

        viewModel.mubLiveDataError().observe(getViewLifecycleOwner(), this::handleError);
    }

    private void handleError(Error error) {
        switch (error.getErrorType()) {
            case username:
            case phone:
            case password:
                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                break;
        }
    }

}