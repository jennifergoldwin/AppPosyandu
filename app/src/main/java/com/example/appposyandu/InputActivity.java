package com.example.appposyandu;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class InputActivity extends AppCompatActivity {
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private EditText nama;
    private EditText alamat;
    private EditText usia;
    private String gender="";
    private String type = "";
    private RadioGroup radioGroupGender;
    private RadioGroup radioGroupJenis;
    private Button btnKirim;
    private String berat;
    private String tinggi;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private ImageView btnBack;
    private TextView textId;
    private ProgressDialog progressDialog;
    private final String SCRIPT_URL = "https://script.google.com/macros/s/AKfycbzxC5ejwxaWGH8JOLhPEJWUCpRDiogVVpx01oCri9PzQLo0Cam5La-9o-QvxFOm4fzv/exec";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input);
        btnBack = findViewById(R.id.act_input_back_btn);
        btnBack.setOnClickListener(view -> finish());
        radioGroupGender = findViewById(R.id.radio_group_gender);
        radioGroupJenis = findViewById(R.id.radio_group_jenis);
        btnKirim = findViewById(R.id.act_input_btn_kirim);
        nama = findViewById(R.id.act_input_edt_nama);
        alamat = findViewById(R.id.act_input_edt_alamat);
        usia = findViewById(R.id.act_input_edt_umur);
        textId = findViewById(R.id.tv_id);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");

        radioGroupGender.setOnCheckedChangeListener((radioGroup, i) -> {
            gender = ((RadioButton) findViewById(i)).getText().toString();
        });
        radioGroupJenis.setOnCheckedChangeListener((radioGroup, i) -> {

            type = ((RadioButton) findViewById(i)).getText().toString().equals("Balita") ? "ESP32_Balita" : "ESP32_Bayi";

        });
        btnKirim.setOnClickListener(view -> {
            if (!nama.getText().toString().isEmpty() && !alamat.getText().toString().isEmpty() && !usia.getText().toString().isEmpty() && !type.isEmpty() && !gender.isEmpty()){
                progressDialog.show();
                getESP32Data(type);
            }
        });
    }
    private String generateId(){
        int max = 10;
        int min = 1;
        int range = max - min + 1;
        String rand = "";
        for (int i = 0; i < 4; i++) {
            rand += (int)(Math.random() * range) + min;
        }
        return rand;
    }
    private void addData(){
        String id = String.valueOf(generateId());
        Map<String, String> data = new HashMap<>();
        data.put("id",id );
        data.put("berat", berat);
        data.put("tinggi", tinggi);
        data.put("nama", nama.getText().toString());
        data.put("alamat",alamat.getText().toString());
        data.put("gender",gender);
        data.put("usia", usia.getText().toString());
        data.put("tipe",type.equals("ESP32_Balita")?"Balita":"Bayi");

        db.collection("users").document(
                        "XFcYolsNArR8l1umbZfOlKrkBQ23")
                .collection("data-balita")
                .add(data)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        textId.setVisibility(View.VISIBLE);
                        textId.setText("ID: "+id);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                    }
                }).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentReference> task) {
                        addToSpreadSheet(data);
                        Toast.makeText(InputActivity.this,"Berhasil",Toast.LENGTH_SHORT).show();
                    }
                });
    }
    private void addToSpreadSheet(Map<String,String> data){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, SCRIPT_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.hide();
                nama.setText("");
                nama.setFocusable(false);
                alamat.setText("");
                alamat.setFocusable(false);
                usia.setText("");
                usia.setFocusable(false);
                RadioButton btnGender = findViewById(radioGroupGender.getCheckedRadioButtonId());
                btnGender.setChecked(false);
                RadioButton btnJenis = findViewById(radioGroupJenis.getCheckedRadioButtonId());
                btnJenis.setChecked(false);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() {
                String date = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.getDefault()).format(new Date());
                data.put("action","addAnak");
                data.put("date",date);
                return data;
            }
        };
        int timeOut = 50000;
        RetryPolicy retryPolicy = new DefaultRetryPolicy(timeOut,0,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(retryPolicy);

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
    private void getESP32Data(String path){
        DatabaseReference myRef = database.getReference(path);
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Map map  = (Map) dataSnapshot.getValue();
                berat = map.get("berat").toString();
                tinggi = map.get("tinggi").toString();
                addData();
            }

            @Override
            public void onCancelled(DatabaseError error) {
            }
        });
    }
}