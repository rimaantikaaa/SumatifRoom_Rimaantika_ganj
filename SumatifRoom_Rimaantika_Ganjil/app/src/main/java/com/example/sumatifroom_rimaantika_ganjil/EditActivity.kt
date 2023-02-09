package com.example.sumatifroom_rimaantika_ganjil

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.sumatifroom_rimaantika_ganjil.room.Constant
import com.example.sumatifroom_rimaantika_ganjil.room.codepelita
import com.example.sumatifroom_rimaantika_ganjil.room.karyawanDAO
import com.example.sumatifroom_rimaantika_ganjil.room.tb_karyawan
import kotlinx.android.synthetic.main.activity_edit.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class EditActivity : AppCompatActivity() {

    private val db by lazy { codepelita(this) }
    private var karyawanId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)
        setupView()
        setupListener()

        karyawanId = intent.getIntExtra("intent_id",karyawanId)
        Toast.makeText(this,karyawanId.toString(),Toast.LENGTH_SHORT).show()
    }

    fun setupView(){
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        val intentType = intent.getIntExtra("intent_type", 0)
        when(intentType){
            Constant.TYPE_CREATE ->{
                btnUpdate.visibility = View.GONE

            }
            Constant.TYPE_READ ->{
                btnSimpan.visibility = View.GONE
                btnUpdate.visibility = View.GONE
                getTampil()
            }
            Constant.TYPE_UPDATE ->{
                btnSimpan.visibility = View.GONE
                getTampil()
            }
        }
    }
    fun setupListener(){
        btnSimpan.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                db.pegawaiDao().addtbKaryawan(
                    tb_karyawan(et_id.text.toString().toInt(),
                                et_nama.text.toString(),
                                et_alamat.text.toString(),
                                et_usia.text.toString().toInt())
                )
                finish()
            }
        }
        btnUpdate.setOnClickListener{
            CoroutineScope(Dispatchers.IO).launch{
                db.pegawaiDao().updatetbKaryawan(
                    tb_karyawan(et_id.text.toString().toInt(),
                        et_nama.text.toString(),
                        et_alamat.text.toString(),
                        et_usia.text.toString().toInt())
                )
                finish()
            }
        }
    }
    fun getTampil(){
        karyawanId = intent.getIntExtra("intent_id", 0)
        CoroutineScope(Dispatchers.IO).launch {
            val pegawai = db.pegawaiDao().getTampil(karyawanId)[0]
            val id : String = pegawai.id.toString()
            val usia : String = pegawai.usia.toString()

            et_id.setText(id)
            et_nama.setText(pegawai.nama)
            et_alamat.setText(pegawai.alamat)
            et_usia.setText(usia)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}