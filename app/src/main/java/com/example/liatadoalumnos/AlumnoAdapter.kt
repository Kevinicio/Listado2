package com.example.liatadoalumnos

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class AlumnoAdapter(
    private val listaAlumnos: MutableList<Alumno>,
    private val onLongClick: (position: Int) -> Unit
) : RecyclerView.Adapter<AlumnoAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imgAlumno: ImageView = itemView.findViewById(R.id.imgAlumno)
        val txtNombre: TextView = itemView.findViewById(R.id.txtNombre)
        val txtCuenta: TextView = itemView.findViewById(R.id.txtCuenta)
        val txtCorreo: TextView = itemView.findViewById(R.id.txtCorreo)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val vista = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_alumno, parent, false)
        return ViewHolder(vista)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val alumno = listaAlumnos[position]
        holder.txtNombre.text = alumno.nombre
        holder.txtCuenta.text = alumno.cuenta
        holder.txtCorreo.text = alumno.correo
        holder.imgAlumno.setImageResource(alumno.imagen)

        // 🔹 Long click para eliminar
        holder.itemView.setOnLongClickListener {
            onLongClick(position)
            true
        }
    }

    override fun getItemCount(): Int = listaAlumnos.size
}
