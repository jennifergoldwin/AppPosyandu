package com.example.appposyandu;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChartTumbuhKembangActivity extends AppCompatActivity {

    private Spinner tahun;
    private Toolbar toolbar;
    private FloatingActionButton fab;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private LineChart lineChart;
    private LineData lineData;
    private LineDataSet lineDataSet;
    private ArrayList lineEntries;
    private BottomSheetDialog logoutBottomSheet;
    private String thnSpinnerStr="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart_tumbuh_kembang);
        tahun = findViewById(R.id.spinner_tahun);
        toolbar = findViewById(R.id.toolbar_chart);
        fab = findViewById(R.id.fab_add);
        lineChart = findViewById(R.id.lineChart);
        lineChart.getDescription().setEnabled(false);
//        lineEntries = new ArrayList<>();
//        fetchData();
        toolbar.setNavigationIcon(R.drawable.baseline_arrow_back_24);
        toolbar.setNavigationOnClickListener(view ->{
            finish();
        });
        ArrayAdapter<CharSequence> adapter=ArrayAdapter.createFromResource(this, R.array.filter, android.R.layout.simple_spinner_item);
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
//        filter.setAdapter(adapter);
        adapter=ArrayAdapter.createFromResource(this, R.array.tahun, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        tahun.setAdapter(adapter);

        tahun.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                thnSpinnerStr = adapterView.getItemAtPosition(i).toString();
                lineEntries = new ArrayList<>();
                fetchData();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        fab.setOnClickListener(view -> {
            generateBottomSheet();
        });
    }

    private void generateBottomSheet(){
        logoutBottomSheet = new BottomSheetDialog(ChartTumbuhKembangActivity.this);
        logoutBottomSheet.setContentView(R.layout.bottom_sheet);
        logoutBottomSheet.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        logoutBottomSheet.show();

        EditText tgl = logoutBottomSheet.findViewById(R.id.et_tgl);
        EditText tinggi = logoutBottomSheet.findViewById(R.id.et_tb);
        EditText berat = logoutBottomSheet.findViewById(R.id.et_bb);
        Button btn = logoutBottomSheet.findViewById(R.id.btm_sheet_btn_simpan);
        tgl.setOnClickListener(view1 -> {
            Calendar calendar = Calendar.getInstance();
            showDatePicker(calendar,tgl);
        });
        btn.setOnClickListener(view1 -> {
            lineEntries = new ArrayList<>();
            String strDate = tgl.getText().toString();
            String strTb = tinggi.getText().toString();
            String strBb = berat.getText().toString();
            if (!strDate.isEmpty() && !strTb.isEmpty() && !strBb.isEmpty()){
                addData(strDate,strBb,strTb);
            }
        });
    }
    private void addData(String strDate, String strBb, String strTb) {
        Map<String, Object> chart = new HashMap<>();
        chart.put("tanggal", strDate);
        chart.put("berat", strBb);
        chart.put("tinggi", strTb);

        db.collection("users").document(
                        "XFcYolsNArR8l1umbZfOlKrkBQ23")
                .collection("data-chart")
                .add(chart)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        logoutBottomSheet.cancel();
                        Toast.makeText(ChartTumbuhKembangActivity.this,"Berhasil",Toast.LENGTH_SHORT).show();
                        fetchData();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding document", e);
                    }
                });

    }

    private void configureLineChart() {
//        lineEntries = new ArrayList<>();
        lineChart.setDragEnabled(true);
        lineChart.setScaleEnabled(true);
        XAxis xAxis = lineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setGranularityEnabled(true);
        xAxis.setValueFormatter(new ClaimsXAxisValueFormatter());
    }

    private void fetchData(){
        configureLineChart();
        db.collection("users")
                .document(
                        "XFcYolsNArR8l1umbZfOlKrkBQ23")
                .collection("data-chart")
                .whereGreaterThanOrEqualTo("tanggal",thnSpinnerStr)
                .whereLessThanOrEqualTo("tanggal",  thnSpinnerStr+ '\uf8ff')
                .orderBy("tanggal")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Map <String,Object> ob = document.getData();
                            float temp = Float.parseFloat(ob.get("berat").toString()) / (Float.parseFloat(ob.get("tinggi").toString()) * Float.parseFloat(ob.get("tinggi").toString()));
                            lineEntries.add(new Entry(Utils.getMonthInFloat(document.getData().get("tanggal").toString()),temp));
                        }
                        if (lineEntries.size()>0){
                            lineChart.getXAxis().setLabelCount(lineEntries.size(),false);
                            lineDataSet = new LineDataSet(lineEntries, "");
                            lineData = new LineData(lineDataSet);
                            lineChart.setData(lineData);
                            lineDataSet.setColors(ColorTemplate.JOYFUL_COLORS);
                            lineDataSet.setDrawValues(false);
                            lineChart.invalidate();
                        }else{
                            lineChart.clear();
                        }

                    } else {
                        Toast.makeText(ChartTumbuhKembangActivity.this,"ERROR",Toast.LENGTH_SHORT).show();
                    }
                });
    }
    private void showDatePicker(Calendar c, EditText e){
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                ChartTumbuhKembangActivity.this,
                (view, year1, monthOfYear, dayOfMonth) -> {
                    String tempMonth = monthOfYear+1>9 ? String.valueOf(monthOfYear+1) : "0"+(monthOfYear+1);
                    String tempDay = dayOfMonth>9 ? String.valueOf(dayOfMonth) : "0"+dayOfMonth;
                    e.setText(year1+"/"+tempMonth +"/"+ tempDay);
                },
                year, month, day);
        datePickerDialog.show();
    }
}