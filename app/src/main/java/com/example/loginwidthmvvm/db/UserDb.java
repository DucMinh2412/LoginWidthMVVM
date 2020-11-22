package com.example.loginwidthmvvm.db;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import com.example.loginwidthmvvm.model.User;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {User.class}, version = 1, exportSchema = false)
public abstract class UserDb extends RoomDatabase {
    public abstract UserDao userDAO();
    private static UserDb sUserDb;
    public static final String DATABASE_NAME = "User-database";

    // tạo luồng xử lý
    private static final int NUMBER_OF_THREADS = 3;
    public static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);


    public static UserDb getInstance(Context context) {
        if (sUserDb == null) {
            synchronized (UserDao.class) {
                sUserDb = Room.databaseBuilder(context, UserDb.class, DATABASE_NAME)
                        .allowMainThreadQueries()
                        .fallbackToDestructiveMigration()
                        .build();
            }
        }
        return sUserDb;
    }
}
