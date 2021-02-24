package com.androiddevs.mvvmnewsapp.ui.fragment

import android.opengl.Visibility
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.androiddevs.mvvmnewsapp.R
import com.androiddevs.mvvmnewsapp.adapters.NewsAdapter
import com.androiddevs.mvvmnewsapp.api.responses.Resource
import com.androiddevs.mvvmnewsapp.ui.NewsActivity
import com.androiddevs.mvvmnewsapp.ui.NewsViewModel
import kotlinx.android.synthetic.main.fragment_breaking_news.*
import retrofit2.Response

class BreakingNewsFragment :Fragment(R.layout.fragment_breaking_news) {

    private val TAG = "BreakingNewsFragment"
    lateinit var viewmodel:NewsViewModel
    lateinit var newsAdapter:NewsAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewmodel=(activity as NewsActivity).viewModel
        setUpRV()

        newsAdapter.setOnItemClickListener {
            val bundle=Bundle().apply {
                putSerializable("article",it)
            }
            findNavController().navigate(R.id.action_breakingNewsFragment_to_articleFragment,bundle)
        }

        viewmodel.breakingNews.observe(viewLifecycleOwner, Observer { response->
            when(response){
                is Resource.Success ->{
                    paginationProgressBar.visibility=View.INVISIBLE
                    response.data?.let {
                        Log.d(TAG,"data fetched success")
                        newsAdapter.differ.submitList(it.articles)
                    }
                }
                is Resource.Error->{
                    Log.d(TAG,"data fetched error")
                    paginationProgressBar.visibility=View.INVISIBLE
                    response.message?.let {
                        Log.e(TAG," an error occurred ${it}")
                    }
                }
                is Resource.Loading->{
                    Log.d(TAG,"data fetched loading")
                    paginationProgressBar.visibility=View.VISIBLE
                }
            }
        })
    }

    private fun setUpRV() {
        newsAdapter= NewsAdapter()
        rvBreakingNews.apply {
            adapter=newsAdapter
            layoutManager=LinearLayoutManager(activity)
        }
    }
}