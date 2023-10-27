package com.gkreduction.vocabulary.presentation.ui.main.fragment.list.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gkreduction.vocabulary.databinding.ItemTypesBinding
import com.gkreduction.vocabulary.presentation.entity.Types
import com.gkreduction.vocabulary.presentation.utils.getColorByStatus

class AdapterTypes(var listener: (Types) -> Unit) :
    RecyclerView.Adapter<AdapterTypes.ViewHolder>() {

    private var items = listOf(Types.WORD, Types.IRRVERB, Types.IDIOM)
    private var chooses: Types = Types.WORD
    private var oldPosition: Int = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater: LayoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemTypesBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    inner class ViewHolder(val binding: ItemTypesBinding) :
        RecyclerView.ViewHolder(binding.root)


    override fun onBindViewHolder(holder: ViewHolder, @SuppressLint("RecyclerView") position: Int) {
        holder.binding.textMainCategory.apply {
            text = items[position].string
        }.setOnClickListener {
            if ((items[position]) != chooses) {
                chooses = items[position]
                notifyItemChanged(oldPosition)
                listener.invoke(items[position])
                oldPosition = position
                activate(holder)

            }
        }
        if (items[position] == chooses)
            activate(holder)
        else
            deactivate(holder)
    }

    override fun getItemCount() = items.size

    private fun deactivate(holder: ViewHolder) {
        holder.binding.rootCategoriesMain.isSelected = false
        holder.binding.textMainCategory.setTextColor(getColorByStatus(false, holder.itemView))

    }

    @SuppressLint("NotifyDataSetChanged")
    private fun activate(holder: ViewHolder) {
        holder.binding.rootCategoriesMain.isSelected = true
        holder.binding.textMainCategory.setTextColor(getColorByStatus(true, holder.itemView))

    }

}