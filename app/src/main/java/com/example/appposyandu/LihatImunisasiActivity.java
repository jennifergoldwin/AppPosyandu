package com.example.appposyandu;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;

public class LihatImunisasiActivity extends AppCompatActivity {

    DatabaseReference databaseReference;
    ValueEventListener eventListener;
    RecyclerView recyclerView;
    List<DaftarImunisasi> dataList;
    MyAdapterImunisasi adapterImunisasi;
    SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lihat_imunisasi);

        recyclerView = findViewById(R.id.recyclerViewImunisasi);
        searchView = findViewById(R.id.searchImunisasi);
        searchView.clearFocus();
        GridLayoutManager gridLayoutManager = new GridLayoutManager(LihatImunisasiActivity.this, 1);
        recyclerView.setLayoutManager(gridLayoutManager);
        AlertDialog  .Builder builder = new AlertDialog.Builder(LihatImunisasiActivity.this);
        builder.setCancelable(false);
        builder.setView(R.layout.progress_layout);
        AlertDialog dialog = builder.create();
        dialog.show();
        dataList = new ArrayList<>();
        adapterImunisasi = new MyAdapterImunisasi(LihatImunisasiActivity.this, dataList);
        recyclerView.setAdapter(adapterImunisasi);
        databaseReference = FirebaseDatabase.getInstance().getReference("imunisasi");
        dialog.show();
        eventListener = databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@Nonnull DataSnapshot snapshot) {
                dataList.clear();
                for (DataSnapshot itemSnapshot: snapshot.getChildren()){
                    DaftarImunisasi daftarImunisasi = itemSnapshot.getValue(DaftarImunisasi.class);
                    daftarImunisasi.setKey(itemSnapshot.getKey());
                    dataList.add(daftarImunisasi);
                }
                adapterImunisasi.notifyDataSetChanged();
                dialog.dismiss();
            }
            @Override
            public void onCancelled(@Nonnull DatabaseError error) {
                dialog.dismiss();
            }
        });
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                searchList(newText);
                return true;
            }
        });
    }
    public void searchList(String text){
        ArrayList<DaftarImunisasi> searchList = new ArrayList<>();
        for (DaftarImunisasi daftarImunisasi: dataList){
            if (daftarImunisasi.getUrutan().toLowerCase().contains(text.toLowerCase())){
                searchList.add(daftarImunisasi);
            }
        }
        adapterImunisasi.searchDataList(searchList);
    }
}