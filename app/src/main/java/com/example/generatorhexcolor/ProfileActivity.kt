package com.example.generatorhexcolor

import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ProfileActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_USER = "extra_user"
    }

    private lateinit var tvInisial: TextView
    private lateinit var tvNama: TextView
    private lateinit var tvNim: TextView
    private lateinit var tvProdi: TextView
    private lateinit var tvGender: TextView
    private lateinit var tvHobi: TextView
    private lateinit var btnKembaliRegister: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        supportActionBar?.title = "Profil"

        tvInisial = findViewById(R.id.tvInisial)
        tvNama = findViewById(R.id.tvNama)
        tvNim = findViewById(R.id.tvNim)
        tvProdi = findViewById(R.id.tvProdi)
        tvGender = findViewById(R.id.tvGender)
        tvHobi = findViewById(R.id.tvHobi)
        btnKembaliRegister = findViewById(R.id.btnKembaliRegister)

        // Terima data User via Parcelable dengan API safe getter
        val user = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra(EXTRA_USER, User::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.getParcelableExtra(EXTRA_USER)
        }

        user?.let {
            tvNama.text = it.nama
            tvNim.text = it.nim
            tvProdi.text = it.programStudi
            tvGender.text = it.gender
            tvHobi.text = it.hobi.joinToString(", ")

            // Hindari crash jika nama kosong
            tvInisial.text = it.nama.firstOrNull()?.uppercaseChar()?.toString().orEmpty()
        }

        btnKembaliRegister.setOnClickListener {
            finish()
        }
    }
}