package com.example.lab14_newslist3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.NetworkOnMainThreadException;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    private ListView lvNewsList;
    private List<News> newsData;

    private NewsAdapter adapter;

    private int page = 1;
    private int mCurrentColIndex = 0;

    private int[] mCols = new int[]{Constants.NEWS_COL5 ,
            Constants.NEWS_COL7 , Constants.NEWS_COL8 ,
            Constants.NEWS_COL10 , Constants.NEWS_COL11};

    private okhttp3.Callback callback = new okhttp3.Callback() {
        @Override
        public void onFailure(Call call, IOException e) {
            Log.e("TAG", "Failed to connect server!");
            e.printStackTrace();
        }

        @Override
        public void onResponse(Call call, Response response)
                throws IOException {
            if (response.isSuccessful()) {
                final String body = response.body().string();

                System.out.println(body);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Gson gson = new Gson();
                        Type jsonType =
                                new TypeToken<BaseResponse<List<News>>>() {
                                }.getType();
                        BaseResponse<List<News>> newsListResponse =
                                gson.fromJson(body, jsonType);
                        for (News news : newsListResponse.getData()) {
                            adapter.add(news);
                            System.out.println(news.getContentUrl());
                        }

                        adapter.notifyDataSetChanged();
                    }
                });
            } else {
            }
        }
    };

        @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        initData();


    }

    private void initView() {
        lvNewsList = findViewById(R.id.lv_news_list);

        lvNewsList.setOnItemClickListener(
            new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView <?> adapterView ,
                                        View view, int i, long l) {

                    Intent intent = new Intent(MainActivity.this ,
                            DetailActivity.class);

                    News news = adapter.getItem(i);
                    intent.putExtra(Constants.NEWS_DETAIL_URL_KEY ,
                            news.getContentUrl());

                    startActivity(intent);
                }
            });

    }

    private void initData() {
        newsData = new ArrayList<>();
        adapter = new NewsAdapter(MainActivity.this ,
                R.layout.list_item , newsData);
        lvNewsList.setAdapter(adapter);

        refreshData(1);
    }

    private void refreshData(final int page) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                NewsRequest requestObj = new NewsRequest();

                requestObj.setCol(mCols[mCurrentColIndex]);
                requestObj.setNum(Constants.NEWS_NUM);
                requestObj.setPage(page);
                String urlParams = requestObj.toString();

                Request request = new Request.Builder()
                        .url(Constants.GENERAL_NEWS_URL + urlParams)
                        .get().build();
                try {
                    OkHttpClient client = new OkHttpClient();
                    client.newCall(request).enqueue(callback);
                } catch (NetworkOnMainThreadException ex) {

                    ex.printStackTrace();
                }
            }
        }).start();
    }


}
