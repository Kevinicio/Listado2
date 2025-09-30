package com.example.liatadoalumnos

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.liatadoalumnos.databinding.ActivityRegistrarBinding

class RegistrarActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegistrarBinding
    private lateinit var dbHelper: DBHelperUsuario

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistrarBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dbHelper = DBHelperUsuario(this)

        binding.btnRegistrar.setOnClickListener {
            val nombre = binding.txtNombre.text.toString()
            val correo = binding.txtCorreo.text.toString()
            val pass = binding.txtContrasena.text.toString()
            val login = correo.substringBefore("@")

            if (nombre.isEmpty() || correo.isEmpty() || pass.isEmpty()) {
                Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show()
            } else {
                val registrado = dbHelper.registrarUsuario(login, pass, correo, nombre)
                if (registrado) {
                    Toast.makeText(this, "Usuario registrado exitosamente", Toast.LENGTH_SHORT).show()
                    finish()
                } else {
                    Toast.makeText(this, "Error al registrar usuario", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
