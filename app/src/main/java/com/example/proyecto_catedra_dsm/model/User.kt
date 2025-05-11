package com.example.proyecto_catedra_dsm.model

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import com.example.proyecto_catedra_dsm.db.HelperDB

class UserDAO(context: Context) {
    private val dbHelper = HelperDB(context)
    private val db: SQLiteDatabase = dbHelper.writableDatabase

    fun registerUser(username: String, password: String): Boolean {
        val values = ContentValues().apply {
            put("username", username)
            put("password", password)
        }
        return db.insert("users", null, values) != -1L
    }

    fun loginUser(username: String, password: String): Boolean {
        val query = "SELECT * FROM users WHERE username = ? AND password = ?"
        val cursor = db.rawQuery(query, arrayOf(username, password))
        val isLoggedIn = cursor.count > 0
        cursor.close()
        return isLoggedIn
    }
}