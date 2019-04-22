package umn.ac.id.finalproject

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import data.Transaction
import kotlinx.android.synthetic.main.activity_transaction.*

class TransactionActivity : AppCompatActivity() {

    private lateinit var produkId: EditText
    private lateinit var ukuran: EditText
    private lateinit var jumlah: EditText
    private lateinit var warna: EditText

    private lateinit var btnInput: Button
    private lateinit var btnTambah: Button

    private lateinit var recyclerView: RecyclerView
    private lateinit var tAdapter: TransactionAdapter
    private var transList: ArrayList<Transaction> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transaction)

        produkId = findViewById(R.id.inputKodeSendal)
        ukuran = findViewById(R.id.inputUkuranSendal)
        jumlah = findViewById(R.id.inputJummlahSendal)
        warna = findViewById(R.id.inputWarnaSendal)

        btnInput = findViewById(R.id.btnInput)
        btnTambah = findViewById(R.id.btnTambah)

        tAdapter = TransactionAdapter(transList, this@TransactionActivity)
        recyclerView = findViewById(R.id.recycler_list_transaksi)

        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(this@TransactionActivity)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = tAdapter

        btnInput.setOnClickListener {
            var warning: String = ""
            var empty: Boolean = true
            if(!produkId.text.isNullOrEmpty() && !ukuran.text.isNullOrEmpty() && !jumlah.text.isNullOrEmpty() && !warna.text.isNullOrEmpty()){
                empty = false
            }

            if(!empty){
                // Initialize transaction object to be added
                //var trans: Transaction = Transaction(2, jumlah.text.toString().toInt(), )
            }
            else{
                if(produkId.text.isNullOrEmpty()){
                    warning += "Kode Sendal, "
                }
                if(ukuran.text.isNullOrEmpty()){
                    warning += "Ukuran, "
                }
                if(warna.text.isNullOrEmpty()){
                    warning += "Warna, "
                }
                if(jumlah.text.isNullOrEmpty()){
                    warning += "Jumlah, "
                }

                warning += "masih kosong"
                Toast.makeText(this@TransactionActivity, warning, Toast.LENGTH_SHORT).show()
            }
        }

        btnTambah.setOnClickListener{
            val intent = Intent(this@TransactionActivity, TransactionConfirmationActivity::class.java);
            startActivity(intent);
        }

    }
}
