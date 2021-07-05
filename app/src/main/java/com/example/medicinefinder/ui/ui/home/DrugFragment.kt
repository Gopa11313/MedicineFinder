package com.example.medicinefinder.ui.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
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
import com.example.medicinefinder.ui.adapter.DrugAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception

class DrugFragment : Fragment() {
//    private lateinit var recyclar_for_Drug: RecyclerView
    private lateinit var drugViewModel: DrugViewModel
    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        drugViewModel =
            ViewModelProvider(this).get(drugViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_drug, container, false)
//        val textView: TextView = root.findViewById(R.id.text_home)
//        bookViewModel.text.observe(viewLifecycleOwner, Observer {
//            textView.text = it
//        })
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        recyclar_for_Drug=view.findViewById(R.id.recyclar_for_Drug)
//        getallDrugs()
    }

    fun getallDrugs(){
        try {
                CoroutineScope(Dispatchers.IO).launch {
                  val repo=DrugRepository()
                    val response=repo.getAllDrug(ServiceBuilder.token!!,ServiceBuilder.id!!)
                    if(response.success==true){
                        val data=response.data
                        if(data!=null) {
                            withContext(Main) {
                                val adapter =
                                    activity?.let { DrugAdapter(data as ArrayList<Drug>, it) }
//                                recyclar_for_Drug.layoutManager = LinearLayoutManager(activity)
//                                recyclar_for_Drug.adapter = adapter
                            }
                        }
                        else{
                            withContext(Main){
                                Toast.makeText(context, "No values", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                    else{
                        withContext(Main){
                            Toast.makeText(context, "${response.msg}", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
        }catch (e:Exception){
            Toast.makeText(context, "$e", Toast.LENGTH_SHORT).show()
        }
    }
}