package com.gkreduction.vocabulary.presentation.ui.main.fragment.add

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.gkreduction.vocabulary.R
import com.gkreduction.vocabulary.databinding.FragmentAddBinding
import com.gkreduction.vocabulary.presentation.entity.BaseWord
import com.gkreduction.vocabulary.presentation.ui.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class AddFragment : Fragment() {
    private val viewModel: AddViewModel by viewModels()

    private var _binding: FragmentAddBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListener()

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    private fun initListener() {
        activity?.let {
            val adapter: ArrayAdapter<*> = ArrayAdapter.createFromResource(
                it,
                R.array.type_word,
                R.layout.items_type
            )
            binding.typeSpinner.adapter = adapter
        }

        if (activity is MainActivity) {
            (activity as MainActivity).setListenerButton {
                viewModel.save(getWord())
            }
        }

        binding.typeSpinner.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                initType(p2)
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }
        }
        activity?.let {
            viewModel.clear.observe(it) { clear ->
                clearFiled(clear)
            }
        }
    }

    private fun clearFiled(clear: Boolean?) {
        if (clear == true) {
            binding.editRussian.text?.clear()
            binding.editForm1.text?.clear()
            binding.editForm2.text?.clear()
            binding.editTranslate.text?.clear()
            viewModel.clear.value = false
        }
    }

    private fun initType(pos: Int) {
        when (pos) {
            0, 2 -> {
                binding.editForm1.visibility = View.GONE
                binding.editForm2.visibility = View.GONE
            }
            1 -> {
                binding.editForm1.visibility = View.VISIBLE
                binding.editForm2.visibility = View.VISIBLE
            }
        }
    }

    private fun getWord(): BaseWord? {
        if (checkEmptyFiled(binding.typeSpinner.selectedItemPosition))
            return when (binding.typeSpinner.selectedItemPosition) {
                0 -> BaseWord.Word(
                    russian = binding.editRussian.text.toString(),
                    translate = binding.editTranslate.text.toString()
                )
                1 -> BaseWord.IrVerb(
                    form1 = binding.editRussian.text.toString(),
                    form2 = binding.editForm1.text.toString(),
                    form3 = binding.editForm2.text.toString(),
                    russian = binding.editTranslate.text.toString()
                )
                2 -> BaseWord.Idiom(
                    russian = binding.editRussian.text.toString(),
                    translate = binding.editTranslate.text.toString()
                )
                else -> null
            }
        else
            return null
    }


    private fun checkEmptyFiled(position: Int): Boolean {
        return when (position) {
            0, 2 -> {
                !binding.editRussian.text.isNullOrEmpty() &&
                        !binding.editTranslate.text.isNullOrEmpty()
            }
            1 -> {
                !binding.editRussian.text.isNullOrEmpty() &&
                        !binding.editForm1.text.isNullOrEmpty() &&
                        !binding.editForm2.text.isNullOrEmpty() &&
                        !binding.editTranslate.text.isNullOrEmpty()


            }
            else -> false
        }
    }

}