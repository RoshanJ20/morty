package com.example.morty.characters

import com.airbnb.epoxy.EpoxyModel
import com.airbnb.epoxy.kotlinsample.helpers.ViewBindingKotlinModel
import com.airbnb.epoxy.paging3.PagedListEpoxyController
import com.example.morty.R
import com.example.morty.databinding.ModelCharacterListItemBinding
import com.example.morty.databinding.ModelCharacterListTitleBinding
import com.example.morty.epoxy.LoadingEpoxyModel
import com.example.morty.network.response.GetCharacterByIdResponse
import com.squareup.picasso.Picasso
import java.util.Locale

class CharacterListPagingEpoxyController(
    private val onCharacterSelected : (Int) -> Unit
): PagedListEpoxyController<GetCharacterByIdResponse>() {

    override fun buildItemModel(currentPosition: Int, item: GetCharacterByIdResponse?): EpoxyModel<*> {
        return CharacterGridItemEpoxyModel(
            characterId =  item!!.id,
            imageUrl = item!!.image,
            name = item.name,
            onCharacterSelected = onCharacterSelected
        ).id(item.id)
    }

    override fun addModels(models: List<EpoxyModel<*>>) {
        if(models.isEmpty()){
            LoadingEpoxyModel().id("loading").addTo(this)
        return
        }

        CharacterGridTitleEpoxyModel("Main Family")
            .id("main_family_header")
            .addTo(this)

        super.addModels(models.subList(0,5))

        (models.subList(5, models.size) as List<CharacterGridItemEpoxyModel>).groupBy {
            it.name[0].toUpperCase() }
            .forEach { mapEntry ->
                val character = mapEntry.key.toString().toUpperCase(Locale.US)
                CharacterGridTitleEpoxyModel(title = character)
                    .id(character)
                    .addTo(this)
                super.addModels(mapEntry.value)
            }
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

    data class CharacterGridTitleEpoxyModel(
        val title: String
    ): ViewBindingKotlinModel<ModelCharacterListTitleBinding>(R.layout.model_character_list_title) {
        override fun ModelCharacterListTitleBinding.bind() {
            textView.text = title
        }

        override fun getSpanSize(totalSpanCount: Int, position: Int, itemCount: Int): Int {
            return totalSpanCount
        }
    }

}