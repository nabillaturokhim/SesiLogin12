package com.example.sesisilogin

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class LoginActivity : AppCompatActivity() {

    companion object {
        private const val EMAIL_DEMO = "rani@example.com"
        private const val SANDI_DEMO = "123456"
    }

    private lateinit var session: SessionManager
    private lateinit var etEmail: EditText
    private lateinit var etPassword: EditText
    private lateinit var cbIngatSaya: CheckBox
    private lateinit var tvPesanMasuk: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        session = SessionManager(this)

        etEmail = findViewById(R.id.etEmail)
        etPassword = findViewById(R.id.etPassword)
        cbIngatSaya = findViewById(R.id.cbIngatSaya)
        tvPesanMasuk = findViewById(R.id.tvPesanMasuk)

        // CEK SESI: sudah pernah login + centang "Ingat saya" -> langsung dashboard
        if (session.sudahLogin()) {
            bukaDashboard()
            return
        }

        // isi ulang email terakhir
        etEmail.setText(session.ambilEmail())

        findViewById<Button>(R.id.btnMasuk).setOnClickListener { prosesLogin() }
    }

    private fun prosesLogin() {
        val email = etEmail.text.toString().trim()
        val sandi = etPassword.text.toString()

        tvPesanMasuk.text = ""

        if (email.isEmpty()) {
            etEmail.error = getString(R.string.pesan_email_kosong)
            return
        }
        if (sandi.length < 6) {
            etPassword.error = getString(R.string.pesan_sandi_pendek)
            return
        }
        if (email != EMAIL_DEMO || sandi != SANDI_DEMO) {
            tvPesanMasuk.text = getString(R.string.pesan_login_gagal)
            return
        }

        // "rani@example.com" -> "Rani"
        val nama = email.substringBefore("@").replaceFirstChar { it.uppercase() }

        // simpan sesi HANYA setelah semua pemeriksaan lolos
        session.simpanSesi(nama, email, cbIngatSaya.isChecked)

        bukaDashboard()
    }

    private fun bukaDashboard() {
        startActivity(Intent(this, DashboardActivity::class.java))
        finish() // supaya tombol Back tidak kembali ke form login
    }
}