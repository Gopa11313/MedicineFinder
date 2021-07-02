package com.example.medicinefinder.ui.dashboard

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ListView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.medicinefinder.R
import com.example.medicinefinder.adapter.dashboradIteamAdapter
import com.example.medicinefinder.databinding.FragmentDashboardBinding
import com.example.medicinefinder.model.DashBoradIteam
import com.example.medicinefinder.ui.signup.SignupActivity

class DashboardFragment : Fragment() {

    private lateinit var dashboardViewModel: DashboardViewModel
    private var _binding: FragmentDashboardBinding? = null
    private lateinit var listView: ListView
    var adapter: dashboradIteamAdapter? = null
    private val DashBoradIteamAdpater: ArrayList<DashBoradIteam> = ArrayList()

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dashboardViewModel =
            ViewModelProvider(this).get(DashboardViewModel::class.java)

        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        val root: View = binding.root

//        val textView: TextView = binding.textDashboard
//        dashboardViewModel.text.observe(viewLifecycleOwner, Observer {
//            textView.text = it
//        })
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        listView=view.findViewById(R.id.listView)
        DashBoradIteamAdpater.add(DashBoradIteam("Info",
            R.drawable.ic_baseline_info_24))

        DashBoradIteamAdpater.add(DashBoradIteam("Help",
            R.drawable.ic_baseline_help_24))

//        DashBoradIteamAdpater.add(DashBoradIteam("Setting",
//            R.drawable.ic_baseline_settings_24))

        DashBoradIteamAdpater.add(DashBoradIteam("LogIn",
            R.drawable.ic_baseline_login_24))

        adapter = context?.let { dashboradIteamAdapter(it, DashBoradIteamAdpater) }
        listView.adapter = adapter

        listView.onItemClickListener =
            AdapterView.OnItemClickListener { parent, view, position, id ->
                val selectedItemText = parent.getItemAtPosition(position)
                if(selectedItemText==0){
//                    startActivity(Intent(context  ,UserProfileActivity::class.java))
                }
                else if(selectedItemText==4){
//                    logout()
                }
                else if( selectedItemText==2){
                    startActivity(Intent(context  ,SignupActivity::class.java))
                }
            }
    }
}