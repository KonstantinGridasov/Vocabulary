package com.gkreduction.vocabulary.presentation.ui.main

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.gkreduction.vocabulary.R
import com.gkreduction.vocabulary.databinding.ActivityMainBinding
import com.gkreduction.vocabulary.presentation.utils.getList
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val viewModel by viewModels<MainViewModel>()
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    private var resultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data: Intent? = result.data
                val items = getList(this, data?.data!!)
                viewModel.saveToDb(items)
            }
        }

    // region AppCompatActivity
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        navController = findNavController(R.id.nav_host_fragment_content_main)

    }


    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    //    endregion

    fun setToolbarName(name: String) {
        binding.toolbar.setTextName(name)
    }

    fun setToolbarNavigation() {
        binding.toolbar.setListenerToolbar { navController.navigateUp() }
    }

    fun setListenerButton(listener: () -> Unit) {
        binding.buttonNext.setOnClickListener { listener.invoke() }

    }

    private fun saveToFile() {
//        saveList()
    }

    private fun navigateToAdd() {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        navController.navigate(R.id.action_FirstFragment_to_SecondFragment)
    }

    private fun loadFromFile() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.addCategory(Intent.CATEGORY_OPENABLE)
        intent.type = "*/*"
        val i = Intent.createChooser(intent, "Vocabulary")
        resultLauncher.launch(i)
    }


}