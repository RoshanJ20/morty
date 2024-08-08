package com.example.morty.characters

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.morty.BaseFragment
import com.example.morty.R
import com.example.morty.databinding.FragmentCharacterSearchBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class CharacterSearchFragment: BaseFragment(R.layout.fragment_character_search) {

    private var _binding: FragmentCharacterSearchBinding? = null
    private val binding get() = _binding!!

    private val viewModel: CharacterSearchViewModel by viewModels()

    private var currentText = ""
    private val handler = Handler(Looper.getMainLooper())
    private val searchRunnable = Runnable {
        viewModel.submitQuery(currentText)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentCharacterSearchBinding.bind(view)

        val epoxyController = CharacterSearchEpoxyController { characterId ->
            // Handle character selection
        }

        binding.epoxyRecyclerView.setControllerAndBuildModels(epoxyController)

        binding.searchEditText.doAfterTextChanged {
            currentText = it?.toString() ?: ""

            handler.removeCallbacks(searchRunnable)
            handler.postDelayed(searchRunnable, 500L)
        }
        lifecycleScope.launch {
            viewModel.flow.collectLatest {
                epoxyController.localException = null
                epoxyController.submitData(it)
            }
        }

        viewModel.localExceptionEventLiveData.observe(viewLifecycleOwner){ event ->
            // Handle local exceptions
            event.getContent()?.let{ localException ->
                epoxyController.localException = localException
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null


    }

}