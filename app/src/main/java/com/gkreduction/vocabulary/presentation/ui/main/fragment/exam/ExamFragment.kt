package com.gkreduction.vocabulary.presentation.ui.main.fragment.exam

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.gkreduction.vocabulary.databinding.FragmentExamBinding
import com.gkreduction.vocabulary.presentation.entity.BaseWord
import com.gkreduction.vocabulary.presentation.ui.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ExamFragment : Fragment() {
    private val viewModel: ExamViewModel by viewModels()
    private var _binding: FragmentExamBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentExamBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getCardExam()
    }

    override fun onStart() {
        super.onStart()
        initListeners()

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    private fun setTextByBaseWord(word: BaseWord?) {
        var text = "TODO"
        if (word != null)
            text = when (word) {
                is BaseWord.Word -> word.text
                is BaseWord.Idiom -> word.text
                is BaseWord.IrVerb -> word.text
            }
        binding.textQuestion.text = text
    }


    private fun initListeners() {
        activity?.let {
            viewModel.baseWord.observe(it) { word ->
                setTextByBaseWord(word)
            }
        }
        if (activity is MainActivity) {
            (activity as MainActivity).setListenerButton {
                viewModel.getCardExam()
            }
        }
    }


}