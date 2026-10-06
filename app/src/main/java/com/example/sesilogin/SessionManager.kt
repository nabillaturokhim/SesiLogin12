package com.example.sesisilogin

import android.content.Context

class SessionManager(context: Context) {

    private val prefs = context.getSharedPreferences(NAMA_PREFS, Context.MODE_PRIVATE)

    companion object {
        private const val NAMA_PREFS = "sesi_login"
        private const val KEY_SUDAH_LOGIN = "sudah_login"
        private const val KEY_NAMA = "nama"
        private const val KEY_EMAIL = "email"
        private const val KEY_WAKTU = "waktu_login"
    }

    fun simpanSesi(nama: String, email: String, ingatSaya: Boolean) {
        prefs.edit()
            .putBoolean(KEY_SUDAH_LOGIN, ingatSaya)
            .putString(KEY_NAMA, nama)
            .putString(KEY_EMAIL, email)
            .putLong(KEY_WAKTU, System.currentTimeMillis())
            .apply()
    }

    fun sudahLogin(): Boolean = prefs.getBoolean(KEY_SUDAH_LOGIN, false)

    fun adaDataProfil(): Boolean = ambilNama().isNotEmpty() && ambilEmail().isNotEmpty()

    fun ambilNama(): String = prefs.getString(KEY_NAMA, "") ?: ""
    fun ambilEmail(): String = prefs.getString(KEY_EMAIL, "") ?: ""
    fun ambilWaktuLogin(): Long = prefs.getLong(KEY_WAKTU, 0L)

    fun hapusSesi() {
        prefs.edit().clear().apply()
    }
}