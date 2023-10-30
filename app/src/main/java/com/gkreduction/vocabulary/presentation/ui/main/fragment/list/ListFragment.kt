package com.gkreduction.vocabulary.presentation.ui.main.fragment.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.gkreduction.vocabulary.databinding.FragmentListBinding
import com.gkreduction.vocabulary.presentation.entity.Types
import com.gkreduction.vocabulary.presentation.ui.main.MainActivity
import com.gkreduction.vocabulary.presentation.ui.main.fragment.list.adapters.AdapterBaseItem
import com.gkreduction.vocabulary.presentation.ui.main.fragment.list.adapters.AdapterTypes
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ListFragment : Fragment() {
    private val viewModel: ListViewModel by viewModels()

    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding!!

    private var adapterBase = AdapterBaseItem()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpRecyclerTypes()
        initListener()

    }

    private fun initListener() {
        activity?.let {
            viewModel.listWord.observe(it) { list ->
                adapterBase.updateItems(list)
            }

        }
        if (activity is MainActivity) {
            (activity as MainActivity).setListenerButton {
                (activity as MainActivity).navigateToAdd()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    private fun setUpRecyclerTypes() {
        val adapterTypes = AdapterTypes { getWordByTypes(it) }
        binding.listTypes.apply {
            layoutManager =
                LinearLayoutManager(activity).also {
                    it.orientation = LinearLayoutManager.HORIZONTAL
                }
            setHasFixedSize(true)
            adapter = adapterTypes
        }

        binding.listItems.apply {
            layoutManager = LinearLayoutManager(activity)
            setHasFixedSize(true)
            adapter = adapterBase
        }

        viewModel.getWord()

    }

    private fun getWordByTypes(types: Types) {
        when (types) {
            Types.WORD -> viewModel.getWord()
            Types.IRRVERB -> viewModel.getIrVerb()
            Types.IDIOM -> viewModel.getIdiom()
        }
    }



}