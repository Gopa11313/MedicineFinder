package com.example.medicinefinder.ui.home

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.SearchView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.medicinefinder.R
import com.example.medicinefinder.api.ServiceBuilder
import com.example.medicinefinder.databinding.FragmentHomeBinding
import com.example.medicinefinder.model.Drug
import com.example.medicinefinder.repository.DrugRepository
import com.example.medicinefinder.response.DrugResponse
import com.example.medicinefinder.ui.adapter.DrugAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private lateinit var searchView: SearchView
    private lateinit var rcy_home_drugs: RecyclerView
    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

//        val textView: TextView = binding.textHome
//        homeViewModel.text.observe(viewLifecycleOwner, Observer {
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
        searchView=view.findViewById(R.id.searchView)
        rcy_home_drugs=view.findViewById(R.id.rcy_home_drugs)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                search(query)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
//                search(newText)
                return true
            }
        })
    }

    private fun search(text: String?) {
            try {
                CoroutineScope(Dispatchers.IO).launch {
                    val repo=DrugRepository()
                    val drug=Drug(search=text)
                    val res=repo.searchDrug(drug)
                    if(res.success==true){
                        val data=res.data
                        withContext(Main) {
                            val adapter = context?.let { DrugAdapter(data as ArrayList<Drug>, it) }
                            rcy_home_drugs.layoutManager = LinearLayoutManager(activity)
                            rcy_home_drugs.adapter = adapter
                        }
                    }
                    else{
                        withContext(Main){
                            Toast.makeText(context, "${res.msg}", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }catch (e:Exception){
                Toast.makeText(context, "$e", Toast.LENGTH_SHORT).show()
            }
        }
}

