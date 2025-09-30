package com.example.liatadoalumnos

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class AlumnoAdapter(
    private val listaAlumnos: MutableList<Alumno>,
    private val onEliminar: (position: Int) -> Unit,
    private val onEditar: (position: Int) -> Unit
) : RecyclerView.Adapter<AlumnoAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imgAlumno: ImageView = itemView.findViewById(R.id.imgAlumno)
        val txtNombre: TextView = itemView.findViewById(R.id.txtNombre)
        val txtCuenta: TextView = itemView.findViewById(R.id.txtCuenta)
        val txtCorreo: TextView = itemView.findViewById(R.id.txtCorreo)
        val btnMenu: ImageButton = itemView.findViewById(R.id.btnMenu)
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

        holder.btnMenu.setOnClickListener {
            val popup = PopupMenu(holder.itemView.context, holder.btnMenu)
            popup.menuInflater.inflate(R.menu.menu_alumno, popup.menu)
            popup.setOnMenuItemClickListener { item ->
                when(item.itemId) {
                    R.id.menu_editar -> { onEditar(position); true }
                    R.id.menu_eliminar -> { onEliminar(position); true }
                    else -> false
                }
            }
            popup.show()
        }
    }

    override fun getItemCount(): Int = listaAlumnos.size
}
