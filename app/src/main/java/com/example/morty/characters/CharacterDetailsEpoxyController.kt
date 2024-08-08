package com.example.morty.characters

import com.airbnb.epoxy.CarouselModel_
import com.airbnb.epoxy.EpoxyController
import com.airbnb.epoxy.kotlinsample.helpers.ViewBindingKotlinModel
import com.example.morty.R
import com.example.morty.databinding.ModelCharacterDetailsDataPointBinding
import com.example.morty.databinding.ModelCharacterDetailsHeaderBinding
import com.example.morty.databinding.ModelCharacterDetailsImageBinding
import com.example.morty.databinding.ModelEpisodeCarouselItemBinding
import com.example.morty.databinding.ModelTitleBinding
import com.example.morty.epoxy.LoadingEpoxyModel
import com.example.morty.domain.models.Character
import com.example.morty.domain.models.Episode
import com.squareup.picasso.Picasso

class CharacterDetailsEpoxyController(
    private val onEpisodeClicked: (Int) -> Unit
): EpoxyController() {
    var isLoading: Boolean = true
        set(value) {
            field = value
            if(field){
                requestModelBuild()
            }
        }

    var character: Character? = null
        set(value) {
            field = value
            if(field != null){
                isLoading = false
                requestModelBuild()
            }
        }

    override fun buildModels(){
        if(isLoading){
            LoadingEpoxyModel().id("loading").addTo(this)
            return
        }

        if(character == null){
            return
        }

        HeaderEpoxyModel(
            name =character!!.name,
            gender =character!!.gender,
            status =character!!.status
        ).id("header").addTo(this)

        ImageEpoxyModel(
            imageUrl =character!!.image
        ).id("image").addTo(this)

        if(character!!.episodeList.isNotEmpty())
        {
            TitleEpoxyModel("Episodes").id("title").addTo(this)
            val items = character!!.episodeList.map{
                EpisodeCarouselItemEpoxyModel(it, onEpisodeClicked).id(it.id)
            }
            CarouselModel_()
                .id("episode_carousel")
                .models(items)
                .numViewsToShowOnScreen(1.2f)
                .addTo(this)
        }

        DataPointEpoxyModel(
            title = "Origin",
            description = character!!.origin.name
        ).id("data-point").addTo(this)

        DataPointEpoxyModel(
            title = "Species",
            description = character!!.species
        ).id("data-point").addTo(this)
    }


    data class HeaderEpoxyModel(
        val name: String,
        val gender: String,
        val status: String
    ) : ViewBindingKotlinModel<ModelCharacterDetailsHeaderBinding>(R.layout.model_character_details_header) {

        override fun ModelCharacterDetailsHeaderBinding.bind() {
            nameTextView.text = name
            aliveTextView.text = status

            if(gender.equals("male", true)){
                genderImageView.setImageResource(R.drawable.ic_male_24)
            }
        }
    }

    data class ImageEpoxyModel(
        val imageUrl: String
    ) : ViewBindingKotlinModel<ModelCharacterDetailsImageBinding>(R.layout.model_character_details_image) {

        override fun ModelCharacterDetailsImageBinding.bind() {
            Picasso.get().load(imageUrl).into(headerImageView)
        }
    }

    data class DataPointEpoxyModel(
        val title: String,
        val description: String
    ) : ViewBindingKotlinModel<ModelCharacterDetailsDataPointBinding>(R.layout.model_character_details_data_point) {

        override fun ModelCharacterDetailsDataPointBinding.bind() {
            labelTextView.text = title
            textView.text = description
        }
    }

    data class EpisodeCarouselItemEpoxyModel(
        val episode: Episode,
        val onEpisodeClicked: (Int) -> Unit
    ): ViewBindingKotlinModel<ModelEpisodeCarouselItemBinding>(R.layout.model_episode_carousel_item) {

        override fun ModelEpisodeCarouselItemBinding.bind() {
            episodeTextView.text = episode.getFormattedSeasonTruncated()
            episodeDetailsTextView.text = "${episode.name}\n${episode.airDate}"
            root.setOnClickListener { onEpisodeClicked(episode.id) }
        }
    }

    data class TitleEpoxyModel(
        val title: String
    ): ViewBindingKotlinModel<ModelTitleBinding>(R.layout.model_title) {

        override fun ModelTitleBinding.bind() {
            titleTextView.text = title
        }
    }


}