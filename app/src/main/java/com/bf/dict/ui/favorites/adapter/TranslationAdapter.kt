package com.bf.dict.ui.favorites.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bf.dict.R
import com.bf.dict.domain.repository.Translation
import kotlinx.android.synthetic.main.favorite_item.view.*


class TranslationAdapter :
        RecyclerView.Adapter<TranslationAdapter.ViewHolder>() {

     var translations = ArrayList<Translation>()
     var onItemClick: ((Translation) -> Unit)? = null
     var onDeleteClick: ((Translation) -> Unit)? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.favorite_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val translation = translations[position]

        holder.bindTranslation(translation)
        holder.itemView.listTitleTv.text = translation.text
        holder.itemView.listTranslatedTv.text = translation.translated
        holder.itemView.listLangsTv.text = translation.direction

        holder.itemView.listFavoriteIv.setOnClickListener {
            translations[position] = translation
            onDeleteClick?.invoke(translation)
        }

        holder.itemView.setOnClickListener {
            onItemClick?.invoke(translation)
        }
    }

    override fun getItemCount(): Int {
        return translations.size
    }

    class ViewHolder internal constructor(view: View) : RecyclerView.ViewHolder(view) {

        lateinit var translation: Translation
            private set

        fun bindTranslation(translation: Translation) {
            this.translation = translation

        }
    }
}
