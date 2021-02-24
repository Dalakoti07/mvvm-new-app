package com.androiddevs.mvvmnewsapp.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Adapter
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.androiddevs.mvvmnewsapp.R
import com.androiddevs.mvvmnewsapp.adapters.NewsAdapter
import com.androiddevs.mvvmnewsapp.api.responses.Resource
import com.androiddevs.mvvmnewsapp.ui.NewsActivity
import com.androiddevs.mvvmnewsapp.ui.NewsViewModel
import kotlinx.android.synthetic.main.fragment_search_news.*
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SearchNewsFragment :Fragment(R.layout.fragment_search_news) {

    lateinit var viewmodel: NewsViewModel
    private val TAG = "SearchNewsFragment"
    lateinit var newsAdapter: NewsAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewmodel=(activity as NewsActivity).viewModel
        setUpRV()
        var job: Job?=null
        etSearch.addTextChangedListener{editable->
            job?.cancel()
            job= MainScope().launch {
                delay(500L)
                editable?.let {
                    if(editable.toString().isNotEmpty())
                        viewmodel.searchNews(editable.toString())
                }
            }
        }
        newsAdapter.setOnItemClickListener {
            val bundle=Bundle().apply {
                putSerializable("article",it)
            }
            findNavController().navigate(R.id.action_searchNewsFragment_to_articleFragment,bundle)
        }

        viewmodel.searchNews.observe(viewLifecycleOwner, Observer { response->
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
        rvSearchNews.apply {
            adapter=newsAdapter
            layoutManager= LinearLayoutManager(activity)
        }
    }
}