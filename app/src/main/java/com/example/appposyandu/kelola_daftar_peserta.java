package com.example.appposyandu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import com.example.appposyandu.adapter.MainAdapter;
import com.example.appposyandu.data.DaftarPeserta;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class kelola_daftar_peserta extends AppCompatActivity implements MainAdapter.FirebaseDataListener {

    private ExtendedFloatingActionButton mFloatingActionButton;
    private EditText mEditNomorID;
    private EditText mEditNamaAnak;
    private EditText mEditNamaIbu;
    private EditText mEditJenisKelamin;
    private EditText mEditTanggalLahir;
    private EditText mEditAlamat;
    private RecyclerView mRecyclerView;
    private MainAdapter mAdapter;
    private ArrayList<DaftarPeserta> daftarPeserta;
    private DatabaseReference mDatabaseReference;
    private FirebaseDatabase mFirebaseInstance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kelola_daftar_peserta);

        if (Build.VERSION.SDK_INT >= 19 && Build.VERSION.SDK_INT < 21) {
            setWindowFlag(this, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, true);
        }
        if (Build.VERSION.SDK_INT >= 19) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                getWindow().getDecorView()
                        .setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                                | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            }
        }

        if (Build.VERSION.SDK_INT >= 21) {
            setWindowFlag(this, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, false);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }

        mRecyclerView = findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        FirebaseApp.initializeApp(this);
        mFirebaseInstance = FirebaseDatabase.getInstance();
        mDatabaseReference = mFirebaseInstance.getReference("peserta");
        mDatabaseReference.child("data_peserta").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                daftarPeserta = new ArrayList<>();
                for (DataSnapshot mDataSnapshot : dataSnapshot.getChildren()) {
                    DaftarPeserta peserta = mDataSnapshot.getValue(DaftarPeserta.class);
                    peserta.setKey(mDataSnapshot.getKey());
                    daftarPeserta.add(peserta);
                }

                mAdapter = new MainAdapter(kelola_daftar_peserta.this, daftarPeserta);
                mRecyclerView.setAdapter(mAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

                Toast.makeText(kelola_daftar_peserta.this,
                        databaseError.getDetails() + " " + databaseError.getMessage(), Toast.LENGTH_LONG).show();
            }

        });

        mFloatingActionButton = findViewById(R.id.tambah_peserta);
        mFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialogTambahPeserta();
            }
        });
    }

    public static void setWindowFlag(Activity activity, final int bits, boolean on) {

        Window win = activity.getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }

    @Override
    public void onDataClick(final DaftarPeserta peserta, int position) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Pilih Aksi");

        builder.setPositiveButton("UPDATE", (dialog, id) -> {
            dialogUpdatePeserta(peserta);
        });

        builder.setNegativeButton("HAPUS", (dialog, id) -> {
            hapusDataPeserta(peserta);
        });

        builder.setNeutralButton("BATAL", (dialog, id) -> { dialog.dismiss(); });

        Dialog dialog = builder.create();
        dialog.show();
    }

    private void dialogTambahPeserta() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Tambah Data Daftar Peserta");
        View view = getLayoutInflater().inflate(R.layout.layout_edit_peserta, null);

        mEditNomorID = view.findViewById(R.id.noID);
        mEditNamaAnak = view.findViewById(R.id.namaAnak);
        mEditNamaIbu = view.findViewById(R.id.namaIbu);
        mEditJenisKelamin = view.findViewById(R.id.jeniskelamin);
        mEditTanggalLahir = view.findViewById(R.id.ttl);
        mEditAlamat = view.findViewById(R.id.alamat);
        builder.setView(view);

        builder.setPositiveButton("SIMPAN", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {

                String nomorPeserta = mEditNomorID.getText().toString();
                String namaanakPeserta = mEditNamaAnak.getText().toString();
                String namaibuPeserta = mEditNamaIbu.getText().toString();
                String jeniskelaminPeserta = mEditJenisKelamin.getText().toString();
                String ttlPeserta = mEditTanggalLahir.getText().toString();
                String alamatPeserta = mEditAlamat.getText().toString();

                if (!nomorPeserta.isEmpty() && !namaanakPeserta.isEmpty() && !namaibuPeserta.isEmpty() && !jeniskelaminPeserta.isEmpty() && !ttlPeserta.isEmpty() && !alamatPeserta.isEmpty()) {
                    submitDataPeserta(new DaftarPeserta(nomorPeserta, namaanakPeserta, namaibuPeserta, jeniskelaminPeserta, ttlPeserta, alamatPeserta));
                } else {
                    Toast.makeText(kelola_daftar_peserta.this, "Data harus diisi!", Toast.LENGTH_LONG).show();
                }
            }
        });

        builder.setNegativeButton("BATAL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });
        Dialog dialog = builder.create();
        dialog.show();
    }

    private void dialogUpdatePeserta(final DaftarPeserta peserta) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Edit Data Daftar Peserta");
        View view = getLayoutInflater().inflate(R.layout.layout_edit_peserta, null);

        mEditNomorID = view.findViewById(R.id.noID);
        mEditNamaAnak = view.findViewById(R.id.namaAnak);
        mEditNamaIbu = view.findViewById(R.id.namaIbu);
        mEditJenisKelamin = view.findViewById(R.id.jeniskelamin);
        mEditTanggalLahir = view.findViewById(R.id.ttl);
        mEditAlamat = view.findViewById(R.id.alamat);

        mEditNomorID.setText(peserta.getNomor());
        mEditNamaAnak.setText(peserta.getNamaanak());
        mEditNamaIbu.setText(peserta.getNamaibu());
        mEditJenisKelamin.setText(peserta.getJeniskelamin());
        mEditTanggalLahir.setText(peserta.getTtl());
        mEditAlamat.setText(peserta.getAlamat());
        builder.setView(view);

        if (peserta != null) {
            builder.setPositiveButton("SIMPAN", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int id) {
                    peserta.setNomor(mEditNomorID.getText().toString());
                    peserta.setNamaanak(mEditNamaAnak.getText().toString());
                    peserta.setNamaibu(mEditNamaIbu.getText().toString());
                    peserta.setJeniskelamin(mEditJenisKelamin.getText().toString());
                    peserta.setTtl(mEditTanggalLahir.getText().toString());
                    peserta.setAlamat(mEditAlamat.getText().toString());
                    updateDataPeserta(peserta);
                }
            });
        }

        builder.setNegativeButton("BATAL", (dialog, id) -> { dialog.dismiss(); });
        Dialog dialog = builder.create();
        dialog.show();

    }

    private void submitDataPeserta(DaftarPeserta peserta) {
        mDatabaseReference.child("data_peserta").push()
                .setValue(peserta).addOnSuccessListener(this, new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void mVoid) {
                Toast.makeText(kelola_daftar_peserta.this, "Data peserta berhasil disimpan!", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void updateDataPeserta(DaftarPeserta peserta) {
        mDatabaseReference.child("data_peserta").child(peserta.getKey())
                .setValue(peserta).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void mVoid) {
                Toast.makeText(kelola_daftar_peserta.this, "Data berhasil diupdate!", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void hapusDataPeserta(DaftarPeserta peserta) {
        if (mDatabaseReference != null) {
            mDatabaseReference.child("data_peserta").child(peserta.getKey())
                    .removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void mVoid) {
                    Toast.makeText(kelola_daftar_peserta.this, "Data berhasil dihapus!", Toast.LENGTH_LONG).show();
                }
            });
        }
    }
}