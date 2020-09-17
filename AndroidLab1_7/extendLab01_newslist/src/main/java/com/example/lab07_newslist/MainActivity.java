package com.example.lab07_newslist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.TypedArray;
import android.os.Bundle;
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
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);System.out.println(111);
//        showTitle();
//        SimpleAdapterDemo();
        customAdapter();
    }




    /**
     * 仅显示标题
     */
    private void showTitle(){
        //获取标题资源
        String[] titles = getResources().getStringArray(R.array.titles);
        // ArrayAdapter 对象 参数设为 String
        //指定 context 为 MainActivity.this
        //对于每一条信息，使用 simple_list_item_1 布局
        //传递数据
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                MainActivity.this, android.R.layout.simple_list_item_1, titles);
        //列表使用 lv_news_list 布局
        ListView lvNewsList = findViewById(R.id.lv_news_list);
        //数据传递给ListView,这时需要借助适配器(Adapter)来完成
        lvNewsList.setAdapter(adapter);
    }

    private void SimpleAdapterDemo(){
        String NEWS_TITLE = "news_title";//设置标题的键
        String NEWS_AUTHOR = "news_author";//设置作者的值
        String[] titles = getResources().getStringArray(R.array.titles);//获取标题资源
        String[] authors = getResources().getStringArray(R.array.authors);//获取作者资源
        //选出数量最小的
        int length = titles.length > authors.length ? authors.length:titles.length;
        //根据 List<? extends Map<String, ?>> 设置参数
        List<Map<String, String>> dataList = new ArrayList<>();
        for (int i = 0; i < length; i++) {
            Map map = new HashMap();//
            map.put(NEWS_TITLE , titles[i]);
            map.put(NEWS_AUTHOR , authors[i]);
            dataList.add(map);
        }

        //创建 SimpleAdapter
        SimpleAdapter simpleAdapter = new SimpleAdapter(MainActivity.this ,
                dataList , android.R.layout.simple_list_item_2 ,
                new String[]{NEWS_TITLE , NEWS_AUTHOR},
                new int[]{android.R.id.text1 , android.R.id.text2});

        //设置为 lv_news_list 布局
        ListView lvNewsList = findViewById(R.id.lv_news_list);
        //添加 SimpleAdapter
        lvNewsList.setAdapter(simpleAdapter);
    }

    private void customAdapter(){

        String[] titles = getResources().getStringArray(R.array.titles);//获取标题资源
        String[] authors = getResources().getStringArray(R.array.authors);//获取作者资源
        TypedArray images = getResources().obtainTypedArray(R.array.images);//获取图片资源
        final String NEWS_ID = "news_id";//设置 news 的键
        List<News> newsList = new ArrayList<>();//

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
        //设置 newsAdapter
        NewsAdapter newsAdapter = new NewsAdapter(MainActivity.this,
                R.layout.list_item, newsList);
        //设置为 list_item 布局
        ListView lvNewsList = findViewById(R.id.lv_news_list);
        //添加 newsAdapter
        lvNewsList.setAdapter(newsAdapter);

    }


}

