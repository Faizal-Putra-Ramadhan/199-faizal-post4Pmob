package com.faizalputraramadhan.post4_199

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.faizalputraramadhan.post4_199.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private var jk = ""
    private var id = 0

    private lateinit var dbBarang: DatabaseBarang
    private lateinit var barangDao: BarangDao
    private lateinit var appExecutors: AppExecutor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        appExecutors = AppExecutor()
        dbBarang = DatabaseBarang.getDatabase(applicationContext)
        barangDao = dbBarang.barangDao()

        var jumlahData = 0

        binding.apply {


            btnSimpan.setOnClickListener {

                jumlahData++

                val nama = etNama.text.toString()
                val nik = etNIK.text.toString()
                val kabupaten = etKabupaten.text.toString()
                val kecamatan = etKecamatan.text.toString()
                val desa = etDesa.text.toString()
                val rt = etRT.text.toString()
                val rw = etRW.text.toString()


                if(rbLaki.isChecked){
                    jk = rbLaki.text.toString()
                }else if(rbPerempuan.isChecked){
                    jk = rbPerempuan.text.toString()
                }


                val status = spStatus.selectedItem.toString()


                appExecutors.diskIO.execute {
                    val lastIdFromDb = barangDao.getLastId()

                    val lastId = if (lastIdFromDb != null) {
                        lastIdFromDb
                    } else {
                        0
                    }

                    id = lastId + 1


                    val dataWarga = Barang(
                        id = id,
                        nama = "$nama ($jk)",
                        nik = nik,
                        alamat = "RT $rt/ RW $rw, $desa, $kecamatan, $kabupaten",
                        status = status
                    )

                    barangDao.insert(dataWarga)
                }

                appExecutors.diskIO.execute {
                    val dao = DatabaseBarang.getDatabase(this@MainActivity).barangDao()
                    val builder = StringBuilder()

                    builder.append("Daftar Warga Negara : \n")
                    for (i in 1..jumlahData){

                        binding.apply {
                            val selectedBarang = dao.getBarangById(i)
                            builder.append("$i. ${selectedBarang.nama} - ${selectedBarang.status}\n")
                            builder.append("NIK : ${selectedBarang.nik}\n")
                            builder.append("Alamat : ${selectedBarang.alamat} \n\n")
                        }
                    }
                    runOnUiThread {
                        binding.tvDaftar.text = builder.toString()
                    }
                }
            }


//            barangDao.getAllBarang().observe(this@MainActivity) { list ->
//                if (list.isEmpty()) {
//                    binding.tvDaftar.text = "Belum ada data."
//                } else {
//                    val builder = StringBuilder()
//                    builder.append("Daftar Warga Negara : \n")
//                    list.forEachIndexed { index, barang ->
//                        builder.append("${index + 1}. ${barang.nama} - ${barang.status}\n")
//                        builder.append("NIK: ${barang.nik}\n")
//                        builder.append("Alamat: ${barang.alamat}\n\n")
//                    }
//                    binding.tvDaftar.text = builder.toString()
//                }
//            }

            btnReset.setOnClickListener {
                val dao = DatabaseBarang.getDatabase(this@MainActivity).barangDao()
                appExecutors.diskIO.execute {
                    dao.deleteAll()
                }
            }
        }
    }
}
