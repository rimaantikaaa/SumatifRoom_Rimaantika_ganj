package com.example.sumatifroom_rimaantika_ganjil


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.sumatifroom_rimaantika_ganjil.room.tb_karyawan
import kotlinx.android.synthetic.main.activity_karyawan_adapter.view.*

class karyawanAdapter(private val pegawai: ArrayList<tb_karyawan>,private val listener: onAdapterListener):
    RecyclerView.Adapter<karyawanAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.activity_karyawan_adapter,
                parent,false)
        )
    }

    override fun getItemCount() = pegawai.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
       val karyawanku = pegawai[position]
        holder.view.tvnamaBrg.text = karyawanku.nama
        holder.view.tvidBrg.text = karyawanku.id.toString()

        holder.view.tvidBrg.setOnClickListener{
            listener.onClick(karyawanku)
        }
        holder.view.tvnamaBrg.setOnClickListener{
            listener.onClick(karyawanku)
        }
        holder.view.icon_edit.setOnClickListener{
            listener.onUpdate(karyawanku)
        }
        holder.view.icon_delete.setOnClickListener{
            listener.onDelete(karyawanku)
        }
    }
    class ViewHolder(val view: View): RecyclerView.ViewHolder(view)

    fun setData(list: List<tb_karyawan>) {
        pegawai.clear()
        pegawai.addAll(list)
        notifyDataSetChanged()
    }
    interface onAdapterListener{
        fun onClick(pegawai: tb_karyawan)
        fun onUpdate(pegawai: tb_karyawan)
        fun onDelete(pegawai: tb_karyawan)
    }

}