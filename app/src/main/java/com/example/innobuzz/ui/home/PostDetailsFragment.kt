package com.example.innobuzz.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.innobuzz.R
import com.example.innobuzz.databinding.FragmentPostDetailsBinding
import com.example.innobuzz.db.entity.Posts
import com.example.innobuzz.utils.FunctionUtils
import com.example.innobuzz.utils.FunctionUtils.focusScreen

class PostDetailsFragment : Fragment() {

    private var _binding: FragmentPostDetailsBinding? = null
    private val binding get() = _binding!!
    private lateinit var postDetails: Posts
    private val args by navArgs<PostDetailsFragmentArgs>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentPostDetailsBinding.inflate(layoutInflater)
        focusScreen(binding.root)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        postDetails = args.post
        initUi()
    }

    private fun initUi() {
        with(binding) {
            postTitleTxt.text = postDetails.title
            postBodyTxt.text = postDetails.body
            toolbar.titleTv.text = getString(R.string.description)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}