package com.example.news_app_demo.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.news_app_demo.EditUserActivity;
import com.example.news_app_demo.R;
import com.example.news_app_demo.database.DatabaseOpenHelper;

import java.util.HashMap;
import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.MyViewHolder> {


    private List<HashMap<String, String>> userData;
    private Context context;


    public UserAdapter(Context context, List<HashMap<String, String>> userData) {
        this.context = context;
        this.userData = userData;

    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_item2, parent, false);
        return new MyViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {

        String userId = userData.get(position).get(DatabaseOpenHelper.USERS_ID);
        String userName = userData.get(position).get(DatabaseOpenHelper.USER_NAME);
        String userPhone = userData.get(position).get(DatabaseOpenHelper.USER_PHONE);


        holder.txtUserName.setText(userName);
        holder.txtUserPhone.setText(userPhone);





    }

    @Override
    public int getItemCount() {
        return userData.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView txtUserName, txtUserPhone;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            txtUserName = itemView.findViewById(R.id.txt_user_name);
            txtUserPhone = itemView.findViewById(R.id.txt_user_phone);


            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            Intent i = new Intent(UserAdapter.this.context, EditUserActivity.class);
            i.putExtra(DatabaseOpenHelper.USERS_ID, (String) ((HashMap) UserAdapter.this.userData.get(getAdapterPosition())).get(DatabaseOpenHelper.USERS_ID));
            i.putExtra(DatabaseOpenHelper.USER_NAME, (String) ((HashMap) UserAdapter.this.userData.get(getAdapterPosition())).get(DatabaseOpenHelper.USER_NAME));
            i.putExtra(DatabaseOpenHelper.USER_PHONE, (String) ((HashMap) UserAdapter.this.userData.get(getAdapterPosition())).get(DatabaseOpenHelper.USER_PHONE));
            i.putExtra(DatabaseOpenHelper.USER_PASSWORD, (String) ((HashMap) UserAdapter.this.userData.get(getAdapterPosition())).get(DatabaseOpenHelper.USER_PASSWORD));



            context.startActivity(i);
        }
    }


}