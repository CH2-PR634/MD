
package com.capstone.vsl.ui.main.moduls

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.capstone.vsl.data.remote.response.DataAlphaItem
import com.capstone.vsl.data.remote.response.ModulesResponse
import com.capstone.vsl.data.remote.retrofit.ApiConfig
import com.capstone.vsl.databinding.FragmentModulsBinding
import com.capstone.vsl.ui.signin.SignInActivity
import com.google.firebase.auth.FirebaseAuth
import android.util.Log
import com.capstone.vsl.R
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ModulsFragment : Fragment() {

    private lateinit var binding: FragmentModulsBinding
    private lateinit var firebaseAuth: FirebaseAuth

    private val apiService = ApiConfig.getApiService()

    private val quotes: Array<String> by lazy {
        resources.getStringArray(R.array.quotes)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentModulsBinding.inflate(inflater, container, false)

//        Sign Out Button
        val ivSignOut : ImageView = binding.ivLogout
        ivSignOut.setOnClickListener {
            signOut()
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val tvQuotes = binding.tvQuotes
        val randomIndex = quotes.indices.random()
        val randomQuote = quotes[randomIndex]
        tvQuotes.text = randomQuote

//        ImageView Modules
        binding.moduleAF.setOnClickListener { getModulesAF() }
        binding.moduleGK.setOnClickListener { getModulesGK() }
        binding.moduleLO.setOnClickListener { getModulesLO() }
        binding.modulePR.setOnClickListener { getModulesPR() }
        binding.moduleSU.setOnClickListener { getModulesSU() }
        binding.moduleVZ.setOnClickListener { getModulesVZ() }
    }

//    SignOut function
    private fun signOut() {
        firebaseAuth = FirebaseAuth.getInstance()
        firebaseAuth.signOut()

        val intent = Intent(requireContext(), SignInActivity::class.java)
        startActivity(intent)
        requireActivity().finish()
    }

//    getModules function
    private fun getModulesAF() {
        val call = apiService.getModulesAF()
        call.enqueue(object : Callback<ModulesResponse> {
            override fun onResponse(call: Call<ModulesResponse>, response: Response<ModulesResponse>) {
                if (response.isSuccessful) {
                    val modulesResponse = response.body()
                    modulesResponse?.dataAlpha?.let { dataAlphaItems ->

                        navigateToDetailActivity(dataAlphaItems)
                    }
                } else {
                    // Handle response error
                    Log.e("ModulesFragment", "Error: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<ModulesResponse>, t: Throwable) {
                // Handle network failure
                Log.e("ModulesFragment", "Network Error: ${t.message}")
            }
        })
    }

    private fun getModulesGK() {
        val call = apiService.getModulesGK()
        call.enqueue(object : Callback<ModulesResponse> {
            override fun onResponse(call: Call<ModulesResponse>, response: Response<ModulesResponse>) {
                if (response.isSuccessful) {
                    val modulesResponse = response.body()
                    modulesResponse?.dataAlpha?.let { dataAlphaItems ->

                        navigateToDetailActivity(dataAlphaItems)
                    }
                } else {
                    // Handle response error
                    Log.e("ModulesFragment", "Error: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<ModulesResponse>, t: Throwable) {
                // Handle network failure
                Log.e("ModulesFragment", "Network Error: ${t.message}")
            }
        })
    }

    private fun getModulesLO() {
        val call = apiService.getModulesLO()
        call.enqueue(object : Callback<ModulesResponse> {
            override fun onResponse(call: Call<ModulesResponse>, response: Response<ModulesResponse>) {
                if (response.isSuccessful) {
                    val modulesResponse = response.body()
                    modulesResponse?.dataAlpha?.let { dataAlphaItems ->

                        navigateToDetailActivity(dataAlphaItems)
                    }
                } else {
                    // Handle response error
                    Log.e("ModulesFragment", "Error: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<ModulesResponse>, t: Throwable) {
                // Handle network failure
                Log.e("ModulesFragment", "Network Error: ${t.message}")
            }
        })
    }

    private fun getModulesPR() {
        val call = apiService.getModulesPR()
        call.enqueue(object : Callback<ModulesResponse> {
            override fun onResponse(call: Call<ModulesResponse>, response: Response<ModulesResponse>) {
                if (response.isSuccessful) {
                    val modulesResponse = response.body()
                    modulesResponse?.dataAlpha?.let { dataAlphaItems ->

                        navigateToDetailActivity(dataAlphaItems)
                    }
                } else {
                    // Handle response error
                    Log.e("ModulesFragment", "Error: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<ModulesResponse>, t: Throwable) {
                // Handle network failure
                Log.e("ModulesFragment", "Network Error: ${t.message}")
            }
        })
    }

    private fun getModulesSU() {
        val call = apiService.getModulesSU()
        call.enqueue(object : Callback<ModulesResponse> {
            override fun onResponse(call: Call<ModulesResponse>, response: Response<ModulesResponse>) {
                if (response.isSuccessful) {
                    val modulesResponse = response.body()
                    modulesResponse?.dataAlpha?.let { dataAlphaItems ->

                        navigateToDetailActivity(dataAlphaItems)
                    }
                } else {
                    // Handle response error
                    Log.e("ModulesFragment", "Error: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<ModulesResponse>, t: Throwable) {
                // Handle network failure
                Log.e("ModulesFragment", "Network Error: ${t.message}")
            }
        })
    }

    private fun getModulesVZ() {
        val call = apiService.getModulesVZ()
        call.enqueue(object : Callback<ModulesResponse> {
            override fun onResponse(call: Call<ModulesResponse>, response: Response<ModulesResponse>) {
                if (response.isSuccessful) {
                    val modulesResponse = response.body()
                    modulesResponse?.dataAlpha?.let { dataAlphaItems ->

                        navigateToDetailActivity(dataAlphaItems)
                    }
                } else {
                    // Handle response error
                    Log.e("ModulesFragment", "Error: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<ModulesResponse>, t: Throwable) {
                // Handle network failure
                Log.e("ModulesFragment", "Network Error: ${t.message}")
            }
        })
    }

//    Intent to DetailModulesActivity
    private fun navigateToDetailActivity(dataAlphaItems: List<DataAlphaItem?>) {
        val intent = Intent(activity, DetailModulesActivity::class.java)
        intent.putParcelableArrayListExtra("dataAlphaItems", ArrayList(dataAlphaItems))
        startActivity(intent)
    }
}