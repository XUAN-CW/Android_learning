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


//        Toast.makeText(MainActivity.this, "here is 3",
//                Toast.LENGTH_SHORT).show();

        final ImageView ivPwdSwitch = (ImageView)findViewById(R.id.iv_pwd_switch);

        Toast.makeText(MainActivity.this, "here is a " + ivPwdSwitch.getId(),
                Toast.LENGTH_SHORT).show();

        final EditText etPwd = findViewById(R.id.et_pwd);

        ivPwdSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bPwdSwitch = !bPwdSwitch;
                if (bPwdSwitch) {
                    ivPwdSwitch.setImageResource(
                            R.drawable.ic_visibility_black_24dp);
                    etPwd.setInputType(
                            InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                } else {
                    ivPwdSwitch.setImageResource(
                            R.drawable.ic_visibility_off_black_24dp);
                    etPwd.setInputType(
                            InputType.TYPE_TEXT_VARIATION_PASSWORD |
                                    InputType.TYPE_CLASS_TEXT);
                    etPwd.setTypeface(Typeface.DEFAULT);
                }
            }
        });
    }
}
