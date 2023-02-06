package com.example.penjualan_indifannirisqiana

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.penjualan_indifannirisqiana.room.tbBarang
import kotlinx.android.synthetic.main.activity_barang_adapter.view.*

class BarangAdapter (private val brngs: ArrayList<tbBarang>, private val listener: OnAdapterListener)
    : RecyclerView.Adapter<BarangAdapter.BarangViewHolder>(){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BarangViewHolder {
        return BarangViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.activity_barang_adapter,parent,false)
        )
    }
    override fun getItemCount()= brngs.size

    override fun onBindViewHolder(holder: BarangViewHolder, position: Int) {
        val barang = brngs[position]
        holder.view.Tnama.text = barang.nama
        holder.view.Tnm.text = barang.harga.toString()
        holder.view.Tview.setOnClickListener{
            listener.onClick(barang)
        }
        holder.view.ic_edit.setOnClickListener{
            listener.onUpdate(barang)
        }
        holder.view.ic_delete.setOnClickListener{
            listener.onDelete(barang)
        }

    }
    class BarangViewHolder(val view: View): RecyclerView.ViewHolder(view)

    fun setData(list: List<tbBarang>){
        brngs.clear()
        brngs.addAll(list)
        notifyDataSetChanged()
    }
    interface OnAdapterListener{
        fun onClick(barang: tbBarang)
        fun onUpdate(barang: tbBarang)
        fun onDelete(barang: tbBarang)
    }
}