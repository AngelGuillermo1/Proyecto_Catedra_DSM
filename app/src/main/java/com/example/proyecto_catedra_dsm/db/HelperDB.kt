package com.example.proyecto_catedra_dsm.db

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class HelperDB(context: Context?) :
    SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {

    companion object {
        private const val DB_NAME = "QUIZ"
        private const val DB_VERSION = 1

        const val TABLE_PREGUNTAS = "Preguntas"
        const val COLUMN_ID = "id"
        const val COLUMN_MATERIA = "materia"
        const val COLUMN_PREGUNTA = "pregunta"
        const val COLUMN_NIVEL_DIFICULTAD = "nivelDificultad"
        const val COLUMN_RESPUESTA = "respuesta"
    }

    override fun onCreate(db: SQLiteDatabase) {
        // Crear tabla users
        val createTableUser = """
            CREATE TABLE users (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                username TEXT NOT NULL UNIQUE,
                password TEXT NOT NULL
            )
        """.trimIndent()
        db.execSQL(createTableUser)

        val createTablePreguntas = """
            CREATE TABLE TABLE_PREGUNTAS (
                $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_MATERIA TEXT,
                $COLUMN_PREGUNTA TEXT,
                $COLUMN_NIVEL_DIFICULTAD TEXT,
                $COLUMN_RESPUESTA TEXT
            )
        """.trimIndent()
        db.execSQL(createTablePreguntas)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_PREGUNTAS")
        db.execSQL("DROP TABLE IF EXISTS users")
        onCreate(db)
    }

    fun insertPregunta(materia: String, pregunta: String, nivelDificultad: String, respuesta: String): Long {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_MATERIA, materia)
            put(COLUMN_PREGUNTA, pregunta)
            put(COLUMN_NIVEL_DIFICULTAD, nivelDificultad)
            put(COLUMN_RESPUESTA, respuesta)
        }
        return db.insert(TABLE_PREGUNTAS, null, values)
    }

    fun getAllPreguntas(): List<Map<String, String>> {
        val db = readableDatabase
        val cursor = db.query(TABLE_PREGUNTAS, null, null, null, null, null, null)
        val preguntas = mutableListOf<Map<String, String>>()

        while (cursor.moveToNext()) {
            val pregunta = mapOf(
                COLUMN_MATERIA to cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_MATERIA)),
                COLUMN_PREGUNTA to cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PREGUNTA)),
                COLUMN_NIVEL_DIFICULTAD to cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NIVEL_DIFICULTAD)),
                COLUMN_RESPUESTA to cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_RESPUESTA))
            )
            preguntas.add(pregunta)
        }
        cursor.close()
        return preguntas
    }

    fun updatePregunta(materia: String, pregunta: String, nivelDificultad: String, respuesta: String): Int {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_NIVEL_DIFICULTAD, nivelDificultad)
            put(COLUMN_RESPUESTA, respuesta)
        }
        return db.update(
            TABLE_PREGUNTAS,
            values,
            "$COLUMN_MATERIA = ? AND $COLUMN_PREGUNTA = ?",
            arrayOf(materia, pregunta)
        )
    }
}