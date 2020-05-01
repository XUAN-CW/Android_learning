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
//        Toast.makeText(MessageActivity.this, "here is MessageActivity",
//                Toast.LENGTH_SHORT).show();
        Intent intent = getIntent();
        String message = intent.getStringExtra(
                "MESSAGE_STRING");
//        Toast.makeText(MessageActivity.this, "here is "+message,
//                Toast.LENGTH_SHORT).show();

        final TextView tvMessage = findViewById(R.id.message);
        if (message != null) {
            if (tvMessage != null) {
                tvMessage.setText(message);
            }
        }
    }
}




