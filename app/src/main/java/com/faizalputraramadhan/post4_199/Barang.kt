package com.faizalputraramadhan.post4_199

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Barang(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,

    @ColumnInfo(name = "nama")
    var nama: String? = null,

    @ColumnInfo(name = "nik")
    var nik: String? = null,

    @ColumnInfo(name = "alamat")
    var alamat: String? = null,

    @ColumnInfo(name = "status")
    var status: String? = null,
)


