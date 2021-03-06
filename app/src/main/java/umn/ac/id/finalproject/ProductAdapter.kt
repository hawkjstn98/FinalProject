package umn.ac.id.finalproject

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import data.Product
import android.R.attr.country
import java.nio.file.Files.size
import android.text.method.TextKeyListener.clear
import android.widget.Filter


class ProductAdapter : RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {
    private var dataList: ArrayList<Product>
    private var context: Context

    constructor(dataList: ArrayList<Product>, context: Context){
        this.dataList = dataList
        this.context = context
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ProductAdapter.ProductViewHolder {
        val layoutInflater: LayoutInflater = LayoutInflater.from(p0.context)
        val view: View = layoutInflater.inflate(R.layout.row_sendal, p0, false)
        return ProductViewHolder(view)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    fun FilterList(filteredList: ArrayList<Product>){
        updateList(filteredList)
    }

    override fun onBindViewHolder(p0: ProductViewHolder, p1: Int) {
        p0.idItem.text = dataList.get(p1).produkId
        p0.warnaItem.text = dataList.get(p1).warna
        p0.ukuranItem.text = dataList.get(p1).ukuran.toString()
        p0.jumlahItem.text = dataList.get(p1).jumlah.toString()
        p0.parentLayout.setOnClickListener {
            Toast.makeText(context, p0.idItem.text, Toast.LENGTH_SHORT).show()
        }
    }

    fun updateList(newList: ArrayList<Product>){
        dataList = ArrayList()
        dataList.addAll(newList)
        notifyDataSetChanged()
    }

    class ProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val idItem: TextView = itemView.findViewById(R.id.idItem) as TextView
        val warnaItem: TextView = itemView.findViewById(R.id.warnaItem) as TextView
        val ukuranItem: TextView = itemView.findViewById(R.id.ukuranItem) as TextView
        val jumlahItem: TextView = itemView.findViewById(R.id.jumlahItem) as TextView
        val parentLayout: LinearLayout = itemView.findViewById(R.id.parent_layout) as LinearLayout
    }
}