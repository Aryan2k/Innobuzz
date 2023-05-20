package com.example.innobuzz.ui.home

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.example.innobuzz.R
import com.example.innobuzz.adapters.PostClickListener
import com.example.innobuzz.adapters.PostsAdapter
import com.example.innobuzz.databinding.FragmentPostsBinding
import com.example.innobuzz.db.entity.Posts
import com.example.innobuzz.utils.FunctionUtils.focusScreen
import com.example.innobuzz.utils.FunctionUtils.setUpDialog
import com.example.innobuzz.utils.RequestStatus
import com.example.innobuzz.viewmodel.PostsFragmentViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PostsFragment : Fragment(), PostClickListener {

    private var _binding: FragmentPostsBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<PostsFragmentViewModel>()
    private lateinit var dialog: Dialog
    private lateinit var adapter: PostsAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentPostsBinding.inflate(layoutInflater)
        focusScreen(binding.root)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
        dialog = setUpDialog(getString(R.string.fetching_posts), requireContext())
        binding.serviceBtn.setOnClickListener { startActivity(Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS)) }
        handlePostListFromServer()
        handleGetPostsFromDbLiveData()
        if (viewModel.postsList.isEmpty())
            viewModel.getPostsFromDb()
    }

    private fun initUI() {
        adapter = PostsAdapter(viewModel.postsList, this)
        binding.postRv.adapter = adapter
        binding.toolbar.titleTv.text = getString(R.string.posts)
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun handleGetPostsFromDbLiveData() {
        viewModel.getPostsFromDbLiveData.observe(viewLifecycleOwner) {
            when (it.status) {
                RequestStatus.LOADING -> {
                    dialog.show()
                }
                RequestStatus.SUCCESS -> {
                    binding.serviceBtn.visibility = View.VISIBLE
                    if (viewModel.postsList.isEmpty()) {
                        viewModel.getPostsFromApi()
                    } else {
                        adapter.notifyDataSetChanged()
                        dialog.dismiss()
                    }
                }
                RequestStatus.EXCEPTION -> {
                    dialog.dismiss()
                    Toast.makeText(this.activity, it.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun handlePostListFromServer() {
        viewModel.getPostsFromApiLiveData.observe(viewLifecycleOwner) {
            when (it.status) {
                RequestStatus.LOADING -> {
                }
                RequestStatus.SUCCESS -> {
                    dialog.dismiss()
                    adapter.notifyDataSetChanged()
                }
                RequestStatus.EXCEPTION -> {
                    dialog.dismiss()
                    Toast.makeText(this.activity, it.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onPostClicked(posts: Posts) {
        val action = PostsFragmentDirections.actionPostsFragmentToPostDetailsFragment(posts)
        Navigation.findNavController(requireView()).navigate(action)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
