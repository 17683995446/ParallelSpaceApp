package com.parallelspace.app.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.parallelspace.app.ParallelSpaceApplication
import com.parallelspace.app.business_ability.clone_create.param.CloneCreateParam
import com.parallelspace.app.databinding.FragmentCreateCloneBinding

class CreateCloneFragment : Fragment() {
    private var _binding: FragmentCreateCloneBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCreateCloneBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
    }

    private fun setupViews() {
        binding.btnCreate.setOnClickListener {
            val cloneName = binding.editCloneName.text.toString().trim()
            val packageName = binding.editPackageName.text.toString().trim()
            if (validateInput(cloneName, packageName)) {
                createClone(cloneName, packageName)
            }
        }
        binding.btnCancel.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
    }

    private fun validateInput(cloneName: String, packageName: String): Boolean {
        if (cloneName.isEmpty()) {
            binding.editCloneName.error = "Clone name is required"
            return false
        }
        if (packageName.isEmpty()) {
            binding.editPackageName.error = "Package name is required"
            return false
        }
        return true
    }

    private fun createClone(cloneName: String, packageName: String) {
        val param = CloneCreateParam(
            packageName = packageName,
            cloneName = cloneName,
            cloneType = 1,
            sourcePackageName = packageName
        )
        binding.progressBar.visibility = View.VISIBLE
        binding.btnCreate.isEnabled = false
        val result = ParallelSpaceApplication.instance.cloneCreateImpl.createClone(param)
        binding.progressBar.visibility = View.GONE
        binding.btnCreate.isEnabled = true
        if (result.code == 0) {
            Toast.makeText(requireContext(), "Clone created successfully", Toast.LENGTH_SHORT).show()
            parentFragmentManager.popBackStack()
        } else {
            Toast.makeText(requireContext(), result.message, Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
