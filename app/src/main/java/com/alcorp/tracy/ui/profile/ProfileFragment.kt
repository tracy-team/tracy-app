package com.alcorp.tracy.ui.profile

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.alcorp.tracy.R
import com.alcorp.tracy.databinding.FragmentProfileBinding
import com.alcorp.tracy.ui.auth.LoginActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class ProfileFragment : Fragment() {

    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var binding: FragmentProfileBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sharedPreferences = requireContext().getSharedPreferences("account", Context.MODE_PRIVATE)

        binding.tvName.text = sharedPreferences.getString("name", "name")
        binding.tvUsername.text = sharedPreferences.getString("username", "username")
        binding.tvEmail.text = sharedPreferences.getString("email", "email")
        binding.tvPhone.text = sharedPreferences.getString("phone", "phoneNumber")
        binding.tvBirth.text = sharedPreferences.getString("dateOfBirth", "dateOfBirth")
        binding.tvAddress.text = sharedPreferences.getString("address", "address")

        Glide.with(requireContext())
            .load(sharedPreferences.getString("photo", "photoUrl"))
            .apply(RequestOptions().override(100, 100))
            .placeholder(ContextCompat.getDrawable(requireActivity().applicationContext, R.drawable.foto))
            .error(ContextCompat.getDrawable(requireActivity().applicationContext, R.drawable.foto))
            .into(binding.imgProfile)

        binding.ivUpdate.setOnClickListener {
            Toast.makeText(context, "This feature will available soon", Toast.LENGTH_SHORT).show()
        }

        binding.btnSignOut.setOnClickListener{
            signOut()
        }
    }

    private fun signOut() {
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        editor.clear().apply()
        startActivity(Intent(context, LoginActivity::class.java))
        activity?.finish()
    }
}