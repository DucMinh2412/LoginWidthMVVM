package com.example.loginwidthmvvm.ui.signup;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.example.loginwidthmvvm.databinding.FragmentSignUpBinding;
import com.example.loginwidthmvvm.model.Error;
import com.example.loginwidthmvvm.model.User;
import com.example.loginwidthmvvm.viewmodel.UserViewModel;
import com.example.loginwidthmvvm.viewmodel.UserViewModelFactory;


public class SignupFragment extends Fragment {
    FragmentSignUpBinding binding;
    UserViewModel viewModel;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentSignUpBinding.inflate(inflater, container, false);
        binding.setLifecycleOwner(requireActivity());
        viewModel = new ViewModelProvider(getActivity(), new UserViewModelFactory(requireContext())).get(UserViewModel.class);
        binding.setUserViewModel(viewModel);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        NavController navController = Navigation.findNavController(view);
        // handle change User
        viewModel.userResponse().observe(getViewLifecycleOwner(), userBaseResponse -> {
            User user = userBaseResponse.getData();
            if (user != null) {
                viewModel.insert(user);
                binding.edtUsername.setText("");
                binding.edtPassword.setText("");
                binding.edtPhone.setText("");
                Toast.makeText(getContext(),"Signup Success !",Toast.LENGTH_SHORT).show();
                navController.navigateUp();
            }

            else {
                Toast.makeText(getContext(),"Signup Error !",Toast.LENGTH_SHORT).show();
            }
        });

        // handle error and update UI
        viewModel.mubLiveDataError().observe(getViewLifecycleOwner(), this::handleError);
    }

    private void handleError(Error error) {
        switch (error.getErrorType()) {
            case username:
            case phone:
            case password:
                Toast.makeText(getContext(),error.getMessage(),Toast.LENGTH_SHORT).show();
                break;
        }
    }
}