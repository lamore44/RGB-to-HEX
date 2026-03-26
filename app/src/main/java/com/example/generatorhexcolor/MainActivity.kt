package com.example.generatorhexcolor

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private lateinit var seekBarR: SeekBar
    private lateinit var seekBarG: SeekBar
    private lateinit var seekBarB: SeekBar
    private lateinit var inputR: EditText
    private lateinit var inputG: EditText
    private lateinit var inputB: EditText
    private lateinit var btnGenerate: Button
    private lateinit var btnCopy: Button
    private lateinit var colorPreview: android.view.View
    private lateinit var tvHexCode: TextView
    private lateinit var tvColorName: TextView

    // Simpan kode HEX terakhir untuk fitur copy
    private var currentHex = "#000000"

    private var isUpdating = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        seekBarR = findViewById(R.id.seekBarR)
        seekBarG = findViewById(R.id.seekBarG)
        seekBarB = findViewById(R.id.seekBarB)
        inputR   = findViewById(R.id.inputR)
        inputG   = findViewById(R.id.inputG)
        inputB   = findViewById(R.id.inputB)
        btnGenerate  = findViewById(R.id.btnGenerate)
        btnCopy      = findViewById(R.id.btnCopy)
        colorPreview = findViewById(R.id.colorPreview)
        tvHexCode    = findViewById(R.id.tvHexCode)
        tvColorName  = findViewById(R.id.tvColorName)

        tvColorName.setTextColor(Color.BLACK)

        // Setup listener SeekBar agar sinkron dengan EditText
        setupSeekBarListeners()

        btnGenerate.setOnClickListener {
            generateColor()
        }

        btnCopy.setOnClickListener {
            copyToClipboard()
        }
    }

    private fun setupSeekBarListeners() {
        val listener = object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                if (fromUser && !isUpdating) {
                    isUpdating = true
                    when (seekBar.id) {
                        R.id.seekBarR -> inputR.setText(progress.toString())
                        R.id.seekBarG -> inputG.setText(progress.toString())
                        R.id.seekBarB -> inputB.setText(progress.toString())
                    }
                    isUpdating = false
                }
            }
            override fun onStartTrackingTouch(seekBar: SeekBar) {}
            override fun onStopTrackingTouch(seekBar: SeekBar) {}
        }

        seekBarR.setOnSeekBarChangeListener(listener)
        seekBarG.setOnSeekBarChangeListener(listener)
        seekBarB.setOnSeekBarChangeListener(listener)
    }

    private fun generateColor() {
        val rText = inputR.text.toString()
        val gText = inputG.text.toString()
        val bText = inputB.text.toString()

        //input gak boleh kosong
        if (rText.isEmpty() || gText.isEmpty() || bText.isEmpty()) {
            Toast.makeText(this, "Semua nilai RGB harus diisi!", Toast.LENGTH_SHORT).show()
            return
        }

        val r = rText.toInt()
        val g = gText.toInt()
        val b = bText.toInt()

        //nilai harus dalam range 0-255
        if (r !in 0..255 || g !in 0..255 || b !in 0..255) {
            Toast.makeText(this, "Nilai RGB harus antara 0 hingga 255!", Toast.LENGTH_SHORT).show()
            return
        }

        //Sinkron SeekBar sama nilai EditText
        if (!isUpdating) {
            isUpdating = true
            seekBarR.progress = r
            seekBarG.progress = g
            seekBarB.progress = b
            isUpdating = false
        }

        // Konversi RGB ke HEX
        currentHex = String.format("#%02X%02X%02X", r, g, b)
        colorPreview.setBackgroundColor(Color.rgb(r, g, b))
        tvHexCode.text = "Kode HEX: $currentHex"

        // Tampilkan nama warna hasil mapping
        tvColorName.text = "Nama Warna: ${getColorName(r, g, b)}"

        // Warna teks otomatis pas tekan generate warna
        val luminance = 0.299 * r + 0.587 * g + 0.114 * b
        val textColor = if (luminance > 128) Color.BLACK else Color.parseColor("#BDBDBD")
        tvHexCode.setTextColor(textColor)
        tvColorName.setTextColor(textColor)

        Toast.makeText(this, "Warna berhasil digenerate!", Toast.LENGTH_SHORT).show()
    }
    private fun copyToClipboard() {
        val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText("Kode HEX", currentHex)
        clipboard.setPrimaryClip(clip)
        Toast.makeText(this, "Kode $currentHex berhasil disalin!!!", Toast.LENGTH_SHORT).show()
    }
    private fun getColorName(r: Int, g: Int, b: Int): String {
        return when {
            r > 200 && g < 80  && b < 80  -> "Merah (Red)"
            r < 80  && g > 200 && b < 80  -> "Hijau (Green)"
            r < 80  && g < 80  && b > 200 -> "Biru (Blue)"
            r > 200 && g > 200 && b < 80  -> "Kuning (Yellow)"
            r > 200 && g < 80  && b > 200 -> "Magenta"
            r < 80  && g > 200 && b > 200 -> "Cyan"
            r > 200 && g > 130 && b < 80  -> "Oranye (Orange)"
            r > 150 && g < 100 && b > 100 -> "Pink"
            r > 220 && g > 220 && b > 220 -> "Putih (White)"
            r < 50  && g < 50  && b < 50  -> "Hitam (Black)"
            r in 80..180 && g in 80..180 && b in 80..180 -> "Abu-Abu (Gray)"
            else -> "Campuran (Mixed)"
        }
    }
}