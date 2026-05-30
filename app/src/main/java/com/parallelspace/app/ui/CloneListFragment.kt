package com.parallelspace.app.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.parallelspace.app.ParallelSpaceApplication
import com.parallelspace.app.R
import com.parallelspace.app.business_ability.clone_create.dto.CloneInfoDto
import com.parallelspace.app.business_ability.clone_create.enums.CloneStatusEnum
import com.parallelspace.app.business_ability.clone_create.param.CloneCreateParam
import com.parallelspace.app.business_ability.clone_manage.param.CloneManageParam
import com.parallelspace.app.databinding.FragmentCloneListBinding

class CloneListFragment : Fragment() {
    private var _binding: FragmentCloneListBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: CloneListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCloneListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setupFab()
        loadClones()
    }

    private fun setupRecyclerView() {
        adapter = CloneListAdapter(
            onCloneClick = { clone -> showCloneDetail(clone) },
            onCloneLongClick = { clone -> showDeleteDialog(clone) }
        )
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = adapter
    }

    private fun setupFab() {
        binding.fabAddClone.setOnClickListener {
            showCreateCloneDialog()
        }
    }

    private fun loadClones() {
        val result = ParallelSpaceApplication.instance.cloneManageImpl.getCloneList()
        if (result.code == 0 && result.data != null) {
            adapter.submitList(result.data)
            updateEmptyState(result.data.isEmpty())
        } else {
            showError(result.message)
        }
    }

    private fun showCloneDetail(clone: CloneInfoDto) {
        val fragment = CloneDetailFragment.newInstance(clone.cloneId)
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .addToBackStack(null)
            .commit()
    }

    private fun showDeleteDialog(clone: CloneInfoDto) {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(R.string.confirm_delete)
            .setMessage(R.string.delete_warning)
            .setNegativeButton(R.string.cancel) { dialog, _ -> dialog.dismiss() }
            .setPositiveButton(R.string.confirm) { _, _ -> deleteClone(clone) }
            .show()
    }

    private fun deleteClone(clone: CloneInfoDto) {
        val param = CloneManageParam(
            cloneId = clone.cloneId,
            packageName = clone.packageName,
            operationType = 0
        )
        val result = ParallelSpaceApplication.instance.cloneManageImpl.deleteClone(param)
        if (result.code == 0) {
            showSuccess(getString(R.string.success_clone_deleted))
            loadClones()
        } else {
            showError(result.message)
        }
    }

    private fun showCreateCloneDialog() {
        val fragment = CreateCloneFragment()
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .addToBackStack(null)
            .commit()
    }

    private fun updateEmptyState(isEmpty: Boolean) {
        binding.emptyState.visibility = if (isEmpty) View.VISIBLE else View.GONE
        binding.recyclerView.visibility = if (isEmpty) View.GONE else View.VISIBLE
    }

    private fun showError(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    private fun showSuccess(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
