package com.example.loginwidthmvvm.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.loginwidthmvvm.databinding.ItemUserBinding;
import com.example.loginwidthmvvm.model.User;

import java.util.ArrayList;
import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {
    private Context context;
    private List<User> userList = new ArrayList<>();
    private UpdateAndDelete updateAndDelete;

    public UserAdapter(Context context, UpdateAndDelete updateAndDelete) {
        this.context = context;
        this.updateAndDelete = updateAndDelete;
    }

    public void setData(List<User> userList) {
        this.userList.clear();
        this.userList.addAll(userList);
        notifyDataSetChanged();
    }

    public interface UpdateAndDelete {
        void onUpdate(User user);

        void onDelete(User user);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        ItemUserBinding binding = ItemUserBinding.inflate(layoutInflater, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final User user = userList.get(position);
        holder.Bind(user);

        holder.binding.imgUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateAndDelete.onUpdate(user);
            }
        });

        holder.binding.imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateAndDelete.onDelete(user);
            }
        });
    }

    @Override
    public int getItemCount() {
        // if userList!=null == true => userList.Size; =False => return 0
        return userList != null ? userList.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ItemUserBinding binding;

        public ViewHolder(@NonNull ItemUserBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        // đổ và cập nhật dữ liệu lên view
        public void Bind(User user) {
            binding.setUser(user);
            binding.executePendingBindings();
        }
    }
}
