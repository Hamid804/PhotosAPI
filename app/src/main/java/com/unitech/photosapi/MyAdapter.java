package com.unitech.photosapi;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.bumptech.glide.Glide;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.LazyHeaders;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    public Context mContext;
    public List<MyListPhoto> mDataList;

    public MyAdapter(Context mContext, List<MyListPhoto> mDataList) {
        this.mContext = mContext;
        this.mDataList = mDataList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View rootView= LayoutInflater.from(mContext).inflate(R.layout.my_recycler_layout,parent,false);
        return new MyViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        MyListPhoto myListPhoto=mDataList.get(position);
        //String myUrl=myListPhoto.getThumbUrl()+".png";

        GlideUrl url = new GlideUrl(myListPhoto.getThumbUrl(), new LazyHeaders.Builder()
                .addHeader("User-Agent", "your-user-agent")
                .build());

        Glide.with(mContext).load(url).into(holder.imageView);
        holder.tvTitle.setText(myListPhoto.getmTitle());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(mContext,SecondActivity.class);
                intent.putExtra("imgId",myListPhoto.getImgId());
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        public ImageView imageView;
        public TextView tvTitle;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.imageViewRecycler);
            tvTitle=itemView.findViewById(R.id.textViewTitle);
        }
    }
}
