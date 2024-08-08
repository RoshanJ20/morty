package com.example.morty.characters

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.airbnb.epoxy.EpoxyRecyclerView
import com.example.morty.BaseFragment
import com.example.morty.NavGraphDirections
import com.example.morty.R

class CharacterDetailFragment : BaseFragment(R.layout.fragment_character_detail) {

    private val viewModel: CharacterViewModel by lazy {
        ViewModelProvider(this).get(CharacterViewModel::class.java)
    }
    private val epoxyController = CharacterDetailsEpoxyController{ episodeClickedId ->
        val navDirections = NavGraphDirections.actionGlobalToEpisodeDetailBottomSheetFragment(episodeClickedId)
        findNavController().navigate(navDirections)
    }

    private val safeArgs : CharacterDetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_character_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.characterByIdLiveData.observe(viewLifecycleOwner) { character ->
            epoxyController.character = character

            if(character == null){
                Toast.makeText(context, "Failed to load character", Toast.LENGTH_SHORT).show()
                return@observe
            }


        }

        viewModel.fetchCharacter(safeArgs.characterId)

        val epoxyRecyclerView = view.findViewById<EpoxyRecyclerView>(R.id.epoxyRecyclerView)
        epoxyRecyclerView.setControllerAndBuildModels(epoxyController)
    }

}