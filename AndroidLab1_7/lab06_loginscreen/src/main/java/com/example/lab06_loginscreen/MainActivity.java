package com.example.lab06_loginscreen;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.text.InputType;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Boolean bPwdSwitch = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //获取 id 为  iv_pwd_switch 的 ImageView 控件
        final ImageView ivPwdSwitch = (ImageView)findViewById(R.id.iv_pwd_switch);
//        //看看是不是真的获取到了 ImageView 控件
//        Toast.makeText(MainActivity.this, "here is a " + ivPwdSwitch.getId(),
//                Toast.LENGTH_SHORT).show();
        //获取 id 为  et_pwd 的 EditText 控件
        final EditText etPwd = findViewById(R.id.et_pwd);
        //设置鼠标监听
        ivPwdSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bPwdSwitch = !bPwdSwitch;//点击之后切换状态
                //可以看到密码
                if (bPwdSwitch) {
                    //设置图片
                    ivPwdSwitch.setImageResource(
                            R.drawable.ic_visibility_black_24dp);
                    //设置文本可见
                    etPwd.setInputType(
                            InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                } else {//不可以看到密码
                    //设置图片
                    ivPwdSwitch.setImageResource(
                            R.drawable.ic_visibility_off_black_24dp);
                    //设置文本不可见
                    etPwd.setInputType(
                            InputType.TYPE_TEXT_VARIATION_PASSWORD |
                                    InputType.TYPE_CLASS_TEXT);
                    //设置字体
                    etPwd.setTypeface(Typeface.DEFAULT);
                }
            }
        });
    }
}
