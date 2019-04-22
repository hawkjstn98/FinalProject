package umn.ac.id.finalproject

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import data.Transaction

class TransactionAdapter : RecyclerView.Adapter<TransactionAdapter.TransactionViewHolder> {
    private var dataList: ArrayList<Transaction>
    private var context: Context

    constructor(dataList: ArrayList<Transaction>, context: Context){
        this.dataList = dataList
        this.context = context
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): TransactionAdapter.TransactionViewHolder {
        val layoutInflater: LayoutInflater = LayoutInflater.from(p0.context)
        val view: View = layoutInflater.inflate(R.layout.transaction, p0, false)
        return TransactionViewHolder(view)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

//    fun FilterList(filteredList: ArrayList<Product>){
//        updateList(filteredList)
//    }

    override fun onBindViewHolder(p0: TransactionViewHolder, p1: Int) {
        p0.idItem.text = "Product ID : " + dataList.get(p1).produkId
        p0.warnaItem.text = "Warna : " + dataList.get(p1).warna
        p0.ukuranItem.text = "Ukuran : " + dataList.get(p1).ukuran
        p0.jumlahItem.text = "Jumlah : " + dataList.get(p1).jumlah
        p0.parentLayout.setOnClickListener {
            Toast.makeText(context, p0.idItem.text, Toast.LENGTH_SHORT).show()
        }
    }

    fun updateList(newList: ArrayList<Transaction>){
        dataList = ArrayList()
        dataList.addAll(newList)
        notifyDataSetChanged()
    }

    class TransactionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val idItem: TextView = itemView.findViewById(R.id.idItem) as TextView
        val warnaItem: TextView = itemView.findViewById(R.id.warnaItem) as TextView
        val ukuranItem: TextView = itemView.findViewById(R.id.ukuranItem) as TextView
        val jumlahItem: TextView = itemView.findViewById(R.id.jumlahItem) as TextView
        val parentLayout: LinearLayout = itemView.findViewById(R.id.parent_layout) as LinearLayout
    }
}