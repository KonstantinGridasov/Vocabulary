package com.gkreduction.vocabulary.presentation.ui.main

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.Settings
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.gkreduction.vocabulary.R
import com.gkreduction.vocabulary.databinding.ActivityMainBinding
import com.gkreduction.vocabulary.presentation.utils.getDataBdByUri
import com.gkreduction.vocabulary.presentation.utils.saveBdToFileJson
import dagger.hilt.android.AndroidEntryPoint
import java.lang.String.format


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    companion object {
        private const val REQUEST_CODE_PERMISSIONS = 1

        private val REQUIRED_PERMISSIONS =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                mutableListOf(
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                ).toTypedArray()
            } else {
                mutableListOf(
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                ).toTypedArray()
            }
    }

    private val viewModel by viewModels<MainViewModel>()
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private var isSave: Boolean = false

    private var resultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data: Intent? = result.data
                val items = getDataBdByUri(this, data?.data!!)
                viewModel.saveToDb(items)
            }
        }

    private var permissionLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (isSave)
            loadBaseListFromDb()
        else
            uploadFromFile()

    }


    // region AppCompatActivity
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        navController = findNavController(R.id.nav_host_fragment_content_main)
        initListeners()

    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (hasPermissions()) {
                if (isSave)
                    loadBaseListFromDb()
                else
                    uploadFromFile()
            }
        }
    }


    //    endregion

    fun checkPermission(isSave: Boolean) {
        this.isSave = isSave
        if (hasPermissions()) {
            if (isSave)
                loadBaseListFromDb()
            else
                uploadFromFile()
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                try {
                    val intent = Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION)
                    intent.addCategory("android.intent.category.DEFAULT")
                    intent.data = Uri.parse(format("package:%s", this.packageName))
                    permissionLauncher.launch(intent)
                } catch (e: Exception) {
                    val intent = Intent()
                    intent.action = Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION
                    permissionLauncher.launch(intent)
                }
            } else {
                ActivityCompat.requestPermissions(
                    this, REQUIRED_PERMISSIONS, REQUEST_CODE_PERMISSIONS
                )
            }

        }

    }

    private fun hasPermissions(): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            Environment.isExternalStorageManager()
        } else
            (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED)
    }


    private fun initListeners() {
        binding.toolbar.setListenerImage { navigateSettings() }
        binding.toolbar.setListenerText { navigateToList() }
        viewModel.baseWords.observe(this) { list ->
            saveBdToFileJson(list, this)
        }
        navController.addOnDestinationChangedListener { _, destination, _ ->
            setNameButtonByDestination(destinationId = destination.id)
            binding.toolbar.setVisibilityImageToolbar(destinationId = destination.id)
        }
    }

    private fun setNameButtonByDestination(destinationId: Int) {
        when (destinationId) {
            R.id.SettingsFragment -> binding.buttonNext.text =
                this.resources.getString(R.string.save_settings)
            R.id.ExamFragment -> binding.buttonNext.text =
                this.resources.getString(R.string.next)
            R.id.ListFragment -> binding.buttonNext.text =
                this.resources.getString(R.string.add)

            R.id.AddFragment -> binding.buttonNext.text =
                this.resources.getString(R.string.save_settings)
        }
    }


    fun setToolbarName(name: String) {
        binding.toolbar.setTextName(name)
    }


    fun setListenerButton(listener: () -> Unit) {
        binding.buttonNext.setOnClickListener { listener.invoke() }
    }


    private fun loadBaseListFromDb() {
        viewModel.getList()
    }


    fun navigateUp() {
        navController.navigateUp()
    }

    private fun uploadFromFile() {
        if (hasPermissions()) {
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.addCategory(Intent.CATEGORY_OPENABLE)
            intent.type = "*/*"
            val i = Intent.createChooser(intent, "Vocabulary")
            resultLauncher.launch(i)
        }
    }

    private fun navigateSettings() {
        navController.navigate(R.id.SettingsFragment)
    }

    fun navigateToAdd() {
        navController.navigate(R.id.AddFragment)
    }

    fun navigateToList() {
        navController.navigate(R.id.ListFragment)
    }


}
