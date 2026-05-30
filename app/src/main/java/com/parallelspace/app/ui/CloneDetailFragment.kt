package com.parallelspace.app.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.parallelspace.app.ParallelSpaceApplication
import com.parallelspace.app.business_ability.clone_manage.param.CloneManageParam
import com.parallelspace.app.databinding.FragmentCloneDetailBinding

class CloneDetailFragment : Fragment() {
    private var _binding: FragmentCloneDetailBinding? = null
    private val binding get() = _binding!!
    private var cloneId: String = ""

    companion object {
        private const val ARG_CLONE_ID = "clone_id"
        fun newInstance(cloneId: String): CloneDetailFragment {
            return CloneDetailFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_CLONE_ID, cloneId)
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        cloneId = arguments?.getString(ARG_CLONE_ID) ?: ""
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCloneDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadCloneDetail()
        setupButtons()
    }

    private fun loadCloneDetail() {
        val param = CloneManageParam(cloneId = cloneId, packageName = "", operationType = 0)
        val result = ParallelSpaceApplication.instance.cloneManageImpl.getCloneInfo(param)
        if (result.code == 0 && result.data != null) {
            binding.textCloneName.text = result.data.cloneName
            binding.textPackageName.text = result.data.packageName
            binding.textCloneId.text = result.data.cloneId
            binding.textStatus.text = when (result.data.status) {
                1 -> "Running"
                2 -> "Stopped"
                else -> "Unknown"
            }
        } else {
            Toast.makeText(requireContext(), "Failed to load clone details", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setupButtons() {
        binding.btnBack.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
