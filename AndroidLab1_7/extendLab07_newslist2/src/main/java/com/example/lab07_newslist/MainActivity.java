package com.example.lab07_newslist;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.res.TypedArray;
import android.os.Bundle;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );

        customAdapter();

    }

    private void customAdapter(){

        String[] titles = getResources().getStringArray(R.array.titles);//获取标题资源
        String[] authors = getResources().getStringArray(R.array.authors);//获取作者资源
        TypedArray images = getResources().obtainTypedArray(R.array.images);//获取图片资源
        final String NEWS_ID = "news_id";//设置 news 的键
        List<News> newsList = new ArrayList<>();
//        NewsAdapter newsAdapter = null;
        RecyclerView recyclerView;

        int length;
        ////选出数量最小的
        if (titles.length > authors.length) {
            length = authors.length > images.length() ? images.length() : authors.length;
        } else {
            length = titles.length>images.length() ? images.length():authors.length;
        }
        //设置 newsList
        for (int i = 0; i < length; i++) {
            News news = new News();
            news.setTitle(titles[i]);
            news.setmAuthor(authors[i]);
            int ResourceId = images.getResourceId(i, 0);
            news.setmImageId(ResourceId);
            newsList.add(news);
        }

        recyclerView = findViewById( R.id.lv_news_list );
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager( this );
        recyclerView.setLayoutManager( linearLayoutManager );
        NewsAdapter newsAdapter = new NewsAdapter(MainActivity.this,
                R.layout.list_item, newsList);

        recyclerView.setAdapter( newsAdapter );


    }

}