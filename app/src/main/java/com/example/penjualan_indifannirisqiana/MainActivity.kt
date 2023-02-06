package com.example.penjualan_indifannirisqiana

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.penjualan_indifannirisqiana.room.Constant
import com.example.penjualan_indifannirisqiana.room.dbpenjualan
import com.example.penjualan_indifannirisqiana.room.tbBarang
import kotlinx.android.synthetic.main.activity_edit.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {
    val db by lazy { dbpenjualan(this) }
    lateinit var barangAdapter:BarangAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupListener()
        setupRecyclerView()
    }

    override fun onStart() {
        super.onStart()
        loadBrg()
    }
    fun loadBrg(){
        CoroutineScope(Dispatchers.IO).launch {
            val barangs = db.DaoBarang().tampilBarang()
            Log.d("MainActivity", "dbResponse:$barangs")
            withContext(Dispatchers.Main) {
                barangAdapter.setData(barangs)
            }
        }
    }
    private fun setupListener(){
        BtnInput.setOnClickListener {
            intentEdit(0, Constant.TYPE_CREATE)
        }
    }
    fun intentEdit(brngId: Int, intentType: Int){
        startActivity(
            Intent(applicationContext,EditActivity::class.java)
                .putExtra("intent_id",brngId)
                .putExtra("intent_type",intentType)
        )
    }

    private fun setupRecyclerView(){
        barangAdapter = BarangAdapter(arrayListOf(),object : BarangAdapter.OnAdapterListener{
            override fun onClick(barang: tbBarang) {
                intentEdit(barang.id,Constant.TYPE_READ)
            }

            override fun onUpdate(barang: tbBarang) {
                intentEdit(barang.id,Constant.TYPE_UPDATE)
            }

            override fun onDelete(barang: tbBarang) {
                deleteDialog(barang)
            }

        })
        listdatabarang.apply {
            layoutManager = LinearLayoutManager(applicationContext)
            adapter = barangAdapter
        }
    }
    private fun deleteDialog(barang: tbBarang){
        val alertDialog = AlertDialog.Builder(this)
        alertDialog.apply {
            setTitle("Konfirmasi")
            setMessage("Yakin Hapus ${barang.nama}?")
            setNegativeButton("Batal") {dialogInterface,i ->
                dialogInterface.dismiss()
            }
            setPositiveButton("Hapus") {dialogInterface,i ->
                dialogInterface.dismiss()
                CoroutineScope(Dispatchers.IO).launch {
                    db.DaoBarang().deleteBarang(barang)
                    loadBrg()
                }
            }
        }
        alertDialog.show()
    }
}