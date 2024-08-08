package com.example.morty.episodes

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.paging.PagingData
import com.example.morty.BaseFragment
import com.example.morty.NavGraphActivity
import com.example.morty.NavGraphDirections
import com.example.morty.R
import com.example.morty.databinding.FragmentEpisodeListBinding
import com.example.morty.domain.models.Episode
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class EpisodeListFragment: BaseFragment(R.layout.fragment_episode_list) {

    private var _binding: FragmentEpisodeListBinding? = null
    private val binding : FragmentEpisodeListBinding by lazy { _binding!! }

    private val viewModel: EpisodesViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentEpisodeListBinding.bind(view)

        val epoxyController = EpisodeListEpoxyController { episodeClickedId ->
            val navDirections = NavGraphDirections.actionGlobalToEpisodeDetailBottomSheetFragment(episodeClickedId)
            findNavController().navigate(navDirections)
        }

        lifecycleScope.launch {
            viewModel.flow.collectLatest{ pagingData : PagingData<EpisodesUiModel> ->
                epoxyController.submitData(pagingData)
            }
        }

        binding.epoxyRecyclerView.setController(epoxyController)


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}