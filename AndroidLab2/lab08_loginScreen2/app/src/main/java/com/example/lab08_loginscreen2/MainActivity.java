package com.example.lab08_loginscreen2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SharedPreferences sp = getSharedPreferences("app_name", MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("app_name", "1111");
        editor.putString("username", "2222");
        editor.commit();
    }
}
