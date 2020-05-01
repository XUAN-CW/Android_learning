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

//        Toast.makeText(MainActivity.this, "here is MainActivity",
//                Toast.LENGTH_SHORT).show();

        final EditText etMessage = findViewById(R.id.message);
         Button btSend = findViewById(R.id.send_message);

//        btSend.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                String message = etMessage.getText().toString();
//                Toast.makeText(MainActivity.this ,message
//                         , Toast.LENGTH_SHORT).show();
//            }
//        });

        btSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String message = etMessage.getText().toString();
                Intent intent = new Intent(MainActivity.this,
                        MessageActivity.class);
                intent.putExtra("MESSAGE_STRING", message);
                startActivity(intent);
            }
        });
    }
}
