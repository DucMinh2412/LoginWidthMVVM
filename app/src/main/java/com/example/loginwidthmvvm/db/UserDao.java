package com.example.loginwidthmvvm.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;


import com.example.loginwidthmvvm.model.User;

import java.util.List;

@Dao
public interface UserDao {

    @Query("SELECT * FROM table_name_user")
    LiveData<List<User>> getAllUser();

    // truy vấn user em đang không biết làm thế nào, nên em không để live data ở đây :)
    // vì khi mình check username với password, thì mình phải set sự kiện onclick, sau đó
    // lấy 2 biến là username và password để truy vấn, rồi trả về user để so sánh với giá trị người dùng nhập
    // ví dụ ở đây dùng live data để theo dõi user trả về, bên view gọi observer để quan sát, thì nó chỉ trả về 1 lần, tức là trước khi người dùng nhập
    // còn cái mình muốn ở đây là sau khi onclick thì lấy username với password của người dùng nhập, thì nó lại không lấy được :)
    // vì vậy nên em mới để mỗi User chứ không có live data
    @Query("SELECT * FROM table_name_user WHERE username= :username AND password= :password")
    User queryUser(String username, String password);

    @Insert
    void insert(User user);

    @Update
    void update(User user);

    @Delete
    void delete(User user);

}
