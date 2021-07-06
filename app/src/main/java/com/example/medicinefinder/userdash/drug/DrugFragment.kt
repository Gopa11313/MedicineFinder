package com.example.myapplication.userdash.drug

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.medicinefinder.R
import com.example.medicinefinder.api.ServiceBuilder
import com.example.medicinefinder.databinding.FragmentDrugBinding
import com.example.medicinefinder.model.Drug
import com.example.medicinefinder.repository.DrugRepository
import com.example.medicinefinder.ui.adapter.DrugAdapter
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.Main


class DrugFragment : Fragment() {
private lateinit var recyclar_for_drug:RecyclerView
    private lateinit var drugViewModel: DrugViewModel
    private var _binding: FragmentDrugBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        drugViewModel =
            ViewModelProvider(this).get(DrugViewModel::class.java)

        _binding = FragmentDrugBinding.inflate(inflater, container, false)
        val root: View = binding.root

//        val textView: TextView = binding.textHome
//        drugViewModel.text.observe(viewLifecycleOwner, Observer {
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
        recyclar_for_drug=view.findViewById(R.id.recyclar_for_drug)
        getalldrugs()
    }
    fun getalldrugs(){
        try{
            CoroutineScope(Dispatchers.IO).launch {
                val repo=DrugRepository()
                val res=repo.getAllDrug(ServiceBuilder.token!!,ServiceBuilder.id!!)
                if(res.success==true){
                    val data=res.data
                    val adapter= context?.let { DrugAdapter(data as ArrayList<Drug>, it) }
                    recyclar_for_drug.layoutManager=LinearLayoutManager(context)
                    recyclar_for_drug.adapter=adapter
                }
                else{
                    withContext(Main ){
                        Toast.makeText(context, "No data", Toast.LENGTH_SHORT).show()
                    }
                }
            }

        }catch (e:Exception){
            Toast.makeText(context, "$e", Toast.LENGTH_SHORT).show()
        }
    }
}