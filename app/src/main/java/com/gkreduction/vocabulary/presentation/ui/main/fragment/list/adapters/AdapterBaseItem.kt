package com.gkreduction.vocabulary.presentation.ui.main.fragment.list.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gkreduction.vocabulary.databinding.ItemsListBinding
import com.gkreduction.vocabulary.presentation.entity.BaseWord

class AdapterBaseItem :
    RecyclerView.Adapter<AdapterBaseItem.ViewHolder>() {

    private var items: List<BaseWord> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater: LayoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemsListBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    inner class ViewHolder(val binding: ItemsListBinding) :
        RecyclerView.ViewHolder(binding.root)


    override fun onBindViewHolder(holder: ViewHolder, @SuppressLint("RecyclerView") position: Int) {
        when (items[position]) {
            is BaseWord.Word -> {
                holder.binding.form2.visibility = View.GONE
                holder.binding.view2.visibility = View.GONE
                holder.binding.form3.visibility = View.GONE
                holder.binding.view3.visibility = View.GONE
                holder.binding.form1.text = (items[position] as BaseWord.Word).translate
                holder.binding.translate.text = (items[position] as BaseWord.Word).russian
            }
            is BaseWord.IrVerb -> {
                holder.binding.form2.visibility = View.VISIBLE
                holder.binding.view2.visibility = View.VISIBLE
                holder.binding.form3.visibility = View.VISIBLE
                holder.binding.view3.visibility = View.VISIBLE
                holder.binding.form1.text = (items[position] as BaseWord.IrVerb).form1
                holder.binding.form2.text = (items[position] as BaseWord.IrVerb).form2
                holder.binding.form3.text = (items[position] as BaseWord.IrVerb).form3
                holder.binding.translate.text = (items[position] as BaseWord.IrVerb).russian

            }
            is BaseWord.Idiom -> {
                holder.binding.form2.visibility = View.GONE
                holder.binding.view2.visibility = View.GONE
                holder.binding.form3.visibility = View.GONE
                holder.binding.view3.visibility = View.GONE
                holder.binding.form1.text = (items[position] as BaseWord.Idiom).translate
                holder.binding.translate.text = (items[position] as BaseWord.Idiom).russian
            }
        }

    }

    override fun getItemCount() = items.size


    @SuppressLint("NotifyDataSetChanged")
    fun updateItems(items: List<BaseWord>) {
        this.items = items
        notifyDataSetChanged()
    }
}