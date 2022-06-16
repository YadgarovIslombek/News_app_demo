package com.example.news_app_demo.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.news_app_demo.model.ObjectDataClass;
import com.example.news_app_demo.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.MyViewHolder>{

    List<ObjectDataClass>objectDataClass;
    Context context;


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
    public void onBindViewHolder(@NonNull NewsAdapter.MyViewHolder holder, int position) {

        final ObjectDataClass objData = objectDataClass.get(position);

        //db = new DB();
        /*holder.idMahsulotGuruhi.setText(Integer.toString(productTypeList.get(position).getIdMahsulotGuruhi()));
        holder.mahsulotTuriId.setText(Integer.toString(productTypeList.get(position).getMahsulotTuriId()));*/
        holder.text_title.setText(objData.getTitle());
        holder.text_desc.setText(objData.getDescription());
        final String url = objData.getUrlToImage();
        if (objData.hasImage()) {

        Picasso.get().load(url).into(holder.image_headline);
        }else{
            holder.image_headline.setImageResource(R.drawable.ic_launcher_background);
        }
        // Put the image resource in a String Variable.

        // Check if an image is provided for this news story or not

            //Display the image of the current news story in that ImageView using Picasso
          /*  Picasso.with(getContext()).load(imageUrl).into(imageView);
        } else {
            // Otherwise Set the ImageView to the default image.
            imageView.setImageResource(R.drawable.news);
        }*/
    /*    holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.setOnClickListener(this);
                Intent intent = new Intent(context, ProductActivity.class);
                intent.putExtra("category", productGroup.getProductGroupId());
                Log.e("CATEGORY","QAYSI ID  "+ productGroup.getProductGroupId());
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                context.startActivity(intent);




            }
        });*/

    }

    @Override
    public int getItemCount() {
        return objectDataClass.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder  {
        public TextView text_title,text_desc;
        ImageView image_headline;

//        MyOnClickListener myOnClickListener;

        public MyViewHolder(@NonNull View view) {
            super(view);
            text_title = (TextView) view.findViewById(R.id.text_title);
            text_desc = (TextView) view.findViewById(R.id.text_desc);
            image_headline = (ImageView) view.findViewById(R.id.image_headline);
          /*  view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(){
                        Intent intent = new Intent(view.getContext(), ProductActivity.class);
                        view.getContext().startActivity(intent);
                    }
                    }
            });*/
      /*      view.setOnClickListener(this);
        }
        @Override
        public void onClick(View view) {
            myOnClickListener.OnClick();
        }
    }
    public interface MyOnClickListener {
        void OnClick();
    }*/

        }
    }
}
