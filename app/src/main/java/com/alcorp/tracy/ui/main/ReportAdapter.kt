package com.alcorp.tracy.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.alcorp.tracy.data.remote.response.DataItemItem
import com.alcorp.tracy.databinding.ItemReportBinding

class ReportAdapter(private val listItem: List<List<DataItemItem>>) : RecyclerView.Adapter<ReportAdapter.ListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding = ItemReportBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        with(holder) {
            with(listItem[position][position]) {
                binding.tvJenis.text = jenisKejahatan
                binding.tvKeterangan.text = uraian
            }
        }
    }

    override fun getItemCount(): Int = listItem.size

    inner class ListViewHolder(val binding: ItemReportBinding) : RecyclerView.ViewHolder(binding.root)

}