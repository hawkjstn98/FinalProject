package umn.ac.id.finalproject

import android.content.Intent
import android.graphics.Bitmap
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import android.widget.Toast.LENGTH_LONG
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.BasicNetwork
import com.android.volley.toolbox.DiskBasedCache
import com.android.volley.toolbox.HurlStack
import com.android.volley.toolbox.StringRequest
import com.google.gson.Gson
import data.UrlTransaction
import kotlinx.android.synthetic.main.activity_transaction_confirmation.*
import data.param
import org.json.JSONObject

class TransactionConfirmationActivity : AppCompatActivity(), UrlTransaction, param {
    lateinit var transactionId: String;
    val REQUEST_IMAGE_CAPTURE=1;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transaction_confirmation)
        btnTambah.setOnClickListener{
            dispatchTakePictureIntent();
        }

        btnConfirm.setOnClickListener{
            val gson = Gson();
            sendData();
        }
    }

    private fun dispatchTakePictureIntent(){
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            takePictureIntent.resolveActivity(packageManager)?.also {
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK){
            val image = data?.extras?.get("data") as Bitmap;
            viewStruk.setImageBitmap(image);
        }
    }

    private fun sendData(){
        val cache = DiskBasedCache(cacheDir, 1024 * 1024);

        val network = BasicNetwork(HurlStack());

        val requestQueue = RequestQueue(cache, network).apply {
            start()
        }

        val url = callUrlTrigger();
        val param = callParamTransactionToko();
        val strings : String = url+param;

        val stringRequest = StringRequest(Request.Method.GET, url+param,
            Response.Listener<String> { response ->
                Log.d("res", response);
                var stringResponse = response.toString();
                val res: JSONObject = JSONObject(response);
                val statusCode: String = res.getString("success");
                if(statusCode.equals("Success")){
                    transactionId  = res.getString("data");
                    Toast.makeText(this, transactionId, LENGTH_LONG).show();
                    Log.d("KONTOL",transactionId);
                }
            },
            Response.ErrorListener { error ->
                    Toast.makeText(this, error.toString(), LENGTH_LONG).show();
            })
        requestQueue.add(stringRequest);
    }

}
