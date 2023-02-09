package com.example.sumatifroom_rimaantika_ganjil.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class tb_karyawan(
    @PrimaryKey
    val id: Int,
    val nama: String,
    val alamat: String,
    val usia: Int
)