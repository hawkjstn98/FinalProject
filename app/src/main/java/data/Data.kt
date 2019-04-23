package data

interface urlData{
    fun callUrl(): String{
        return "http://192.168.0.24/getData/getstockToko.php"
    }
}

interface param{
    fun callParamToko(): String{
        return "?userName=MarioWibu&password=18FD9299941BC42015D5B54DA25B8BCEAA9ED3559C57FFB95442C38842964504"
    }

    fun callParamTransactionToko(): String{
        return "?userName=MWB&Password=MWB";
    }

    fun callParamTransactionTokoData(id: String, warna: String, ukuran : Int, jumlah: Int, transactionid: String): String{
        return "?transactionid="+transactionid+"&produkid="+id+"&Warna="+warna+"&Ukuran="+ukuran.toString()+"&Jumlah="+jumlah.toString();
    }
}

interface kodeToko{
    fun callKodeToko(): Int{
        return 2;
    }
}

interface UrlTransaction{
    fun callUrlTrigger(): String{
        return baseUrl()+"/transactionData/startTransactionToko.php";
    }

    fun callUrlTransaction(): String{
        return baseUrl()+"/transactionData/transactionToko.php";
    }
}

private fun baseUrl(): String{
    return "http://192.168.1.6";
}
