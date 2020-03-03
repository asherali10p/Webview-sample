package com.tenpearls.webview;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final EditText etUrl = findViewById(R.id.etUrl);
        Button btnStart = findViewById(R.id.btnStart);

        etUrl.setText(Constants.BASE_URL);
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!etUrl.getText().toString().isEmpty()) {
                    Constants.BASE_URL = etUrl.getText().toString();
                }
                startActivity(new Intent(MainActivity.this, WebViewActivity.class));
            }
        });
    }
}
