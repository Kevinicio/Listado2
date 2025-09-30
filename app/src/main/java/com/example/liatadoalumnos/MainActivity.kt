package com.example.liatadoalumnos

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: AlumnoAdapter
    private lateinit var fabAgregar: FloatingActionButton
    private lateinit var listaAlumnos: MutableList<Alumno>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recyclerView)
        fabAgregar = findViewById(R.id.fabAgregar)

        listaAlumnos = mutableListOf(
            Alumno("Kevin Ramirez", "20101545", "kevin@mail.com", R.drawable.crow),
            Alumno("Luis Pedro", "20162030", "luis@mail.com", R.drawable.crow),
            Alumno("Carlos Enrique", "20111646", "carlos@mail.com", R.drawable.crow),
            Alumno("Cesar Josue", "20124020", "cesar@mail.com", R.drawable.crow),
            Alumno("Antonio de Jesús", "20112356", "antonio@mail.com", R.drawable.crow)
        )

        adapter = AlumnoAdapter(
            listaAlumnos,
            onEliminar = { position ->
                AlertDialog.Builder(this)
                    .setTitle("Eliminar Alumno")
                    .setMessage("¿Deseas eliminar a ${listaAlumnos[position].nombre}?")
                    .setPositiveButton("Sí") { _, _ ->
                        listaAlumnos.removeAt(position)
                        adapter.notifyItemRemoved(position)
                    }
                    .setNegativeButton("No", null)
                    .show()
            },
            onEditar = { position ->
                val alumno = listaAlumnos[position]
                val dialogView = layoutInflater.inflate(R.layout.dialog_agregar_alumno, null)
                val edtNombre = dialogView.findViewById<EditText>(R.id.edtNombre)
                val edtCuenta = dialogView.findViewById<EditText>(R.id.edtCuenta)
                val edtCorreo = dialogView.findViewById<EditText>(R.id.edtCorreo)

                edtNombre.setText(alumno.nombre)
                edtCuenta.setText(alumno.cuenta)
                edtCorreo.setText(alumno.correo)

                AlertDialog.Builder(this)
                    .setTitle("Editar Alumno")
                    .setView(dialogView)
                    .setPositiveButton("Guardar") { _, _ ->
                        val nombre = edtNombre.text.toString()
                        val cuenta = edtCuenta.text.toString()
                        val correo = edtCorreo.text.toString()
                        if (nombre.isNotEmpty() && cuenta.isNotEmpty() && correo.isNotEmpty()) {
                            listaAlumnos[position] = Alumno(nombre, cuenta, correo, R.drawable.crow)
                            adapter.notifyItemChanged(position)
                        }
                    }
                    .setNegativeButton("Cancelar", null)
                    .show()
            }
        )

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        fabAgregar.setOnClickListener {
            val dialogView = layoutInflater.inflate(R.layout.dialog_agregar_alumno, null)
            val edtNombre = dialogView.findViewById<EditText>(R.id.edtNombre)
            val edtCuenta = dialogView.findViewById<EditText>(R.id.edtCuenta)
            val edtCorreo = dialogView.findViewById<EditText>(R.id.edtCorreo)

            AlertDialog.Builder(this)
                .setTitle("Agregar Alumno")
                .setView(dialogView)
                .setPositiveButton("Agregar") { _, _ ->
                    val nombre = edtNombre.text.toString()
                    val cuenta = edtCuenta.text.toString()
                    val correo = edtCorreo.text.toString()
                    if (nombre.isNotEmpty() && cuenta.isNotEmpty() && correo.isNotEmpty()) {
                        listaAlumnos.add(Alumno(nombre, cuenta, correo, R.drawable.crow))
                        adapter.notifyItemInserted(listaAlumnos.size - 1)
                        recyclerView.scrollToPosition(listaAlumnos.size - 1)
                    }
                }
                .setNegativeButton("Cancelar", null)
                .show()
        }
    }
}
