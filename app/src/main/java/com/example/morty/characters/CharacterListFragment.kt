package com.example.morty.characters

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.airbnb.epoxy.EpoxyRecyclerView
import com.example.morty.BaseFragment
import com.example.morty.R
import com.google.firebase.crashlytics.FirebaseCrashlytics

class CharacterListFragment : BaseFragment(R.layout.fragment_character_list) {

    private val epoxyController = CharacterListPagingEpoxyController(::onCharacterSelected)

    private val viewModel: CharactersViewModel by lazy {
        ViewModelProvider(this).get(CharactersViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_character_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.charactersPagedListLiveData.observe(viewLifecycleOwner) { pagedList ->
            epoxyController.submitList(pagedList)
        }

        view.findViewById<EpoxyRecyclerView>(R.id.epoxyRecyclerView).setController(epoxyController)
    }

    private fun onCharacterSelected(characterId: Int) {
        FirebaseCrashlytics.getInstance().recordException(RuntimeException("Character selected with id $characterId"))
        val directions = CharacterListFragmentDirections.actionCharacterListFragmentToCharacterDetailFragment(characterId = characterId)
        view?.findNavController()?.navigate(directions)
    }

}