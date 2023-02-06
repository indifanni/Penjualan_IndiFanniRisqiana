package com.example.penjualan_indifannirisqiana

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.penjualan_indifannirisqiana.room.Constant
import com.example.penjualan_indifannirisqiana.room.dbpenjualan
import com.example.penjualan_indifannirisqiana.room.tbBarang
import kotlinx.android.synthetic.main.activity_edit.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class EditActivity : AppCompatActivity() {
    val db by lazy { dbpenjualan(this) }
    private var brngId: Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)
        setupView()
        setupListener()
        Toast.makeText(this,brngId.toString(), Toast.LENGTH_SHORT).show()
    }

    fun setupView(){
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        val intentType = intent.getIntExtra("intent_type",0)
        when(intentType) {
            Constant.TYPE_CREATE -> {
                BtnUpdate.visibility = View.GONE
            }
            Constant.TYPE_READ -> {
                BtnSimpan.visibility = View.GONE
                BtnUpdate.visibility = View.GONE
                tampil()
            }
            Constant.TYPE_UPDATE -> {
                BtnSimpan.visibility = View.GONE
                tampil()
            }
        }
    }

    fun setupListener(){
        BtnSimpan.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                db.DaoBarang().addBarang(
                    tbBarang(ETid.text.toString().toInt(), ETnama.text.toString(),ETharga.text.toString().toInt(),ETstok.text.toString().toInt())
                )
                finish()
            }
        }
        BtnUpdate.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                db.DaoBarang().updateBarang(
                    tbBarang(brngId, ETnama.text.toString(),ETharga.text.toString().toInt(),ETstok.text.toString().toInt())
                )
                finish()
            }
        }
    }
    fun tampil(){
        brngId = intent.getIntExtra("intent_id",0)
        CoroutineScope(Dispatchers.IO).launch {
            val barang = db.DaoBarang().tampil(brngId)[0]
            val dataid: String = barang.id.toString()
            val dataharga: String = barang.harga.toString()
            val datastok: String = barang.stok.toString()
            ETid.setText(dataid)
            ETharga.setText(dataharga)
            ETstok.setText(datastok)
            ETnama.setText(barang.nama)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}