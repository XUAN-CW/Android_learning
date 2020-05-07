package com.example.lab02_counter;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.view.View;
import android.widget.Toast;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private int count = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        Toast.makeText(MainActivity.this, "here is 33",
//                Toast.LENGTH_SHORT).show();

        Button btnShowToast = findViewById(R.id.btnShowToast);
        btnShowToast.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "here is toast",
                        Toast.LENGTH_SHORT).show();
            }
        });

        Button btnCount = findViewById(R.id.btnCount);
        final TextView tvCount = findViewById(R.id.tvTextView);
        btnCount.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                tvCount.setText(Integer.toString(++count));
            }
        });



    }
}
