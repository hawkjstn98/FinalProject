package umn.ac.id.finalproject

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
import org.json.JSONObject

class StockActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var pAdapter: ProductAdapter
    private var dataList: ArrayList<Product> = ArrayList()
    private var defaultDataList: ArrayList<Product> = ArrayList()

    private lateinit var btnSearch: Button
    private lateinit var txtSearch: EditText

    private var url: String = "http://localhost/getData/getstockToko.php?userName=MarioWibu&password=18FD9299941BC42015D5B54DA25B8BCEAA9ED3559C57FFB95442C38842964504"
    private lateinit var requestQueue: RequestQueue

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stock)

        requestQueue = Volley.newRequestQueue(this)

        dataList = ArrayList()
        addData()
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

    fun addData(){
        val cache = DiskBasedCache(cacheDir, 1024 * 1024)

        val network = BasicNetwork(HurlStack())

        val requestQueue : RequestQueue = RequestQueue(cache, network).apply { start() }

        val jsonObjectRequest = JsonObjectRequest(Request.Method.GET, url, null, Response.Listener { response ->
            val jsonArray: JSONArray = response.getJSONArray("data")
            for(i in 0..(jsonArray.length() - 1)){
                val data: JSONObject = jsonArray.getJSONObject(i)
                val product = Product(data.getInt("tokoid"), data.getInt("jumlah"), data.getString("warna"), data.getInt("ukuran"), data.getString("produkid"))
                dataList.add(product)
            }
        },
            Response.ErrorListener { error ->
                Log.e("REPONSEERROR", error.message)
        })

        requestQueue.add(jsonObjectRequest)
    }
}
