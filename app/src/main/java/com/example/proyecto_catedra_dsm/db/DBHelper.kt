// File: app/src/main/kotlin/com/example/proyecto_catedra_dsm/db/DBHelper.kt
package com.example.proyecto_catedra_dsm.db

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.proyecto_catedra_dsm.model.*

class DBHelper(context: Context) : SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {

    companion object {
        private const val DB_NAME    = "catedraEvaluacion.db"
        private const val DB_VERSION = 1
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL("""
            CREATE TABLE Usuario (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                nombre TEXT NOT NULL,
                email TEXT NOT NULL UNIQUE,
                password TEXT NOT NULL,
                fecha_registro TEXT NOT NULL
            )
        """.trimIndent())
        db.execSQL("""
            CREATE TABLE Materia (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                titulo TEXT NOT NULL,
                descripcion TEXT,
                id_usuario INTEGER NOT NULL,
                fecha_creacion TEXT NOT NULL,
                FOREIGN KEY(id_usuario) REFERENCES Usuario(id)
            )
        """.trimIndent())
        db.execSQL("""
            CREATE TABLE Pregunta (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                texto TEXT NOT NULL,
                id_materia INTEGER NOT NULL,
                FOREIGN KEY(id_materia) REFERENCES Materia(id)
            )
        """.trimIndent())
        db.execSQL("""
            CREATE TABLE Respuesta (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                texto TEXT NOT NULL,
                id_pregunta INTEGER NOT NULL,
                es_correcta INTEGER NOT NULL CHECK(es_correcta IN (0,1)),
                FOREIGN KEY(id_pregunta) REFERENCES Pregunta(id)
            )
        """.trimIndent())
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS Respuesta")
        db.execSQL("DROP TABLE IF EXISTS Pregunta")
        db.execSQL("DROP TABLE IF EXISTS Materia")
        db.execSQL("DROP TABLE IF EXISTS Usuario")
        onCreate(db)
    }


    fun addUsuario(u: Usuario): Long =
        writableDatabase.use { db ->
            ContentValues().run {
                put("nombre", u.nombre)
                put("email", u.email)
                put("password", u.password)
                put("fecha_registro", u.fechaRegistro)
                db.insert("Usuario", null, this)
            }
        }

    fun getUsuarioByEmail(email: String): Usuario? =
        readableDatabase.use { db ->
            db.query("Usuario", null, "email = ?", arrayOf(email), null, null, null).use { c ->
                if (!c.moveToFirst()) return@use null
                Usuario(
                    id             = c.getInt(c.getColumnIndexOrThrow("id")),
                    nombre         = c.getString(c.getColumnIndexOrThrow("nombre")),
                    email          = c.getString(c.getColumnIndexOrThrow("email")),
                    password       = c.getString(c.getColumnIndexOrThrow("password")),
                    fechaRegistro  = c.getString(c.getColumnIndexOrThrow("fecha_registro"))
                )
            }
        }

    fun updateUsuario(u: Usuario): Int =
        writableDatabase.use { db ->
            ContentValues().run {
                put("nombre", u.nombre)
                put("password", u.password)
                db.update("Usuario", this, "id = ?", arrayOf(u.id.toString()))
            }
        }

    fun deleteUsuario(id: Int): Int =
        writableDatabase.use { db ->
            db.delete("Usuario", "id = ?", arrayOf(id.toString()))
        }


    fun addMateria(m: Materia): Long =
        writableDatabase.use { db ->
            ContentValues().run {
                put("titulo", m.titulo)
                put("descripcion", m.descripcion)
                put("id_usuario", m.idUsuario)
                put("fecha_creacion", m.fechaCreacion)
                db.insert("Materia", null, this)
            }
        }

    fun getAllMaterias(usuarioId: Int): List<Materia> =
        mutableListOf<Materia>().apply {
            readableDatabase.use { db ->
                db.query(
                    "Materia", null,
                    "id_usuario = ?", arrayOf(usuarioId.toString()),
                    null, null, "fecha_creacion DESC"
                ).use { c ->
                    while (c.moveToNext()) add(
                        Materia(
                            id           = c.getInt(c.getColumnIndexOrThrow("id")),
                            titulo       = c.getString(c.getColumnIndexOrThrow("titulo")),
                            descripcion  = c.getString(c.getColumnIndexOrThrow("descripcion")),
                            idUsuario    = c.getInt(c.getColumnIndexOrThrow("id_usuario")),
                            fechaCreacion= c.getString(c.getColumnIndexOrThrow("fecha_creacion"))
                        )
                    )
                }
            }
        }

