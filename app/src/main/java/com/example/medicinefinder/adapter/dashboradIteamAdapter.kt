package com.example.medicinefinder.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.medicinefinder.R
import com.example.medicinefinder.model.DashBoradIteam

class dashboradIteamAdapter (private val context: Context, private val arrayList: java.util.ArrayList<DashBoradIteam>) : BaseAdapter() {

    private lateinit var title: TextView
    private lateinit var icon: ImageView
    override fun getCount(): Int {
        return arrayList.size
    }
    override fun getItem(position: Int): Any {
        return position
    }
    override fun getItemId(position: Int): Long {
        return position.toLong()
    }
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View? {
        var convertView = convertView
        convertView = LayoutInflater.from(context).inflate(R.layout.dashboarditeam, parent, false)
        title = convertView.findViewById(R.id.dsh_title)
        icon = convertView.findViewById(R.id.dsh_icon)
        title.text = arrayList[position].title
        icon.setImageResource(arrayList[position].icon)
        return convertView
    }
}