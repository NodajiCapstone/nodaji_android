package com.example.nodaji;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView name = findViewById(R.id.name);

        Intent intent = getIntent();
        String strUserName = intent.getStringExtra("name");
        System.out.println("main " + strUserName);
        name.setText(strUserName);

    }
}
