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


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        测试代码
//        Toast.makeText(MainActivity.this, "here is MainActivity",
//                Toast.LENGTH_SHORT).show();

        final EditText etMessage = findViewById(R.id.message);//获取 id 为  message 的 EditText 控件
         Button btSend = findViewById(R.id.send_message);//获取 id 为  send_message 的 btSend 控件



        //设置鼠标点击监听器
        btSend.setOnClickListener(new View.OnClickListener() {
            //设置对鼠标点击的响应
            @Override
            public void onClick(View view) {
                String message = etMessage.getText().toString();//获取 etMessage 控件的文本并转为字符串
                //这个地方的 Intent 对象有点像 request 对象
                Intent intent = new Intent(MainActivity.this,
                        MessageActivity.class);
                //将键值对放入 Intent 对象中
                intent.putExtra("MESSAGE_STRING", message);
                //调用 activity
                startActivity(intent);
            }
        });
    }
}
