package com.example.appposyandu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class dashboard_kader extends AppCompatActivity {

    CardView btnDaftarPeserta, btnTambahJadwal, btnKelolaTumbuhKembang, btnKelolaImunisasi, btnLogoutKader;
    private FirebaseFirestore db;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_kader);
        btnDaftarPeserta=(CardView) findViewById(R.id.btnDaftarPeserta);
        btnTambahJadwal=(CardView) findViewById(R.id.btnTambahJadwal);
        btnKelolaTumbuhKembang=(CardView) findViewById(R.id.btnKelolaTumbuhKembang);
        btnKelolaImunisasi=(CardView) findViewById(R.id.btnKelolaImunisasi);
        db= FirebaseFirestore.getInstance();

        mAuth=FirebaseAuth.getInstance();
        btnLogoutKader=(CardView) findViewById(R.id.btnLogoutKader);

        btnDaftarPeserta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(dashboard_kader.this, kelola_daftar_peserta.class);
                startActivity(intent);
            }
        });

        btnTambahJadwal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(dashboard_kader.this, kelola_jadwal_pelaksanaan.class);
                startActivity(intent);
            }
        });

        btnKelolaTumbuhKembang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(dashboard_kader.this, tumbuh_kembang.class);
                startActivity(intent);
            }
        });

        btnKelolaImunisasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(dashboard_kader.this, kelola_imunisasi.class);
                startActivity(intent);
            }
        });

        btnLogoutKader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.signOut();
                Intent intent = new Intent(dashboard_kader.this, login.class);
                startActivity(intent);
                finish();
                Toast.makeText(dashboard_kader.this, "Berhasil keluar!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}