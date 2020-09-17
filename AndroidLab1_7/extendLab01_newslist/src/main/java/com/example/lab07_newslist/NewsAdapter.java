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


//ListView加载数据都是在
//public View getView(int position, View convertView, ViewGroup parent) {}
//方法中进行的(要自定义listview都需要重写listadapter:
//如BaseAdapter，SimpleAdapter,CursorAdapter的等的getvView方法),
//优化listview的加载速度就要让convertView匹配列表类型，
//并最大程度上的重新使用convertView
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

//    @Override
//    public View getView(int position , View convertView , ViewGroup parent) {
//        News news = getItem(position);//用position来找数据
//
////        第一个传入的参数resourse是你想要加载的布局资源。
////        第二个传入的参数是指当前载入的视图要将要放入在层级结构中的根视图。
////        如果传入了第三个参数attachToRoot，那么它会决定视图在载入完成后是否附加到传入的根视图中去。
//        View view = LayoutInflater.from(getContext()).inflate(resourceId , parent , false);
//
//        //获取控件
//        TextView tvTitle = view.findViewById(R.id.tv_title);
//        TextView tvAuthor = view.findViewById(R.id.tv_subtitle);
//        ImageView ivImage = view.findViewById(R.id.iv_image);
//
//        //重置控件
//        tvTitle.setText(news.getTitle());
//        tvAuthor.setText(news.getmAuthor());
//        ivImage.setImageResource(news.getmImageId());
//        return view;//返回 view 以供调用
//    }


    public View getView(int position , View convertView , ViewGroup parent) {
        News news = getItem(position);
        View view;
        ViewHolder viewHolder;
        //当convertView不为空的时候
        // 直接重新使用convertView从而减少了很多不必要的View的创建，然后加载数据
        if (convertView == null) {
            view = LayoutInflater.from(getContext()).inflate(resourceId , parent , false);
            viewHolder = new ViewHolder();
            //获取控件
            viewHolder.textView = view.findViewById(R.id.tv_title);
            viewHolder.tvAuthor = view.findViewById(R.id.tv_subtitle);
            viewHolder.ivImage = view.findViewById(R.id.iv_image);
//            (1).Tag不像ID是用标示view的。Tag从本质上来讲是就是相关联的view的额外的信息。
//                它们经常用来存储一些view的数据，这样做非常方便而不用存入另外的单独结构。
//            (2). 首先我们要知道setTag方法是干什么的：他是给View对象的一个标签。
//                标签可以是任何内容，我们这里把他设置成了一个对象，
//                因为我们是把vlist2.xml的元素抽象出来成为一个类ViewHolder，
//                用了setTag，这个标签就是ViewHolder实例化后对象的一个属性。
//                我们之后对于ViewHolder实例化的对象holder的操作，
//                都会因为java的引用机制而一直存活并改变convertView的内容，
//                而不是每次都是去new一个。我们就这样达到的重用。
            view.setTag(viewHolder);
        } else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }
        //重置控件
        viewHolder.textView.setText(news.getTitle());
        viewHolder.tvAuthor.setText(news.getmAuthor());
        viewHolder.ivImage.setImageResource(news.getmImageId());

        return view;
    }

    class ViewHolder{
        TextView textView;
        TextView tvAuthor;
        ImageView ivImage;
    }


}