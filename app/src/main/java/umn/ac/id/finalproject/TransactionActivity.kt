package umn.ac.id.finalproject

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import kotlinx.android.synthetic.main.activity_transaction.*

class TransactionActivity : AppCompatActivity() {

    lateinit var produkId: EditText
    lateinit var ukuran: EditText
    lateinit var jumlah: EditText
    lateinit var warna: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transaction)

        produkId = findViewById(R.id.idItem)

        btnTambah.setOnClickListener{
            val intent = Intent(this@TransactionActivity, TransactionConfirmationActivity::class.java);
            startActivity(intent);
        }

    }
}
