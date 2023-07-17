package com.example.appposyandu.data

class DaftarPeserta {

    var key: String? = null
    var nomor: String? = null
    var namaanak: String? = null
    var namaibu: String? = null
    var jeniskelamin: String? = null
    var ttl: String? = null
    var alamat: String? = null

    constructor() {}

    constructor(nomorPeserta: String?, namaanakPeserta: String?, namaibuPeserta: String?, jeniskelaminPeserta: String?, ttlPeserta: String?, alamatPeserta: String?) {
        nomor = nomorPeserta
        namaanak = namaanakPeserta
        namaibu = namaibuPeserta
        jeniskelamin = jeniskelaminPeserta
        ttl = ttlPeserta
        alamat = alamatPeserta
    }
}