package com.example.news_app_demo.Adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.news_app_demo.R;
import com.example.news_app_demo.model.ObjectDataClass;
import com.example.news_app_demo.model.Subject;

import java.util.ArrayList;


public class SubjectAdapter extends RecyclerView.Adapter<SubjectAdapter.MyViewHolder> {
    ArrayList<Subject> list1;
    Context context;
    public SubjectAdapter(ArrayList<Subject> list, Context context) {
        this.list1 = list;
        this.context = context;
    }
    @NonNull
    @Override
    public SubjectAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item, parent, false);
        return new SubjectAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull SubjectAdapter.MyViewHolder holder, int position) {
        final Subject list = list1.get(position);
        holder.text_title.setText(list.getName());
    }
    @Override
    public int getItemCount() {
        return list1.size();
    }
    public class MyViewHolder extends RecyclerView.ViewHolder  {
        public TextView text_title;


//        MyOnClickListener myOnClickListener;

        public MyViewHolder(@NonNull View view) {
            super(view);
            text_title = (TextView) view.findViewById(R.id.subject_text_view);

        }
    }

}
