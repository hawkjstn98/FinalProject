package data

class Product{
    var idToko: Int
    var jumlah: Int
    var warna: String
    var ukuran: Int
    var produkId: String

    constructor (idToko : Int, jumlah : Int, warna : String, ukuran : Int, produkId : String) {
        this.idToko = idToko
        this.warna = warna
        this.jumlah = jumlah
        this.ukuran = ukuran
        this.produkId = produkId
    }
}