package com.example.lab07_newslist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lab07_newslist.News;
import com.example.lab07_newslist.R;

import java.util.List;

public class NewsAdapter extends ArrayAdapter <News> {

    private List<News> mNewsData;
    private Context mContext;
    private int resourceId;

    public NewsAdapter(Context context , int resourceId , List<News> data) {
        super(context , resourceId , data);
        this.mContext = context;
        this.mNewsData = data;
        this.resourceId = resourceId;
    }

    @Override
    public View getView(int position , View convertView , ViewGroup parent) {
        News news = getItem(position);//用position来找数据

//        第一个传入的参数resourse是你想要加载的布局资源。
//        第二个传入的参数是指当前载入的视图要将要放入在层级结构中的根视图。
//        如果传入了第三个参数attachToRoot，那么它会决定视图在载入完成后是否附加到传入的根视图中去。
        View view = LayoutInflater.from(getContext()).inflate(resourceId , parent , false);

        //获取控件
        TextView tvTitle = view.findViewById(R.id.tv_title);
        TextView tvAuthor = view.findViewById(R.id.tv_subtitle);
        ImageView ivImage = view.findViewById(R.id.iv_image);

        //重置控件
        tvTitle.setText(news.getTitle());
        tvAuthor.setText(news.getmAuthor());
        ivImage.setImageResource(news.getmImageId());
        return view;//返回 view 以供调用
    }
}