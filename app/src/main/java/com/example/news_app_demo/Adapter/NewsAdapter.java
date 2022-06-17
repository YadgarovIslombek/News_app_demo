package com.example.news_app_demo.Adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.news_app_demo.Util;
import com.example.news_app_demo.model.ObjectDataClass;
import com.example.news_app_demo.R;

import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.MyViewHolder>{

    List<ObjectDataClass>objectDataClass;
    Context context;
    private OnItemClickListener onItemClickListener;


//    public MyOnClickListener myOnClickListener;

    public NewsAdapter(List<ObjectDataClass> objectDataClass, Context context) {
        this.objectDataClass = objectDataClass;
        this.context = context;
    }

    @NonNull
    @Override
    public NewsAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.news_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holders, int position) {
        final MyViewHolder holder = holders;
        final ObjectDataClass objData = objectDataClass.get(position);


     /*   holder.text_title.setText(objData.getTitle());
        holder.text_desc.setText(objData.getDescription());
        final String url = objData.getUrlToImage();
        if (objData.hasImage()) {

        Picasso.get().load(url).into(holder.image_headline);
        }else{
            holder.image_headline.setImageResource(R.drawable.ic_launcher_background);
        }*/
        if(objData.hasImage()) {
            Glide.with(context)
                    .load(objData.getUrlToImage())
                    .listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            holder.progressBar.setVisibility(View.GONE);
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            holder.progressBar.setVisibility(View.GONE);
                            return false;
                        }
                    })
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(holder.imageView);
        }else{
            holder.imageView.setImageResource(R.drawable.ic_launcher_background);
        }
        holder.title.setText(objData.getTitle());
        holder.desc.setText(objData.getDescription());
       // holder.source.setText(objData.getSource().getName());
        holder.time.setText(" \u2022 " + Util.DateToTimeFormat(objData.getPublishedAt()));
        holder.published_ad.setText(Util.DateFormat(objData.getPublishedAt()));
       // holder.author.setText(objData.getAuthor());




    }

    @Override
    public int getItemCount() {
        return objectDataClass.size();
    }
    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }
    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }
    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements  View.OnClickListener  {
       /* public TextView text_title,text_desc;
        ImageView image_headline;*/
       TextView title, desc, author, published_ad, source, time;
        ImageView imageView;
        ProgressBar progressBar;
        OnItemClickListener onItemClickListener;


        public MyViewHolder(@NonNull View view) {
            super(view);
          /*  text_title = (TextView) view.findViewById(R.id.text_title);
            text_desc = (TextView) view.findViewById(R.id.text_desc);
            image_headline = (ImageView) view.findViewById(R.id.image_headline);*/
            view.setOnClickListener(this);
            title = itemView.findViewById(R.id.text_title);
            desc = itemView.findViewById(R.id.text_desc);
           // author = itemView.findViewById(R.id.author);
            published_ad = itemView.findViewById(R.id.publishedAt);
           // source = itemView.findViewById(R.id.source);
            time = itemView.findViewById(R.id.time);
            imageView = itemView.findViewById(R.id.image_headline);
            progressBar = itemView.findViewById(R.id.prograss_load_photo);

            this.onItemClickListener = onItemClickListener;

        }


        @Override
        public void onClick(View view) {
            onItemClickListener.onItemClick(view, getAdapterPosition());
        }
    }



}
