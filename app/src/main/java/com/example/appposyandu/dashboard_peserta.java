 package com.example.appposyandu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

 public class dashboard_peserta extends AppCompatActivity {

    CardView DaftarPeserta, JadwalPelaksanaan, TumbuhKembang, Imunisasi, btnLogoutPeserta;
    private FirebaseFirestore db;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_peserta);
        DaftarPeserta=(CardView) findViewById(R.id.DaftarPeserta);
        JadwalPelaksanaan=(CardView) findViewById(R.id.JadwalPelaksanaan);
        TumbuhKembang=(CardView) findViewById(R.id.TumbuhKembang);
        Imunisasi=(CardView) findViewById(R.id.Imunisasi);
        db=FirebaseFirestore.getInstance();

        mAuth=FirebaseAuth.getInstance();
        btnLogoutPeserta=(CardView) findViewById(R.id.btnLogoutPeserta);

        DaftarPeserta.setOnClickListener(view -> {
            Intent intent = new Intent(dashboard_peserta.this, lihat_daftar_peserta.class);
            startActivity(intent);
        });

        JadwalPelaksanaan.setOnClickListener(view -> {
            Intent intent = new Intent(dashboard_peserta.this, lihat_jadwal_pelaksanaan.class);
            startActivity(intent);
        });

        TumbuhKembang.setOnClickListener(view -> {
            Intent intent = new Intent(dashboard_peserta.this, ChartTumbuhKembangActivity.class);
            startActivity(intent);
        });

        Imunisasi.setOnClickListener(view -> {
            Intent intent = new Intent(dashboard_peserta.this, LihatImunisasiActivity.class);
            startActivity(intent);
        });

        btnLogoutPeserta.setOnClickListener(view -> {
            mAuth.signOut();
            Intent intent = new Intent(dashboard_peserta.this, login.class);
            startActivity(intent);
            finish();
            Toast.makeText(dashboard_peserta.this, "Berhasil keluar!", Toast.LENGTH_SHORT).show();
        });
    }
}