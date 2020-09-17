package com.example.sentmessage;

import androidx.appcompat.app.AppCompatActivity;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.view.View;
import android.widget.Toast;
import android.widget.TextView;
import android.widget.EditText;
import android.content.Intent;

public class MessageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        Intent intent = getIntent();//获取 Intent 对象
        final TextView tvMessage = findViewById(R.id.message);//获取 id 为  message 的 TextView 控件
        String message = intent.getStringExtra("MESSAGE_STRING");//根据键找传过来的
        if (message != null) {
            //判断传过来的值是否为空，不为空则根据传过来的值设置显示的内容
            if (tvMessage != null) {
                tvMessage.setText(message);//设置显示的内容
            }
        }
    }
}




