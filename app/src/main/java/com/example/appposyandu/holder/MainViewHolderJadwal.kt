package com.example.appposyandu.holder

import android.view.View
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.appposyandu.R

class MainViewHolderJadwal(itemView: View) : RecyclerView.ViewHolder(itemView) {

    @JvmField
    var tanggalPelaksanaan: TextView

    annotation class JvmField

    @JvmField
    var jamPelaksanaan: TextView

    @JvmField
    var tempatPelaksanaan: TextView

    @JvmField
    var kegiatanPelaksanaan: TextView

    @JvmField
    var viewjadwal: CardView

    init {
        tanggalPelaksanaan = itemView.findViewById(R.id.tanggal_pelaksanaan)
        jamPelaksanaan = itemView.findViewById(R.id.jam_pelaksanaan)
        tempatPelaksanaan = itemView.findViewById(R.id.tempat_pelaksanaan)
        kegiatanPelaksanaan = itemView.findViewById(R.id.kegiatan_posyandu)
        viewjadwal = itemView.findViewById(R.id.cvMain_jadwal)
    }
}