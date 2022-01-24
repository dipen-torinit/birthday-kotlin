package com.birthday.kotlin.ui.birthdaylist

import android.util.Base64
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.birthday.kotlin.R
import com.birthday.kotlin.data.Person
import com.birthday.kotlin.databinding.BirthdayListItemBinding
import com.bumptech.glide.Glide

class BirthdayListAdapter(
    private val persons: List<Person>,
    private val onItemClick: (person: Person) -> Unit
) :
    RecyclerView.Adapter<BirthdayListAdapter.ViewHolder>() {

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    class ViewHolder(binding: BirthdayListItemBinding) : RecyclerView.ViewHolder(binding.root) {
        val personImageView: ImageView = binding.personIv
        val nameTextView: TextView = binding.nameTextview
        val emailTextView: TextView = binding.emailTextview
        val phoneTextView: TextView = binding.phoneTextview
        val dobTextView: TextView = binding.dobTextview
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = BirthdayListItemBinding
            .inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        viewHolder.apply {
            Glide.with(personImageView.context)
                .load(Base64.decode(persons[position].image, Base64.DEFAULT))
                .placeholder(R.drawable.ic_app_icon)
                .error(R.drawable.ic_app_icon)
                .into(personImageView)
            nameTextView.text = persons[position].name
            emailTextView.text = persons[position].email
            phoneTextView.text = persons[position].phone
            dobTextView.text = persons[position].date

            itemView.setOnClickListener {
                onItemClick.invoke(persons[position])
            }
        }
    }

    override fun getItemCount() = persons.size

}