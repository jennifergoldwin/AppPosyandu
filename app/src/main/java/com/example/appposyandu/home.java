package com.example.appposyandu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class home extends AppCompatActivity {

    Button btnHalamanLogin, btnHalamanRegist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        btnHalamanLogin=(Button)findViewById(R.id.btnHalamanLogin);
        btnHalamanRegist=(Button)findViewById(R.id.btnHalamanRegist);

        btnHalamanLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(home.this, login.class);
                startActivity(intent);
            }
        });

        btnHalamanRegist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View view) {
                Intent intent = new Intent(home.this, register.class);
                startActivity(intent);
            }
        });
    }
}
