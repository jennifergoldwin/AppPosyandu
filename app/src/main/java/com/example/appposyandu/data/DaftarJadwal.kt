package com.example.appposyandu.data

class DaftarJadwal {

    var key: String? = null
    var tanggal: String? = null
    var jam: String? = null
    var tempat: String? = null
    var kegiatan: String? = null

    constructor() {}

    constructor(tanggalPelaksanaan: String?, jamPelaksanaan: String?, tempatPelaksanaan: String?, kegiatanPelaksanaan: String?) {
        tanggal = tanggalPelaksanaan
        jam = jamPelaksanaan
        tempat = tempatPelaksanaan
        kegiatan = kegiatanPelaksanaan
    }
}