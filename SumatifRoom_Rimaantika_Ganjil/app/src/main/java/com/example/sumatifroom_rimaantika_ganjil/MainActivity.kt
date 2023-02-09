package com.example.sumatifroom_rimaantika_ganjil

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sumatifroom_rimaantika_ganjil.room.Constant
import com.example.sumatifroom_rimaantika_ganjil.room.codepelita
import com.example.sumatifroom_rimaantika_ganjil.room.tb_karyawan
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    val db by lazy { codepelita(this) }
    lateinit var karyawanAdapter: karyawanAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupRecyclerView()
        setupListener()
    }

    override fun onStart() {
        super.onStart()
        loadData()

    }

    fun loadData(){
        CoroutineScope(Dispatchers.IO).launch {
            val pegawai = db.pegawaiDao().tampilsemua()
            Log.d("MainActivity", "dbResponse:$pegawai")
            withContext(Dispatchers.Main){
                karyawanAdapter.setData(pegawai)
            }
        }
    }

    fun setupListener(){
        inputdata.setOnClickListener{
            intentEdit(0,Constant.TYPE_CREATE)
        }
    }

    fun intentEdit(karyawanId: Int, intentType: Int){
        val pindah = Intent(applicationContext,EditActivity::class.java)
        startActivity(pindah
            .putExtra("intent_id", karyawanId)
            .putExtra("intent_type", intentType))
    }
    private fun setupRecyclerView(){
        karyawanAdapter = karyawanAdapter(arrayListOf(),object :karyawanAdapter.onAdapterListener{
            override fun onClick(pegawai: tb_karyawan) {
                intentEdit(pegawai.id,Constant.TYPE_READ)
            }

            override fun onUpdate(pegawai: tb_karyawan) {
                intentEdit(pegawai.id,Constant.TYPE_UPDATE)
            }

            override fun onDelete(pegawai: tb_karyawan) {
                deleteDialog(pegawai)
            }
        })
        listKaryawan.apply {
            layoutManager = LinearLayoutManager(applicationContext)
            adapter = karyawanAdapter
        }
    }
    private fun deleteDialog(karyawan: tb_karyawan){
        val alertDialog = AlertDialog.Builder(this)
        alertDialog.apply {
            setTitle("Konfirmasi")
            setMessage("Yakin hapus ${karyawan.id}?")
            setNegativeButton("Batal"){dialogInterface, i ->
                dialogInterface.dismiss()
            }
            setPositiveButton("Hapus"){dialogInterface, i ->
                dialogInterface.dismiss()
                CoroutineScope(Dispatchers.IO).launch {
                    db.pegawaiDao().deletetbKaryawan(karyawan)
                    loadData()
                }
            }
        }
        alertDialog.show()
    }
}