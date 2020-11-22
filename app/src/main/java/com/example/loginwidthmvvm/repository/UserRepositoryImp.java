package com.example.loginwidthmvvm.repository;

import android.content.Context;

import androidx.lifecycle.LiveData;
import com.example.loginwidthmvvm.db.UserDao;
import com.example.loginwidthmvvm.db.UserDb;
import com.example.loginwidthmvvm.model.User;
import java.util.List;

public class UserRepositoryImp implements UserRepository {
    private LiveData<List<User>> liveDataUserList;
    private static UserRepository instance = null;
    private UserDao userDao;

    // singleton pattern
    public static UserRepository getInstance(Context context) {
        if (instance == null) {
            instance = new UserRepositoryImp(context);
        }
        return instance;
    }

    private UserRepositoryImp(Context context) {
        UserDb userDb = UserDb.getInstance(context);
        this.userDao = userDb.userDAO();
        liveDataUserList = userDao.getAllUser();
    }


    @Override
    public LiveData<List<User>> getAllUser() {
        return liveDataUserList;
    }

    @Override
    public User queryUser(String username, String password) {
        return userDao.queryUser(username, password);
    }

    @Override
    public void insertUser(User user) {
        UserDb.databaseWriteExecutor.execute(() ->
                userDao.insert(user)
        );
    }

    @Override
    public void updateUser(User user) {
        UserDb.databaseWriteExecutor.execute(() ->
                userDao.update(user)
        );
    }

    @Override
    public void deleteUser(User user) {
        UserDb.databaseWriteExecutor.execute(() ->
                userDao.delete(user)
        );

    }
}
