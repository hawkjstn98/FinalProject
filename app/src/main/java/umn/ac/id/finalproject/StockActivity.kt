package umn.ac.id.finalproject

import android.os.AsyncTask
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.*
import data.Product
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.lang.StringBuilder
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL

class StockActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var pAdapter: ProductAdapter
    private var dataList: ArrayList<Product> = ArrayList()
    private var defaultDataList: ArrayList<Product> = ArrayList()

    private lateinit var btnSearch: Button
    private lateinit var txtSearch: EditText

    private var param: String = "?userName=MarioWibu&password=18FD9299941BC42015D5B54DA25B8BCEAA9ED3559C57FFB95442C38842964504"
    private lateinit var requestQueue: RequestQueue

    private inner class FetchData : AsyncTask<String, Void, String>() {
        override fun doInBackground(vararg params: String?): String? {
            var urlConnection: HttpURLConnection? = null
            var bufferedReader: BufferedReader? = null

            var jsonString: String = ""
            try {
                val urlString: String = "http://192.168.0.24/getData/getstockToko.php" + params[0]
                val urlConnect:URL = URL(urlString)

                urlConnection = urlConnect.openConnection() as HttpURLConnection

                urlConnection.requestMethod = "GET"

                urlConnection.connect()

                var lengthOfFile: Int = urlConnection.contentLength

                val inputStream: InputStream = urlConnection.inputStream

                if(inputStream.available() > 0){
                    return null
                }

                bufferedReader = BufferedReader(InputStreamReader(inputStream))

                val line: String = bufferedReader.readLine()

                jsonString = line

                Log.d("FETCHDATA", jsonString)

                val jsonObject: JSONObject = JSONObject(jsonString)

                val statusCode: String = jsonObject.getString("success")

                if(statusCode.equals("success")){
                    val jsonArray: JSONArray = jsonObject.getJSONArray("data")

                    for(i: Int in 0 until (jsonArray.length())){
                        val theData: JSONObject = jsonArray.getJSONObject(i)

                        val product: Product = Product(
                            theData.getInt("tokoid"),
                            theData.getInt("jumlah"),
                            theData.getString("warna"),
                            theData.getInt("ukuran"),
                            theData.getString("produkid")
                        )

                        dataList.add(product)
                    }
                }
            }
            catch (e: MalformedURLException){
                Log.e("MALFORMED", "MalformedURLException" + e.message)
            }
            catch (e: IOException){
                Log.e("IO", "IOException" + e.message)
            }
            catch (e: JSONException){
                e.printStackTrace()
            }
            finally {
                urlConnection?.disconnect()

                if (bufferedReader != null) {
                    try {
                        bufferedReader.close()
                    } catch (e: IOException) {
                        Log.e("BUFFEREDIOEXCEPTION", "IOException" + e.message)
                    }
                }
            }
            return jsonString
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stock)

        requestQueue = Volley.newRequestQueue(this)

        dataList = ArrayList()
        FetchData().execute(param)
        defaultDataList.addAll(dataList)

        pAdapter = ProductAdapter(dataList, this@StockActivity)

        recyclerView = findViewById(R.id.recycler_list_sendal)
        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(this@StockActivity)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = pAdapter

        btnSearch = findViewById(R.id.main2_search_button)
        txtSearch = findViewById(R.id.main2_search_edittext)

        btnSearch.setOnClickListener {
            val newList: ArrayList<Product> = ArrayList()

            if(!txtSearch.text.equals("")){
                for(data: Product in dataList){
                    if(data.produkId.toLowerCase().equals(txtSearch.text)){
                        newList.add(data)
                    }
                }
                if(newList.size > 0){
                    dataList = ArrayList()
                    dataList.addAll(newList)
                    pAdapter.updateList(dataList)
                }
                else{
                    Toast.makeText(this@StockActivity, "Nothing Found", Toast.LENGTH_SHORT)
                }
            }
            else{
                dataList = ArrayList()
                dataList.addAll(defaultDataList)
                pAdapter.updateList(dataList)
            }
        }
    }

    /*
    fun addData(){
        val cache = DiskBasedCache(cacheDir, 1024 * 1024)

        val network = BasicNetwork(HurlStack())

        val requestQueue : RequestQueue = RequestQueue(cache, network).apply { start() }

        val stringRequest = StringRequest(
            Request.Method.GET,
            url,
            Response.Listener<String>() {
                val res = JSONObject(it)
                val status: JSONObject = res.getJSONObject("success")
                val data: JSONObject = res.getJSONObject("data")
                if(status.getString("success").equals("success")){
                    for(i: Int in 0 until (data.length() - 1)){
                        val newProd = Product(data.getInt("tokoid"), data.getInt("jumlah"), data.getString("warna"), data.getInt("ukuran"), data.getString("produkid"))
                        dataList.add(newProd)
                    }
                    pAdapter.updateList(dataList)
                }
                else{
                    Log.e("ResponseUnknown", "Unknown Response")
                }
            },
            Response.ErrorListener {

            }
        )
        requestQueue.add(stringRequest)v
    }
    */

}
