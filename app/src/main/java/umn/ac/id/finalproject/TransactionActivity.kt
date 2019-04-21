package umn.ac.id.finalproject

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_transaction.*

class TransactionActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transaction)
        btnTambah.setOnClickListener{
            val intent = Intent(this@TransactionActivity, TransactionConfirmationActivity::class.java);
            startActivity(intent);
        }

    }
}
