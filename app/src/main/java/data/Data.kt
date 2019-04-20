package data

interface urlData{
    fun callUrl(): String{
        return "http://localhost/getData/getstockToko.php"
    }
}

interface param{
    fun callParamToko(): String{
        return "?userName=MarioWibu&password=18FD9299941BC42015D5B54DA25B8BCEAA9ED3559C57FFB95442C38842964504"
    }

}
