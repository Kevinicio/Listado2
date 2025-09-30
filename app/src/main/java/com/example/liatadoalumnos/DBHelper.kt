package com.example.liatadoalumnos

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "escuela.db"
        private const val DATABASE_VERSION = 1
    }

    override fun onCreate(db: SQLiteDatabase) {
        // Tabla usuarios
        db.execSQL("""
            CREATE TABLE usuarios (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                nombre TEXT,
                correo TEXT UNIQUE,
                contrasena TEXT
            )
        """.trimIndent())

        // Tabla alumnos
        db.execSQL("""
            CREATE TABLE alumnos (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                nombre TEXT,
                matricula TEXT,
                correo TEXT
            )
        """.trimIndent())
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS usuarios")
        db.execSQL("DROP TABLE IF EXISTS alumnos")
        onCreate(db)
    }

    // Registrar usuario
    fun registrarUsuario(nombre: String, correo: String, contrasena: String): Long {
        val db = writableDatabase
        val values = ContentValues().apply {
            put("nombre", nombre)
            put("correo", correo)
            put("contrasena", contrasena)
        }
        val res = db.insert("usuarios", null, values)
        db.close()
        return res
    }

    // Validar login
    fun loginUsuario(correo: String, contrasena: String): Boolean {
        val db = readableDatabase
        val cursor = db.rawQuery(
            "SELECT * FROM usuarios WHERE correo=? AND contrasena=?",
            arrayOf(correo, contrasena)
        )
        val existe = cursor.moveToFirst()
        cursor.close()
        db.close()
        return existe
    }
}
