package com.example.appposyandu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class tumbuh_kembang extends AppCompatActivity {

    private CardView btnInput, btnHasil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tumbuh_kembang);

        btnInput = (CardView) findViewById(R.id.btnInput);
        btnHasil = (CardView) findViewById(R.id.btnHasil);

        btnInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(tumbuh_kembang.this, InputActivity.class);
                tumbuh_kembang.this.startActivity(intent);
            }
        });

        btnHasil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(tumbuh_kembang.this, HasilActivity.class);
                tumbuh_kembang.this.startActivity(intent);
            }
        });
    }
}