package com.example.geekstudiorickmorty.core

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment

abstract class BaseFragment<Binding : ViewDataBinding>(@LayoutRes private val layoutResId: Int) :
    Fragment() {

    private var _binding: Binding? = null
    protected val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = DataBindingUtil.inflate(inflater,layoutResId, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUi()
        setupObserver()
        if (!isNetworkAvailable(requireContext())) {
            noInternet()
        } else {
            internetConnected()
        }
    }

    abstract fun setupObserver()

    abstract fun setupUi()


    open fun isNetworkAvailable(context: Context?): Boolean {
        if (context == null) return false
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as
                ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val capabilities = connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
            if (capabilities != null) {
                when {
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> {
                        Toast.makeText(context, "Ваш интернет работает!!! ",
                            Toast.LENGTH_SHORT).show()
                        internetConnected()
                        return true
                    }
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> {
                        Toast.makeText(context, "Ваш интернет работает!!! ",
                            Toast.LENGTH_SHORT).show()
                        internetConnected()
                        return true
                    }
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> {
                        Toast.makeText(context, "Ваш интернет работает!!! ",
                            Toast.LENGTH_SHORT).show()
                        internetConnected()
                        return true
                    }
                }
            }
        }
        return false
    }

    abstract fun noInternet()

    abstract fun internetConnected()


}