package com.example.appposyandu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.appposyandu.adapter.MainAdapterJadwal;
import com.example.appposyandu.data.DaftarJadwal;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;

public class lihat_jadwal_pelaksanaan extends AppCompatActivity implements MainAdapterJadwal.FirebaseDataListener {

    private RecyclerView mRecyclerViewJadwal;
    private MainAdapterJadwal mAdapterJadwal;
    private ArrayList<DaftarJadwal> daftarJadwal;
    private DatabaseReference mDatabaseReferenceJadwal;
    private FirebaseDatabase mFirebaseInstanceJadwal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lihat_jadwal_pelaksanaan);

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

        mRecyclerViewJadwal = findViewById(R.id.recycler_view_lihat_jadwal);
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

                mAdapterJadwal = new MainAdapterJadwal(lihat_jadwal_pelaksanaan.this, daftarJadwal);
                mRecyclerViewJadwal.setAdapter(mAdapterJadwal);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

                Toast.makeText(lihat_jadwal_pelaksanaan.this,
                        databaseError.getDetails() + " " + databaseError.getMessage(), Toast.LENGTH_LONG).show();
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
    public void onDataClick(@Nullable DaftarJadwal jadwal, int position) {

    }
}