package data

interface urlData{
    fun callUrl(): String{
        return baseUrl()+"/getData/getstockToko.php";
    }
    fun callUrlFactory(): String{
        return baseUrl()+"/getData/getstockPabrik.php";
    }
}

interface param{
    fun callParamToko(): String{
        return "?userName=MarioWibu&password=A559C7CA6258E603E59125333FFF381496982D96CFB54B691A5E84DBFDF2B475"
    }

    fun callParamTransactionToko(): String{
        return "?userName=MWB&Password=MWB";
    }

    fun callParamTransactionTokoData(id: String, data:String): String{
        return "?transactionid="+id+"&jsonString="+data;
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
