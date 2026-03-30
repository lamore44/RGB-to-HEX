package com.example.generatorhexcolor

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class RegisterActivity : AppCompatActivity() {
    private lateinit var inputNama: EditText
    private lateinit var inputNim: EditText
    private lateinit var spinnerProdi: Spinner
    private lateinit var radioGroupGender: RadioGroup
    private lateinit var cbMembaca: CheckBox
    private lateinit var cbMusik: CheckBox
    private lateinit var cbGaming: CheckBox
    private lateinit var cbMemasak: CheckBox
    private lateinit var cbNyawit: CheckBox
    private lateinit var cbOlahraga: CheckBox
    private lateinit var btnDaftar: Button
    private lateinit var btnKembali: Button

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        supportActionBar?.title = "Form Regiter"

        // binding komponen
        inputNama = findViewById(R.id.inputNama)
        inputNim = findViewById(R.id.inputNim)
        spinnerProdi = findViewById(R.id.spinnerProdi)
        radioGroupGender = findViewById(R.id.radioGroupGender)
        cbMembaca = findViewById(R.id.cbMembaca)
        cbMusik = findViewById(R.id.cbMusik)
        cbGaming = findViewById(R.id.cbGaming)
        cbMemasak = findViewById(R.id.cbMemasak)
        cbNyawit = findViewById(R.id.cbNyawit)
        cbOlahraga = findViewById(R.id.cbOlahraga)
        btnDaftar = findViewById(R.id.btnDaftar)
        btnKembali = findViewById(R.id.btnKembali)

        // list prodi
        val prodiList = listOf(
            "Pilih Program Studi",
            "Teknik Informatika",
            "Teknik Elektro",
            "Teknik Sipil",
            "Teknik Mesin",
            "Teknik Industri",
            "Teknik Arsitektur"
        )


        val adapter = object : ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, prodiList){
            override fun getView(position: Int, convertView: android.view.View?, parent: android.view.ViewGroup): android.view.View {
                val view = super.getView(position, convertView, parent)
                (view as android.widget.TextView).setTextColor(android.graphics.Color.parseColor("#212121"))
                return view
            }
        }

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerProdi.adapter = adapter

        btnDaftar.setOnClickListener {
            if (validasiInput()){
                kirimData()
            }
        }
        btnKembali.setOnClickListener {
            finish()
        }
    }

    private fun validasiInput(): Boolean{
        val nama = inputNama.text.toString().trim()
        val nim = inputNim.text.toString().trim()

        if (nama.isEmpty()){
            inputNama.error = "Nama gak boleh kosong!!!"
            inputNama.requestFocus()
            return false
        }

        if (nim.isEmpty()){
            inputNim.error = "NIM gak boleh kosong!!!"
            inputNim.requestFocus()
            return false
        }

        if(spinnerProdi.selectedItemPosition == 0){
            Toast.makeText(this, "Pilih program studi!!!", Toast.LENGTH_SHORT).show()
            spinnerProdi.requestFocus()
            return false
        }
        if(radioGroupGender.checkedRadioButtonId == -1){
            Toast.makeText(this, "Pilih jenis kelamin!!!", Toast.LENGTH_SHORT).show()
            radioGroupGender.requestFocus()
            return false
        }
        val adaHobi = cbMembaca.isChecked || cbMusik.isChecked || cbGaming.isChecked || cbMemasak.isChecked || cbNyawit.isChecked || cbOlahraga.isChecked
        if(!adaHobi){
            Toast.makeText(this, "Pilih minimal satu hobi!!!", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }

    private fun kirimData(){
        val nama = inputNama.text.toString().trim()
        val nim = inputNim.text.toString().trim()
        val prodi = spinnerProdi.selectedItem.toString()
        val radioPick = findViewById<RadioButton>(radioGroupGender.checkedRadioButtonId)
        val gender = radioPick.text.toString()

        val listHobi = arrayListOf<String>()
        if(cbMembaca.isChecked) listHobi.add("Membaca")
        if(cbMusik.isChecked) listHobi.add("Musik")
        if(cbGaming.isChecked) listHobi.add("Gaming")
        if(cbMemasak.isChecked) listHobi.add("Memasak")
        if(cbNyawit.isChecked) listHobi.add("Nyawit")
        if(cbOlahraga.isChecked) listHobi.add("Olahraga")

        val user = User(
            nama = nama,
            nim = nim,
            programStudi = prodi,
            gender = gender,
            hobi = listHobi
        )

        val intent = Intent(this, ProfileActivity::class.java)
        intent.putExtra(ProfileActivity.EXTRA_USER, user)
        startActivity(intent)
    }

}