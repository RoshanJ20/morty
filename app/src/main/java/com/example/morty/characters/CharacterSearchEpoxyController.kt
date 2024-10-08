package com.example.morty.characters

import com.airbnb.epoxy.EpoxyModel
import com.airbnb.epoxy.kotlinsample.helpers.ViewBindingKotlinModel
import com.airbnb.epoxy.paging3.PagingDataEpoxyController
import com.example.morty.R
import com.example.morty.databinding.ModelCharacterListItemBinding
import com.example.morty.databinding.ModelLocalExceptionErrorStateBinding
import com.example.morty.domain.models.Character
import com.example.morty.epoxy.LoadingEpoxyModel
import com.squareup.picasso.Picasso

class CharacterSearchEpoxyController(
    private val onCharacterSelected: (Int) -> Unit
): PagingDataEpoxyController<Character>() {

    var localException: CharacterSearchPagingSource.LocalException? = null
        set(value) {
            field = value
            if (localException != null) {
                requestModelBuild()
            }
        }

    override fun buildItemModel(currentPosition: Int, item: Character?): EpoxyModel<*> {
        return CharacterGridItemEpoxyModel(
            characterId = item!!.id,
            imageUrl = item.image,
            name = item.name,
            onCharacterSelected = { characterId ->
                onCharacterSelected(characterId)
            }
        ).id(item.id)
    }

    override fun addModels(models: List<EpoxyModel<*>>) {

        localException?.let{
            LocalExceptionErrorStateEpoxyModel(it).id("error_state").addTo(this)
            return
        }

        if(models.isEmpty()){
            LoadingEpoxyModel().id("loading").addTo(this)
            return
        }
        super.addModels(models)
    }

    data class CharacterGridItemEpoxyModel(
        val characterId: Int,
        val imageUrl: String,
        val name: String,
        val onCharacterSelected : (Int) -> Unit
    ): ViewBindingKotlinModel<ModelCharacterListItemBinding>(R.layout.model_character_list_item) {
        override fun ModelCharacterListItemBinding.bind() {
            Picasso.get().load(imageUrl).into(characterImageView)
            characterNameTextView.text = name

            root.setOnClickListener{
                onCharacterSelected(characterId)
            }
        }
    }

    data class LocalExceptionErrorStateEpoxyModel(
        val localException: CharacterSearchPagingSource.LocalException
    ): ViewBindingKotlinModel<ModelLocalExceptionErrorStateBinding>(R.layout.model_local_exception_error_state) {
        override fun ModelLocalExceptionErrorStateBinding.bind() {
            titleTextView.text = localException.title
            descriptionTextView.text = localException.description

        }

        override fun getSpanSize(totalSpanCount: Int, position: Int, itemCount: Int): Int {
            return totalSpanCount
        }
    }
}


