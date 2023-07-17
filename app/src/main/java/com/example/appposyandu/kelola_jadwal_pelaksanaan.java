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

import com.example.appposyandu.adapter.MainAdapterJadwal;
import com.example.appposyandu.data.DaftarJadwal;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class kelola_jadwal_pelaksanaan extends AppCompatActivity implements MainAdapterJadwal.FirebaseDataListener {

    private ExtendedFloatingActionButton mFloatingActionButtonJadwal;
    private EditText mEditTanggalPelaksanaan;
    private EditText mEditJamPelaksanaan;
    private EditText mEditTempatPelaksanaan;
    private EditText mEditKegiatanPosyandu;
    private RecyclerView mRecyclerViewJadwal;
    private MainAdapterJadwal mAdapterJadwal;
    private ArrayList<DaftarJadwal> daftarJadwal;
    private DatabaseReference mDatabaseReferenceJadwal;
    private FirebaseDatabase mFirebaseInstanceJadwal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kelola_jadwal_pelaksanaan);

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

        mRecyclerViewJadwal = findViewById(R.id.recycler_view_tambah_jadwal);
        mRecyclerViewJadwal.setHasFixedSize(true);
        mRecyclerViewJadwal.setLayoutManager(new LinearLayoutManager(this));

        FirebaseApp.initializeApp(this);
        mFirebaseInstanceJadwal = FirebaseDatabase.getInstance();
        mDatabaseReferenceJadwal = mFirebaseInstanceJadwal.getReference("jadwal");
        mDatabaseReferenceJadwal.child("data_jadwal").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                daftarJadwal = new ArrayList<>();
                for (DataSnapshot mDataSnapshot : dataSnapshot.getChildren()) {
                    DaftarJadwal jadwal = mDataSnapshot.getValue(DaftarJadwal.class);
                    jadwal.setKey(mDataSnapshot.getKey());
                    daftarJadwal.add(jadwal);
                }

                mAdapterJadwal = new MainAdapterJadwal(kelola_jadwal_pelaksanaan.this, daftarJadwal);
                mRecyclerViewJadwal.setAdapter(mAdapterJadwal);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

                Toast.makeText(kelola_jadwal_pelaksanaan.this,
                        databaseError.getDetails() + " " + databaseError.getMessage(), Toast.LENGTH_LONG).show();
            }

        });

        mFloatingActionButtonJadwal = findViewById(R.id.tambah_jadwal);
        mFloatingActionButtonJadwal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialogTambahJadwal();
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
    public void onDataClick(final DaftarJadwal jadwal, int position) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Pilih Aksi");

        builder.setPositiveButton("UPDATE", (dialog, id) -> {
            dialogUpdateJadwal(jadwal);
        });

        builder.setNegativeButton("HAPUS", (dialog, id) -> {
            hapusDataJadwal(jadwal);
        });

        builder.setNeutralButton("BATAL", (dialog, id) -> { dialog.dismiss(); });

        Dialog dialog = builder.create();
        dialog.show();
    }

    private void dialogTambahJadwal() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Tambah Data Jadwal Pelaksanaan Posyandu");
        View view = getLayoutInflater().inflate(R.layout.layout_edit_jadwal, null);

        mEditTanggalPelaksanaan = view.findViewById(R.id.Tanggal);
        mEditJamPelaksanaan = view.findViewById(R.id.Jam);
        mEditTempatPelaksanaan = view.findViewById(R.id.Tempat);
        mEditKegiatanPosyandu = view.findViewById(R.id.Kegiatan);
        builder.setView(view);

        builder.setPositiveButton("SIMPAN", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {

                String tanggalPelaksanaan = mEditTanggalPelaksanaan.getText().toString();
                String jamPelaksanaan = mEditJamPelaksanaan.getText().toString();
                String tempatPelaksanaan = mEditTempatPelaksanaan.getText().toString();
                String kegiatanPelaksanaan = mEditKegiatanPosyandu.getText().toString();

                if (!tanggalPelaksanaan.isEmpty() && !jamPelaksanaan.isEmpty() && !tempatPelaksanaan.isEmpty() && !kegiatanPelaksanaan.isEmpty()) {
                    submitDataJadwal(new DaftarJadwal(tanggalPelaksanaan, jamPelaksanaan, tempatPelaksanaan, kegiatanPelaksanaan));
                } else {
                    Toast.makeText(kelola_jadwal_pelaksanaan.this, "Data harus diisi!", Toast.LENGTH_LONG).show();
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

    private void dialogUpdateJadwal(final DaftarJadwal jadwal) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Edit Jadwal Pelaksanaan Posyandu");
        View view = getLayoutInflater().inflate(R.layout.layout_edit_jadwal, null);

        mEditTanggalPelaksanaan = view.findViewById(R.id.Tanggal);
        mEditJamPelaksanaan = view.findViewById(R.id.Jam);
        mEditTempatPelaksanaan = view.findViewById(R.id.Tempat);
        mEditKegiatanPosyandu = view.findViewById(R.id.Kegiatan);

        mEditTanggalPelaksanaan.setText(jadwal.getTanggal());
        mEditJamPelaksanaan.setText(jadwal.getJam());
        mEditTempatPelaksanaan.setText(jadwal.getTempat());
        mEditKegiatanPosyandu.setText(jadwal.getKegiatan());
        builder.setView(view);

        if (jadwal != null) {
            builder.setPositiveButton("SIMPAN", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int id) {
                    jadwal.setTanggal(mEditTanggalPelaksanaan.getText().toString());
                    jadwal.setJam(mEditJamPelaksanaan.getText().toString());
                    jadwal.setTempat(mEditTempatPelaksanaan.getText().toString());
                    jadwal.setKegiatan(mEditKegiatanPosyandu.getText().toString());
                    updateDataJadwal(jadwal);
                }
            });
        }

        builder.setNegativeButton("BATAL", (dialog, id) -> { dialog.dismiss(); });
        Dialog dialog = builder.create();
        dialog.show();

    }

    private void submitDataJadwal(DaftarJadwal jadwal) {
        mDatabaseReferenceJadwal.child("data_jadwal").push()
                .setValue(jadwal).addOnSuccessListener(this, new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void mVoid) {
                Toast.makeText(kelola_jadwal_pelaksanaan.this, "Jadwal pelaksanaan posyandu berhasil disimpan!", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void updateDataJadwal(DaftarJadwal jadwal) {
        mDatabaseReferenceJadwal.child("data_jadwal").child(jadwal.getKey())
                .setValue(jadwal).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void mVoid) {
                Toast.makeText(kelola_jadwal_pelaksanaan.this, "Jadwal pelaksanaan berhasil diupdate!", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void hapusDataJadwal(DaftarJadwal jadwal) {
        if (mDatabaseReferenceJadwal != null) {
            mDatabaseReferenceJadwal.child("data_jadwal").child(jadwal.getKey())
                    .removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void mVoid) {
                    Toast.makeText(kelola_jadwal_pelaksanaan.this, "Jadwal pelaksanaan berhasil dihapus!", Toast.LENGTH_LONG).show();
                }
            });
        }
    }
}