package com.example.sumatifroom_rimaantika_ganjil.room

import androidx.room.*

@Dao
interface karyawanDAO {
    @Insert
    fun addtbKaryawan(karyawan: tb_karyawan)

    @Update
    fun updatetbKaryawan(Karyawan: tb_karyawan)

    @Delete
    fun deletetbKaryawan(karyawan: tb_karyawan)

    @Query("SELECT * FROM tb_karyawan")
    fun tampilsemua():List<tb_karyawan>

    @Query("SELECT * FROM tb_karyawan WHERE id =:id_karyawan")
    fun getTampil(id_karyawan: Int) : List<tb_karyawan>

}