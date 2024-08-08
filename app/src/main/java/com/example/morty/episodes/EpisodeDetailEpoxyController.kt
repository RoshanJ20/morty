package com.example.morty.episodes

import com.airbnb.epoxy.EpoxyController
import com.airbnb.epoxy.kotlinsample.helpers.ViewBindingKotlinModel
import com.example.morty.R
import com.example.morty.databinding.ModelCharacterListItemSquareBinding
import com.example.morty.domain.models.Character
import com.squareup.picasso.Picasso

class EpisodeDetailEpoxyController(
    private val characterList: List<Character>
): EpoxyController() {
    override fun buildModels() {
        characterList.forEach { character ->
            CharacterEpoxyModel(
                imageUrl = character.image,
                name = character.name
            ).id(character.id).addTo(this)
        }
    }

    data class CharacterEpoxyModel(
        val imageUrl: String,
        val name: String
    ): ViewBindingKotlinModel<ModelCharacterListItemSquareBinding>(R.layout.model_character_list_item_square) {
        override fun ModelCharacterListItemSquareBinding.bind() {
            characterNameTextView.text = name
            Picasso.get().load(imageUrl).into(characterImageView)
        }
    }
}