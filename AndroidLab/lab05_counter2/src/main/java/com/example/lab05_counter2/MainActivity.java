package com.example.lab05_counter2;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.view.View;
import android.widget.Toast;
import android.widget.TextView;
import android.widget.EditText;
import android.content.Intent;

public class MainActivity extends AppCompatActivity {

    private int count = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnCount = findViewById(R.id.btnCount);
        final TextView tvCount = findViewById(R.id.tvTextView);
        btnCount.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                tvCount.setText(Integer.toString(++count));
            }
        });
    }

     private static final  String COUNT_VALUE = "count_value";
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(COUNT_VALUE, count);
        super.onSaveInstanceState(outState);
    }
//     TextView tvCount;
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        count = savedInstanceState.getInt(COUNT_VALUE);
        final TextView tvCount = findViewById(R.id.tvTextView);
        if (tvCount != null) {
            tvCount.setText(Integer.toString(count));
        }
    }
}
