package com.example.loginwidthmvvm.ui.showlist;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;

import com.example.loginwidthmvvm.R;
import com.example.loginwidthmvvm.adapter.UserAdapter;
import com.example.loginwidthmvvm.databinding.CustomDialogUpdateBinding;
import com.example.loginwidthmvvm.databinding.FragmentShowListBinding;
import com.example.loginwidthmvvm.model.User;
import com.example.loginwidthmvvm.viewmodel.UserViewModel;
import com.example.loginwidthmvvm.viewmodel.UserViewModelFactory;


public class ShowListFragment extends Fragment implements UserAdapter.UpdateAndDelete {
    FragmentShowListBinding binding;
    UserViewModel viewModel;
    UserAdapter userAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentShowListBinding.inflate(inflater, container, false);
        viewModel = new ViewModelProvider(this, new UserViewModelFactory(requireContext())).get(UserViewModel.class);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setAdapter();
        viewModel.getAllUser().observe(getViewLifecycleOwner(), listUser -> {
            if (listUser != null) {
                userAdapter.setData(listUser);
            }
        });
    }

    private void setAdapter() {
        userAdapter = new UserAdapter(getContext(), this);
        binding.rclRecycleView.setAdapter(userAdapter);
    }

    // Dont create alertDialog in viewModel
    @Override
    public void onUpdate(User user) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        final AlertDialog dialog = builder.create();
        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
        final CustomDialogUpdateBinding binding = DataBindingUtil.inflate(layoutInflater,
                R.layout.custom_dialog_update, null, false);
        dialog.setView(binding.getRoot());
        binding.edtUsername.setText(user.getUsername());
        binding.edtPassword.setText(user.getPassword());
        binding.edtPhone.setText(user.getPhone());
        dialog.show();

        // get profile and update
        binding.imgUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String UserName = binding.edtUsername.getText().toString().trim();
                String PassWord = binding.edtPassword.getText().toString().trim();
                String Phone = binding.edtPhone.getText().toString().trim();
                user.setUsername(UserName);
                user.setPassword(PassWord);
                user.setPhone(Phone);
                viewModel.update(user);
                dialog.dismiss();
            }
        });
    }

    @Override
    public void onDelete(User user) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage(R.string.delete);
        builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                viewModel.delete(user);
            }
        });

        builder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create();
        builder.show();
    }


}