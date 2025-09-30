package com.example.liatadoalumnos

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHelperUsuario(context: Context) : SQLiteOpenHelper(context, DB_name, null, DB_version) {

    companion object {
        const val DB_version = 1
        const val DB_name = "dbServicios"
        const val nomTabla = "usuarios"
        const val keyId = "id"
        const val usrLogin = "usrLogin"
        const val usrPass = "usrPass"
        const val usrEmail = "usrEmail"
        const val usrNombre = "usrNombre"

        val sqlCreate = """
            CREATE TABLE $nomTabla(
            $keyId INTEGER PRIMARY KEY AUTOINCREMENT,
            $usrLogin TEXT,
            $usrPass TEXT,
            $usrEmail TEXT,
            $usrNombre TEXT)
        """.trimIndent()
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(sqlCreate)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $nomTabla")
        onCreate(db)
    }

    override fun onDowngrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        onUpgrade(db, oldVersion, newVersion)
    }

    fun registrarUsuario(login: String, pass: String, correo: String, nombre: String): Boolean {
        val db = writableDatabase
        val newReg = ContentValues()
        newReg.put(usrLogin, login)
        newReg.put(usrPass, pass)
        newReg.put(usrEmail, correo)
        newReg.put(usrNombre, nombre)
        val res = db.insert(nomTabla, null, newReg)
        db.close()
        return res != -1L
    }

    fun loginUsuario(correo: String, pass: String): Boolean {
        val db = readableDatabase
        val cursor = db.rawQuery(
            "SELECT * FROM $nomTabla WHERE $usrEmail=? AND $usrPass=?",
            arrayOf(correo, pass)
        )
        val existe = cursor.moveToFirst()
        cursor.close()
        db.close()
        return existe
    }
}
