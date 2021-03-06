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
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import data.Transaction
import data.UrlTransaction
import kotlinx.android.synthetic.main.activity_transaction_confirmation.*
import data.param
import org.json.JSONObject
import android.graphics.drawable.BitmapDrawable
import android.util.Base64
import com.android.volley.VolleyError
import com.android.volley.toolbox.*
import java.io.ByteArrayOutputStream


class TransactionConfirmationActivity : AppCompatActivity(), UrlTransaction, param {
    lateinit var transactionId: String;
    val REQUEST_IMAGE_CAPTURE=1;
    lateinit var transactionData: ArrayList<Transaction>;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transaction_confirmation)
        val s : String = intent.getStringExtra("json");
        btnTambah.setOnClickListener{
            dispatchTakePictureIntent();
        }
        Log.d("Data", s);
        btnConfirm.setOnClickListener{
            if(viewStruk.getDrawable()!=null){
                val bitmap: Bitmap = (viewStruk.getDrawable() as BitmapDrawable).getBitmap();
                val processedImage: String = ConvertToBase64(bitmap);
               // Log.d("Base64", processedImage);
                sendData(s, processedImage);
            }
            else{
                Toast.makeText(this, "Please capture the image for verification", Toast.LENGTH_LONG).show();
            }


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

    private fun sendData(s: String, base64String: String){
        val cache = DiskBasedCache(cacheDir, 1024 * 1024);

        val network = BasicNetwork(HurlStack());

        val requestQueue = RequestQueue(cache, network).apply {
            start()
        }

        val url = callUrlTrigger();
        //val param = callParamTransactionToko(base64String);
        //val strings : String = url+param;

//        val stringRequest = StringRequest(Request.Method.POST, url ,
//            Response.Listener<String> { response ->
//                Log.d("res", response);
//                var stringResponse = response.toString();
//                val res: JSONObject = JSONObject(response);
//                val statusCode: String = res.getString("success");
//                if(statusCode.equals("Success")){
//                    transactionId  = res.getString("data");
//                    sendProductData(transactionId, s);
//                    Toast.makeText(this, transactionId, LENGTH_LONG).show();
//                    Log.d("TID",transactionId);
//                }
//            },
//            Response.ErrorListener { error ->
//                    Toast.makeText(this, error.toString(), LENGTH_LONG).show();
//            }){
//
//        }

        val postBody = JSONObject();
        postBody.put("userName", "MWB");
        postBody.put("Password", "MWB");
        postBody.put("image", base64String);

        val queue = Volley.newRequestQueue(this);
        val request = JsonObjectRequest(Request.Method.POST, url, postBody,
            Response.Listener {
                response ->
                Log.d("Response", response.toString() )
//                var stringResponse = response.toString();
//                val res: JSONObject = JSONObject(response);
                val statusCode: String = response.getString("success");
                if(statusCode.equals("Success")){
                    transactionId  = response.getString("data");
                    sendProductData(transactionId, s);
                    Log.d("TID",transactionId);
                }
                else{
                }
            }, Response.ErrorListener {
                error : VolleyError -> Log.d("Error1", error.toString());
            }
            )
        requestQueue.add(request);
    }
    private fun sendProductData(id: String, data: String){
        Log.d("Error", id+data);
        val cache = DiskBasedCache(cacheDir, 1024 * 1024);

        val network = BasicNetwork(HurlStack());

        val requestQueue = RequestQueue(cache, network).apply {
            start()
        }

        val url = callUrlTransaction();
      //  val param = callParamTransactionTokoData(id, data);

        val postBody = JSONObject();
        postBody.put("transactionid", id);
        postBody.put("jsonString", data);

        Log.d("Data", postBody.toString());

        val queue = Volley.newRequestQueue(this);
        val request = JsonObjectRequest(Request.Method.POST, url, postBody,
            Response.Listener {
                    response ->
                Log.d("Response2", response.toString() )
//                var stringResponse = response.toString();
//                val res: JSONObject = JSONObject(response);
                val statusCode: String = response.getString("success");
                if(statusCode.equals("Success")){
                    //transactionId  = response.getString("data");
                  //  sendProductData(transactionId, s);

                    val statusCode: String = response.getString("success");
                    //Toast.makeText(this, statusCode, LENGTH_LONG).show();
                    finish();
                   // Log.d("TID",transactionId);
                }
                else{
                    Toast.makeText(this, response.getString("data"), Toast.LENGTH_LONG).show();
                }
            }, Response.ErrorListener {
                    error : VolleyError -> Log.d("Error2", error.toString());
            }
        )
        queue.add(request);

//        val stringRequest = StringRequest(Request.Method.GET, url+param,
//            Response.Listener<String> { response ->
//                Log.d("res", response);
//                var stringResponse = response.toString();
//                val res: JSONObject = JSONObject(response);
//                val statusCode: String = res.getString("success");
//                if(statusCode.equals("Success")){
//                    //transactionId  = res.getString("data");
//                    Toast.makeText(this, statusCode, LENGTH_LONG).show();
//                }
//            },
//            Response.ErrorListener { error ->
//                Toast.makeText(this, error.toString(), LENGTH_LONG).show();
//            })
//        requestQueue.add(stringRequest);

    }

    fun ConvertToBase64(image: Bitmap): String{
        val stream = ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.PNG, 90, stream);
        val processedImage: ByteArray = stream.toByteArray();
        val base64String: String = Base64.encodeToString(processedImage, Base64.DEFAULT);
        return base64String;
    }
}
