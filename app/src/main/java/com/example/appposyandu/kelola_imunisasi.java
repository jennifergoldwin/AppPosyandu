package com.example.appposyandu;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class kelola_imunisasi extends AppCompatActivity {

    // Variable yang merefers ke firebase realtime database
    private DatabaseReference mDatabase;

    // Variable fields EditText dan Button
    private EditText editNama, editTanggal, editUrutan, editImunisasi;
    private Button dashboard, input;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kelola_imunisasi);

        // Inisialisasi fields EditText dan Button
        editNama = findViewById(R.id.inputNamaImunisasi);
        editTanggal = findViewById(R.id.inputTanggalImunisasi);
        editUrutan = findViewById(R.id.inputUrutanImunisasi);
        editImunisasi = findViewById(R.id.inputJenisImunisasi);
        dashboard = findViewById(R.id.btnDashboardImunisasi);
        input = findViewById(R.id.btnInputImunisasi);

        // Mengambil referensi ke Firebase Database
        mDatabase = FirebaseDatabase.getInstance().getReference();

        // Klik tombol cancel untuk kembali ke dashboard
        dashboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(kelola_imunisasi.this, dashboard_kader.class);
                startActivity(intent);
            }
        });

        input.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isEmpty(editNama.getText().toString()) && !isEmpty(editTanggal.getText().toString()) && !isEmpty(editUrutan.getText().toString()) && !isEmpty(editImunisasi.getText().toString()))
                    submitDaftarImunisasi(new DaftarImunisasi(editNama.getText().toString(), editTanggal.getText().toString(), editUrutan.getText().toString(), editImunisasi.getText().toString()));
                else
                    Snackbar.make(findViewById(R.id.btnInputImunisasi), "Data tidak boleh kosong!", Snackbar.LENGTH_LONG).show();

                InputMethodManager imm = (InputMethodManager)
                        getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(
                        editNama.getWindowToken(), 0);
            }
        });
    }

    private boolean isEmpty(String s) {
        // Cek apakah ada fields yang kosong, sebelum disubmit
        return TextUtils.isEmpty(s);
    }

    private void submitDaftarImunisasi(DaftarImunisasi daftarImunisasi) {
        // Mengirimkan data ke firebase realtime database
        mDatabase.child("imunisasi").push().setValue(daftarImunisasi).addOnSuccessListener(this, new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                editNama.setText("");
                editTanggal.setText("");
                editUrutan.setText("");
                editImunisasi.setText("");
                Snackbar.make(findViewById(R.id.btnInputImunisasi), "Data berhasil ditambahkan", Snackbar.LENGTH_LONG).show();
            }
        });
    }

    public static Intent getActIntent(Activity activity) {
        // kode untuk pengambilan Intent
        return new Intent(activity, kelola_imunisasi.class);
    }
}