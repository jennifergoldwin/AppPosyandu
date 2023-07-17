package com.example.appposyandu.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.amulyakhare.textdrawable.util.ColorGenerator
import com.example.appposyandu.R
import com.example.appposyandu.data.DaftarJadwal
import com.example.appposyandu.holder.MainViewHolderJadwal
import java.util.*

class MainAdapterJadwal(private val context: android.content.Context,
                  private val daftarJadwal: ArrayList<DaftarJadwal?>?) : RecyclerView.Adapter<MainViewHolderJadwal>() {
    private val listener: FirebaseDataListener
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolderJadwal {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.daftar_jadwal, parent, false)
        return MainViewHolderJadwal(view)
    }

    override fun onBindViewHolder(holder: MainViewHolderJadwal, position: Int) {
        val generator = ColorGenerator.MATERIAL //custom color
        val color = generator.randomColor
        holder.viewjadwal.setCardBackgroundColor(color)
        holder.tanggalPelaksanaan.text = "Tanggal : " + (daftarJadwal?.get(position)?.tanggal)
        holder.jamPelaksanaan.text = "Jam : " + (daftarJadwal?.get(position)?.jam)
        holder.tempatPelaksanaan.text = "Tempat : " + (daftarJadwal?.get(position)?.tempat)
        holder.kegiatanPelaksanaan.text = "Kegiatan : " + (daftarJadwal?.get(position)?.kegiatan)
    }

    override fun getItemCount(): Int {

        return daftarJadwal?.size!!
    }

    //interface data listener
    interface FirebaseDataListener {
        fun onDataClick(jadwal: DaftarJadwal?, position: Int)
    }

    init {
        listener = context as FirebaseDataListener
    }
}