package com.example.appposyandu;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class DetailImunisasiActivity extends AppCompatActivity {
    TextView detailTitle, detailNama, detailTanggal, detailJenis, detailUrutan;
    String key = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_imunisasi);

        detailTitle = findViewById(R.id.detailTitle);
        detailNama = findViewById(R.id.detailNama);
        detailTanggal = findViewById(R.id.detailTanggal);
        detailJenis = findViewById(R.id.detailJenis);
        detailUrutan = findViewById(R.id.detailUrutan);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            detailTitle.setText(bundle.getString("Riwayat Imunisasi"));
            detailNama.setText(bundle.getString("Nama Anak"));
            detailTanggal.setText(bundle.getString("Tanggal Imunisasi"));
            detailJenis.setText(bundle.getString("Jenis Imunisasi"));
            detailUrutan.setText(bundle.getString("Urutan Imunisasi"));
            key = bundle.getString("Key");
        }
    }
}