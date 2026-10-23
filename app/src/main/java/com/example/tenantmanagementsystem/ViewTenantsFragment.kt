package com.example.tenantmanagementsystem

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.tenantmanagementsystem.databinding.FragmentViewTenantsBinding

class ViewTenantsFragment : Fragment() {
    private var _binding: FragmentViewTenantsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentViewTenantsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val tenant = arguments?.getSerializable("newTenant") as? Tenant ?: TenantRepository.tenants.lastOrNull()

        if (tenant != null) {
            binding.tenantResultTextView.text = tenant.summary()
        } else {
            binding.tenantResultTextView.text = "No tenant found. Please add one."
        }

        binding.callButton.setOnClickListener {
            if (tenant == null) {
                Toast.makeText(requireContext(), "Save a tenant first", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:${tenant.phone}"))
            startActivity(intent)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
