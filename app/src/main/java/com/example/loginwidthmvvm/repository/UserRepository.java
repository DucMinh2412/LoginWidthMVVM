package com.example.loginwidthmvvm.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.example.loginwidthmvvm.model.User;
import java.util.List;

// lớp trung gian giữa việc truy cập dữ liệu và xử lý logic
public interface UserRepository {
    LiveData<List<User>> getAllUser();

    User queryUser(String username, String password);

    void insertUser(User user);

    void updateUser(User user);

    void deleteUser(User user);
}
