package com.example.imagegallery.activity

import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.imagegallery.R
import com.example.imagegallery.SearchViewModel
import com.example.imagegallery.util.SharedPreferenceHelper.customPreference
import com.example.imagegallery.util.SharedPreferenceHelper.recentSearch
import com.example.imagegallery.adapter.ImageListAdapter
import com.example.imagegallery.adapter.RecentSearchAdapter
import com.example.imagegallery.databinding.ActivityMainBinding
import com.example.imagegallery.remote.SearchViewModelFactory
import com.example.imagegallery.util.Constants
import com.example.imagegallery.util.Utils
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type


class MainActivity : AppCompatActivity(), View.OnClickListener,
    RecentSearchAdapter.OnRecentSearchListener {

    private lateinit var binding: ActivityMainBinding
    private val adapter = ImageListAdapter(this)
    private val recentSearchAdapter = RecentSearchAdapter(this, this)

    private var recentItems: ArrayList<String> = arrayListOf()
    private var prefs: SharedPreferences? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        setUpData()
    }

    private fun setUpData(){
        prefs = customPreference(this, Constants.customPrefName)
        binding.btnSearch.setOnClickListener(this)

        binding.rvGridImages.adapter = recentSearchAdapter
        if(prefs?.recentSearch != null){
            binding.tvRecentSearches.text = this.resources.getString(R.string.recent_searches)
            binding.rvGridImages.layoutManager = LinearLayoutManager(this@MainActivity)
            val gson = Gson()
            val type: Type = object : TypeToken<List<String?>?>() {}.type
            val arrayList: List<String> = gson.fromJson(prefs?.recentSearch, type)
            recentSearchAdapter.setSearchList(arrayList)
        }else{
            binding.tvRecentSearches.text = this.resources.getString(R.string.no_recent_items)
        }
    }

    private fun searchImages(){

        if(binding.etSearch.text.isNotEmpty()){
            val searchViewModel = ViewModelProviders.of(this,
                SearchViewModelFactory(this@MainActivity,
                    binding.etSearch.text.toString())
            ).get(SearchViewModel::class.java)
            binding.rvGridImages.layoutManager = GridLayoutManager(this@MainActivity, 2)
            binding.rvGridImages.adapter = adapter

            searchViewModel.init(binding.etSearch.text.toString())
            searchViewModel.getData().observe(this) { response ->
                response?.let {
                    binding.tvRecentSearches.visibility = View.GONE
                    adapter.setDataList(response.items)
                    adapter.notifyDataSetChanged()
                    Log.i("response", response.toString())
                }
            }
        }
    }

    override fun onClick(v: View?) {
        if(v?.id == R.id.btn_search){
            Utils.hideKeyboard(binding.btnSearch, this)
            searchImages()
        }

        val gson = Gson()
        val type: Type = object : TypeToken<List<String?>?>() {}.type
        prefs?.recentSearch?.let {
            val arrayList: List<String> = gson.fromJson(it, type)
            recentItems = arrayList as ArrayList<String>

        }

        //delete the oldest item - max limit is 5
        if (recentItems.size == 5) {
            recentItems.remove(recentItems.elementAt(4))
        }

        //do not add the duplicate item into the list
        if(!recentItems.contains(binding.etSearch.text.toString())) {
            recentItems?.add(0,binding.etSearch.text.toString())
        }

        val json = gson.toJson(recentItems)
        prefs?.recentSearch = json

    }

    override fun onRecentItemSelected(recentItem: String) {
        Log.i("recent item", recentItem)
        binding.etSearch.setText(recentItem)
    }

}