package umn.ac.id.finalproject

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import android.widget.Toast.LENGTH_LONG
import data.urlData
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(), urlData {
    private val al = ArrayList<String>();
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        cardviewTransaksi.setOnClickListener {
            val intent = Intent(this@MainActivity, TransactionActivity::class.java);
            startActivity(intent);
            Toast.makeText(this@MainActivity, callUrl(), LENGTH_LONG ).show();
        }
        cardviewStock.setOnClickListener {
            val intent = Intent( this@MainActivity, StockActivity::class.java);
            startActivity(intent);
        }
    }


}
