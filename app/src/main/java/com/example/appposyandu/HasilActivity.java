package com.example.appposyandu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class HasilActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private Button btnCek ;
    private EditText edtId;
    private TextView tvNama;
    private TextView tvGender;
    private TextView tvUsia;
    private TextView tvAlamat;
    private TextView tvBerat;
    private TextView tvTinggi;
    private TextView tvNoData;
    private LinearLayout ll_info;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hasil);
        tvNama = findViewById(R.id.tv_nama);
        tvAlamat = findViewById(R.id.tv_alamat);
        tvBerat = findViewById(R.id.tv_berat);
        tvTinggi = findViewById(R.id.tv_tinggi);
        tvGender = findViewById(R.id.tv_gender);
        tvUsia = findViewById(R.id.tv_usia);
        toolbar = findViewById(R.id.toolbar_hasil);
        toolbar.setNavigationIcon(R.drawable.baseline_arrow_back_24);
        toolbar.setNavigationOnClickListener(view ->{
            finish();
        });
        btnCek = findViewById(R.id.act_hasil_btn_cek);
        edtId = findViewById(R.id.act_hasil_edt_id);
        ll_info = findViewById(R.id.ll_info);
        tvNoData = findViewById(R.id.act_hasil_tv_nodata);
        btnCek.setOnClickListener(view -> {
            if (!edtId.getText().toString().isEmpty()){
                cekId(edtId.getText().toString());
            }
        });
    }

    private void cekId(String toString) {

        db.collection("users")
                .document(
                        "XFcYolsNArR8l1umbZfOlKrkBQ23")
                .collection("data-balita")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                               if (document.getData().get("id").equals(toString)){
                                   tvNoData.setVisibility(View.GONE);
                                   ll_info.setVisibility(View.VISIBLE);
                                   tvNama.setText("Nama : "+document.getData().get("nama"));
                                   tvAlamat.setText("Alamat : "+document.getData().get("alamat"));
                                   tvBerat.setText("Berat : "+document.getData().get("berat")+ " kg");
                                   tvGender.setText("Jenis Kelamin : "+document.getData().get("gender"));
                                   tvUsia.setText("Usia : "+document.getData().get("usia")+ " Bulan");
                                   tvTinggi.setText("Tinggi "+document.getData().get("tinggi")+ " cm");
                               }
                            }

                        } else {
                            Toast.makeText(HasilActivity.this,"ERROR",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}