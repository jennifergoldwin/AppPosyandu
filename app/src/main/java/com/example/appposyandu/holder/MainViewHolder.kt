package com.example.appposyandu.holder

import android.view.View
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.appposyandu.R

class MainViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    @JvmField
    var nomorPeserta: TextView

    annotation class JvmField

    @JvmField
    var namaanakPeserta: TextView

    @JvmField
    var namaibuPeserta: TextView

    @JvmField
    var jeniskelaminPeserta: TextView

    @JvmField
    var ttlPeserta: TextView

    @JvmField
    var alamatPeserta: TextView

    @JvmField
    var view: CardView

    init {
        nomorPeserta = itemView.findViewById(R.id.nomor_ID)
        namaanakPeserta = itemView.findViewById(R.id.nama_anak)
        namaibuPeserta = itemView.findViewById(R.id.nama_ibu)
        jeniskelaminPeserta = itemView.findViewById(R.id.jenis_kelamin)
        ttlPeserta = itemView.findViewById(R.id.tanggal_lahir)
        alamatPeserta = itemView.findViewById(R.id.alamat_peserta)
        view = itemView.findViewById(R.id.cvMain)
    }
}