package com.example.lab07_newslist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lab07_newslist.News;
import com.example.lab07_newslist.R;

import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {

    private List<News> data;
    private Context context;
    private int resourceId;

    public NewsAdapter(Context context , int resourceId , List<News> data){

        this.context=context;
        this.data=data;
        this.resourceId=resourceId;
    }


    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView textView;
        TextView tvAuthor;
        ImageView ivImage;

        public ViewHolder(@NonNull View itemView) {
            super( itemView );
            textView = itemView.findViewById(R.id.tv_title);
            tvAuthor = itemView.findViewById(R.id.tv_subtitle);
            ivImage = itemView.findViewById(R.id.iv_image);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View  view = LayoutInflater.from(context).inflate(resourceId , parent , false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        News news = data.get(position);//获取位置
        holder.textView.setText(news.getmTitle());//获取文本
        holder.tvAuthor.setText(news.getmAuthor());//获取作者

        if (news.getmImageId() != -1){
            holder.ivImage.setImageResource(news.getmImageId());
        }

    }

    @Override
    public int getItemCount() {
        return data.size();
    }


}