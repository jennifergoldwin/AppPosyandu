package com.example.appposyandu.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.amulyakhare.textdrawable.util.ColorGenerator
import com.example.appposyandu.R
import com.example.appposyandu.data.DaftarJadwal
import com.example.appposyandu.data.DaftarPeserta
import com.example.appposyandu.holder.MainViewHolder
import java.util.*

class MainAdapter(private val context: android.content.Context,
                  private val daftarPeserta: ArrayList<DaftarPeserta?>?) : RecyclerView.Adapter<MainViewHolder>() {
    private val listener: FirebaseDataListener
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.daftar_peserta, parent, false)
        return MainViewHolder(view)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        val generator = ColorGenerator.MATERIAL //custom color
        val color = generator.randomColor
        holder.view.setCardBackgroundColor(color)
        holder.nomorPeserta.text = "Nomor ID : " + (daftarPeserta?.get(position)?.nomor)
        holder.namaanakPeserta.text = "Nama Anak : " + (daftarPeserta?.get(position)?.namaanak)
        holder.namaibuPeserta.text = "Nama Ibu : " + (daftarPeserta?.get(position)?.namaibu)
        holder.jeniskelaminPeserta.text = "Jenis Kelamin : " + (daftarPeserta?.get(position)?.jeniskelamin)
        holder.ttlPeserta.text = "Tanggal Lahir : " + (daftarPeserta?.get(position)?.ttl)
        holder.alamatPeserta.text = "Alamat : " + (daftarPeserta?.get(position)?.alamat)
        holder.view.setOnClickListener { listener.onDataClick(daftarPeserta?.get(position), position) }
    }

    override fun getItemCount(): Int {

        return daftarPeserta?.size!!
    }

    //interface data listener
    interface FirebaseDataListener {
        fun onDataClick(peserta: DaftarPeserta?, position: Int)
    }

    init {
        listener = context as FirebaseDataListener
    }
}