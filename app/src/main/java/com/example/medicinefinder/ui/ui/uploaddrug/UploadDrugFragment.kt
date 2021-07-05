package com.example.medicinefinder.ui.ui.uploaddrug

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.medicinefinder.R
import com.example.medicinefinder.api.ServiceBuilder
import com.example.medicinefinder.databinding.FragmentGalleryBinding
import com.example.medicinefinder.model.Drug
import com.example.medicinefinder.repository.DrugRepository
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception

class UploadDrugFragment : Fragment() {
private lateinit var medicine_name:EditText
private lateinit var prescription:EditText
private lateinit var upload:Button
private lateinit var upload_drug_layout:LinearLayout
    private lateinit var uploadDrugViewModel: UploadDrugViewModel
    private var _binding: FragmentGalleryBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        uploadDrugViewModel =
            ViewModelProvider(this).get(UploadDrugViewModel::class.java)

        _binding = FragmentGalleryBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        medicine_name=view.findViewById(R.id.medicine_name)
        prescription=view.findViewById(R.id.prescription)
        upload=view.findViewById(R.id.upload)
        upload_drug_layout=view.findViewById(R.id.upload_drug_layout)
        upload.setOnClickListener(){
uploadMedicine()
        }
    }
    fun uploadMedicine(){
        try {
            if (validation()) {
                var drug = Drug(
                    name = medicine_name.text.toString(),
                    prescription = prescription.text.toString(),
                    SellerId = ServiceBuilder.id!!
                )
                CoroutineScope(Dispatchers.IO).launch {
                    val repo = DrugRepository()
                    val response = repo.UploadDrug(drug)
                    if (response.success == true) {
                        withContext(Main) {
                            val  snack=
                                Snackbar.make(upload_drug_layout, "${response.msg}", Snackbar.LENGTH_LONG)
                            snack.show()
                            clear()
                        }
                    } else {
                        withContext(Main) {
                            Toast.makeText(context, "${response.msg}", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
            else{
                val  snack=
                    Snackbar.make(upload_drug_layout, "Please fill all the requirements", Snackbar.LENGTH_LONG)
                snack.show()
            }
            }
            catch(e:Exception) {
                Toast.makeText(context, "$e", Toast.LENGTH_SHORT).show()
            }

    }
    fun validation():Boolean {
        return !(medicine_name.text.toString().isEmpty()&& prescription.text.toString().isEmpty())
    }
    fun clear(){
        medicine_name.setText("")
        prescription.setText("")
    }
}