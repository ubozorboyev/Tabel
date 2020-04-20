package com.example.tabel.ui

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavigatorProvider
import androidx.navigation.fragment.findNavController
import cn.pedant.SweetAlert.SweetAlertDialog
import com.example.tabel.R
import com.example.tabel.databinding.LoginFragmentBinding
import com.example.tabel.utils.Constant
import com.example.tabel.utils.SharedPreferense
import com.example.tabel.viewmodel.LoginViewModel

class LoginFragment :BaseFragment<LoginFragmentBinding>(R.layout.login_fragment),View.OnClickListener{

    private val viewModel by viewModels<LoginViewModel>()
    private val dialog by lazy { SweetAlertDialog(context) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding.loginButton.setOnClickListener(this)
        editTextListener()

        viewModel.statusError.observe(viewLifecycleOwner, Observer {
            dialog.dismiss()
            dialog.changeAlertType(SweetAlertDialog.ERROR_TYPE)
            dialog.setTitleText(it)
            dialog.setConfirmButton("Ok",object :SweetAlertDialog.OnSweetClickListener {
                override fun onClick(sweetAlertDialog: SweetAlertDialog?) {
                    sweetAlertDialog?.dismiss()
                    binding.inputLogin.setText("")
                    binding.inputPassword.setText("")
                }

            }).show()
        })

        viewModel.userId.observe(viewLifecycleOwner, Observer {
            dialog.dismiss()
            findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
        })
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.loginButton->{
                val username=binding.inputLogin.text.toString()
                val password=binding.inputPassword.text.toString()

                if (verification(username,password)) {
                    dialog.changeAlertType(SweetAlertDialog.PROGRESS_TYPE)
                    dialog.setTitleText("Loadding...")
                    dialog.show()
                    Constant.userName=username
                    Constant.password=password
                    viewModel.loginUser()
                }
            }
        }
    }


    fun editTextListener(){

     binding.inputLogin.addTextChangedListener(object :TextWatcher{

         override fun afterTextChanged(s: Editable?) {
             if (!s.isNullOrEmpty()) binding.loginLaytout.isErrorEnabled=false
         }

         override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) { }

         override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) { }
     })

        binding.inputPassword.addTextChangedListener(object :TextWatcher{

            override fun afterTextChanged(s: Editable?) {
                if (!s.isNullOrEmpty()) binding.passworLayout.isErrorEnabled=false
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) { }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) { }
        })
    }

    fun verification(userName:String,password:String):Boolean{
        var valid=true

        if (userName.isNullOrEmpty()){
            binding.loginLaytout.error="Login kiritilishi kerak"
            valid=false
        }

        if (password.isNullOrEmpty()){
            binding.passworLayout.error="Password kiritilishi kerak"
             valid=false
        }
        return valid
    }

    override fun onAttach(context: Context) {

        val login= SharedPreferense.getLogin()
        val password= SharedPreferense.getPassword()

        if (!login.isNullOrEmpty() && !password.isNullOrEmpty()){

            dialog.dismiss()
            Constant.userName=login
            Constant.password=password
           findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
        }

        super.onAttach(context)
    }

}