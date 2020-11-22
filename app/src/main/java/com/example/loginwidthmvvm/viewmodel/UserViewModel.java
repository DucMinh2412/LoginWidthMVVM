package com.example.loginwidthmvvm.viewmodel;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.loginwidthmvvm.R;
import com.example.loginwidthmvvm.base.SingleLiveEvent;
import com.example.loginwidthmvvm.base.BaseResponse;
import com.example.loginwidthmvvm.model.Error;
import com.example.loginwidthmvvm.model.User;
import com.example.loginwidthmvvm.repository.UserRepository;
import com.example.loginwidthmvvm.repository.UserRepositoryImp;

import java.util.List;


public class UserViewModel extends ViewModel {
    // get information from user
    public MutableLiveData<String> username = new MutableLiveData<>();
    public MutableLiveData<String> password = new MutableLiveData<>();
    public MutableLiveData<String> phone = new MutableLiveData<>();
    // check error
    public MutableLiveData<Error> message = new MutableLiveData<>();
    // live data object
    private SingleLiveEvent<User> singleLiveUser = new SingleLiveEvent<>();
    private SingleLiveEvent<BaseResponse<User>> singleLiveUserResponse = new SingleLiveEvent<>();
    private LiveData<List<User>> liveDataListUser;
    // constructor
    private UserRepository userRepository;
    private User user;
    private Context context;


    public UserViewModel(Context context) {
        this.context = context;
        user = new User();
        userRepository = UserRepositoryImp.getInstance(context);
        liveDataListUser = userRepository.getAllUser();
    }

    public MutableLiveData<Error> mubLiveDataError() {
        return message;
    }

    public SingleLiveEvent<BaseResponse<User>> singleLiveUserResponse() {
        return singleLiveUserResponse;
    }

    public LiveData<List<User>> getAllUser() {
        return liveDataListUser;
    }

    public SingleLiveEvent<User> getUser() {
        return singleLiveUser;
    }

    public void insert(User user) {
        userRepository.insertUser(user);
    }

    public void update(User user) {
        userRepository.updateUser(user);
    }

    public void delete(User user) {
        userRepository.deleteUser(user);
    }

    public void signup() {
        if (username.getValue() == null || username.getValue().isEmpty()) {
            message.setValue(new Error(Error.ErrorType.username, context.getString(R.string.error_username)));

        } else if (password.getValue() == null || password.getValue().isEmpty()) {
            message.setValue(new Error(Error.ErrorType.password, context.getString(R.string.error_password)));

        } else if (phone.getValue() == null || phone.getValue().isEmpty()) {
            message.setValue(new Error(Error.ErrorType.phone, context.getString(R.string.error_phone)));

        } else if (username.getValue().length() > 0 && password.getValue().length() > 0 && phone.getValue().length() == 10) {
            user.setUsername(username.getValue());
            user.setPassword(password.getValue());
            user.setPhone(phone.getValue());
            singleLiveUserResponse.setValue(new BaseResponse<>(user));
        }
    }

    public void login() {
        if (username.getValue() == null || username.getValue().isEmpty()) {
            message.setValue(new Error(Error.ErrorType.username, context.getString(R.string.error_username)));
            return;
        } else if (password.getValue() == null || password.getValue().isEmpty()) {
            message.setValue(new Error(Error.ErrorType.password, context.getString(R.string.error_password)));
            return;
        } else {
            singleLiveUser.setValue(userRepository.queryUser(username.getValue(),password.getValue()));
        }
    }
}
