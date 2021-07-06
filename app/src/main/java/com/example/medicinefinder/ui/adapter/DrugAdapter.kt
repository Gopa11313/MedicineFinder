package com.example.medicinefinder.ui.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.medicinefinder.R
import com.example.medicinefinder.model.Drug
import com.example.medicinefinder.ui.activitymap.GoogleMapsActivity
import com.example.medicinefinder.ui.map.MapsFragment


class DrugAdapter(
    val list_Of_Drug:ArrayList<Drug>,
    val context: Context
): RecyclerView.Adapter<DrugAdapter.DrugViewholder>() {
    class DrugViewholder(view: View) : RecyclerView.ViewHolder(view) {
        var name: TextView
        val prescrip_view: TextView
        val deleteMadicine: ImageView
        init {
            name = view.findViewById(R.id.name)
            prescrip_view = view.findViewById(R.id.prescrip_view)
            deleteMadicine = view.findViewById(R.id.deleteMadicine)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DrugViewholder {
        val view= LayoutInflater.from(parent.context)
            .inflate(R.layout.drug,parent,false)
        return DrugViewholder(view)
    }

    override fun onBindViewHolder(holder: DrugViewholder, position: Int) {
        val drug=list_Of_Drug[position]
        holder.name.text=drug.name
        holder.prescrip_view.text=drug.prescription
        holder.deleteMadicine.setOnClickListener(){
            val intent=Intent(context,GoogleMapsActivity::class.java)
            intent.putExtra("id",drug.SellerId)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return list_Of_Drug.size
    }
}
