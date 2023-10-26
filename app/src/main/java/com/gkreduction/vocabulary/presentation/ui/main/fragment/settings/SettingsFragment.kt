package com.gkreduction.vocabulary.presentation.ui.main.fragment.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.gkreduction.vocabulary.R
import com.gkreduction.vocabulary.databinding.FragmentSettingsBinding
import com.gkreduction.vocabulary.presentation.entity.Settings
import com.gkreduction.vocabulary.presentation.ui.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingsFragment : Fragment() {
    private val viewModel: SettingsViewModel by viewModels()

    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getSettings()
    }

    override fun onStart() {
        super.onStart()
        initListeners()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initListeners() {
        if (activity is MainActivity) {
            (activity as MainActivity).setListenerButton {
                viewModel.saveSettings(getSettings())
                (activity as MainActivity).navigateUp()
            }
            binding.buttonUpload.setOnClickListener {
                (activity as MainActivity).checkPermission(isSave = false)
            }

            binding.buttonSave.setOnClickListener {
                (activity as MainActivity).checkPermission(isSave = true)
            }

            activity?.let {
                viewModel.settings.observe(it) { settings ->
                    binding.checkboxIdiom.isChecked = settings.isIdiom
                    binding.checkboxIrVerb.isChecked = settings.isIrVerbs
                    binding.checkboxWord.isChecked = settings.isWord

                }

            }
        }
    }


    private fun getSettings(): Settings {
        return Settings(
            isIdiom = binding.checkboxIdiom.isChecked, isIrVerbs = binding.checkboxIrVerb.isChecked,
            isWord = binding.checkboxWord.isChecked
        )
    }

}