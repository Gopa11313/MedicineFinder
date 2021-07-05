package com.example.medicinefinder.ui.ui.uploaddrug

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.medicinefinder.R
import com.example.medicinefinder.databinding.FragmentGalleryBinding

class UploadDrugFragment : Fragment() {
private lateinit var medicine_name:EditText
private lateinit var prescription:EditText
private lateinit var upload:Button
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
        upload.setOnClickListener(){

        }
    }
    fun uploadMedicine(){

    }
}