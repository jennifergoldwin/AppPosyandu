package com.example.appposyandu;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;

public class MyAdapterImunisasi extends RecyclerView.Adapter<MyViewHolder> {

    private Context context;
    private List<DaftarImunisasi> daftarImunisasi;

    public MyAdapterImunisasi(Context context, List<DaftarImunisasi> daftarImunisasi) {
        this.context = context;
        this.daftarImunisasi = daftarImunisasi;
    }

    @Nonnull
    @Override
    public MyViewHolder onCreateViewHolder(@Nonnull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.daftar_imunisasi, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@Nonnull MyViewHolder holder, int position) {
        holder.namaImunisasi.setText(daftarImunisasi.get(position).getNama());
        holder.tanggalImunisasi.setText(daftarImunisasi.get(position).getTanggal());
        holder.urutanImunisasi.setText(daftarImunisasi.get(position).getUrutan());
        holder.jenisImunisasi.setText(daftarImunisasi.get(position).getImunisasi());

        holder.recCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DetailImunisasiActivity.class);
                intent.putExtra("Nama Anak", daftarImunisasi.get(holder.getAdapterPosition()).getNama());
                intent.putExtra("Tanggal Imunisasi", daftarImunisasi.get(holder.getAdapterPosition()).getTanggal());
                intent.putExtra("Imunisasi Ke", daftarImunisasi.get(holder.getAdapterPosition()).getUrutan());
                intent.putExtra("Key",daftarImunisasi.get(holder.getAdapterPosition()).getKey());
                intent.putExtra("Jenis Imunisasi", daftarImunisasi.get(holder.getAdapterPosition()).getImunisasi());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return daftarImunisasi.size();
    }
    public void searchDataList(ArrayList<DaftarImunisasi> searchList){
        daftarImunisasi = searchList;
        notifyDataSetChanged();
    }
}

class MyViewHolder extends RecyclerView.ViewHolder{
    TextView namaImunisasi, tanggalImunisasi, urutanImunisasi, jenisImunisasi;
    CardView recCard;

    public MyViewHolder(@Nonnull View itemView) {
        super(itemView);
        namaImunisasi = itemView.findViewById(R.id.namaImunisasi);
        tanggalImunisasi= itemView.findViewById(R.id.tanggalImunisasi);
        urutanImunisasi = itemView.findViewById(R.id.urutanImunisasi);
        jenisImunisasi = itemView.findViewById(R.id.jenisImunisasi);
    }
}