    fun updateMateria(m: Materia): Int =
        writableDatabase.use { db ->
            ContentValues().run {
                put("titulo", m.titulo)
                put("descripcion", m.descripcion)
                db.update("Materia", this, "id = ?", arrayOf(m.id.toString()))
            }
        }

    fun deleteMateria(id: Int): Int =
        writableDatabase.use { db ->
            db.delete("Materia", "id = ?", arrayOf(id.toString()))
        }


    fun addPregunta(p: Pregunta): Long =
        writableDatabase.use { db ->
            ContentValues().run {
                put("texto", p.texto)
                put("id_materia", p.idMateria)
                db.insert("Pregunta", null, this)
            }
        }

    fun getPreguntasPorMateria(materiaId: Int): List<Pregunta> =
        mutableListOf<Pregunta>().apply {
            readableDatabase.use { db ->
                db.query(
                    "Pregunta", null,
                    "id_materia = ?", arrayOf(materiaId.toString()),
                    null, null, null
                ).use { c ->
                    while (c.moveToNext()) add(
                        Pregunta(
                            id        = c.getInt(c.getColumnIndexOrThrow("id")),
                            texto     = c.getString(c.getColumnIndexOrThrow("texto")),
                            idMateria = c.getInt(c.getColumnIndexOrThrow("id_materia"))
                        )
                    )
                }
            }
        }

    fun updatePregunta(p: Pregunta): Int =
        writableDatabase.use { db ->
            ContentValues().run {
                put("texto", p.texto)
                db.update("Pregunta", this, "id = ?", arrayOf(p.id.toString()))
            }
        }

    fun deletePregunta(id: Int): Int =
        writableDatabase.use { db ->
            db.delete("Pregunta", "id = ?", arrayOf(id.toString()))
        }


    fun addRespuesta(r: Respuesta): Long =
        writableDatabase.use { db ->
            ContentValues().run {
                put("texto", r.texto)
                put("id_pregunta", r.idPregunta)
                put("es_correcta", if (r.esCorrecta) 1 else 0)
                db.insert("Respuesta", null, this)
            }
        }

    fun getRespuestasPorPregunta(preguntaId: Int): List<Respuesta> =
        mutableListOf<Respuesta>().apply {
            readableDatabase.use { db ->
                db.query(
                    "Respuesta", null,
                    "id_pregunta = ?", arrayOf(preguntaId.toString()),
                    null, null, null
                ).use { c ->
                    while (c.moveToNext()) add(
                        Respuesta(
                            id          = c.getInt(c.getColumnIndexOrThrow("id")),
                            texto       = c.getString(c.getColumnIndexOrThrow("texto")),
                            idPregunta  = c.getInt(c.getColumnIndexOrThrow("id_pregunta")),
                            esCorrecta  = c.getInt(c.getColumnIndexOrThrow("es_correcta")) == 1
                        )
                    )
                }
            }
        }

    fun updateRespuesta(r: Respuesta): Int =
        writableDatabase.use { db ->
            ContentValues().run {
                put("texto", r.texto)
                put("es_correcta", if (r.esCorrecta) 1 else 0)
                db.update("Respuesta", this, "id = ?", arrayOf(r.id.toString()))
            }
        }

    fun deleteRespuesta(id: Int): Int =
        writableDatabase.use { db ->
            db.delete("Respuesta", "id = ?", arrayOf(id.toString()))
        }

    fun deleteRespuestasPorPregunta(preguntaId: Int): Int =
        writableDatabase.use { db ->
            db.delete("Respuesta", "id_pregunta = ?", arrayOf(preguntaId.toString()))
        }


    fun getMateriaById(id: Int): Materia? =
        readableDatabase.use { db ->
            db.query("Materia", null, "id = ?", arrayOf(id.toString()),
                null, null, null).use { c ->
                return if (c.moveToFirst()) {
                    Materia(c.getInt(c.getColumnIndexOrThrow("id")),
                        c.getString(c.getColumnIndexOrThrow("titulo")),
                        c.getString(c.getColumnIndexOrThrow("descripcion")))
                } else null
            }
        }
    fun getRespuestaById(id: Int): Respuesta? {
        readableDatabase.use { db ->
            val c: Cursor = db.query(
                "Respuesta", null,
                "id = ?", arrayOf(id.toString()),
                null, null, null
            )
            return c.use {
                if (it.moveToFirst()) {
                    Respuesta(
                        id = it.getInt(it.getColumnIndexOrThrow("id")),
                        texto = it.getString(it.getColumnIndexOrThrow("texto")),
                        idPregunta = it.getInt(it.getColumnIndexOrThrow("id_pregunta")),
                        esCorrecta = it.getInt(it.getColumnIndexOrThrow("es_correcta")) == 1
                    )
                } else null
            }
        }
    }








}
