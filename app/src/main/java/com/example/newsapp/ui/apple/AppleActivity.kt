package com.example.newsapp.ui.apple

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.widget.doOnTextChanged
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.core.data.Resource
import com.example.core.utils.ErrorMessageSplit
import com.example.newsapp.R
import com.example.newsapp.databinding.ActivityAppleBinding
import com.example.newsapp.databinding.ActivityMainBinding
import com.example.newsapp.helper.CurrentDate
import com.example.newsapp.ui.apple.DetailApple.DetailAppleActivity
import com.example.newsapp.util.dialog.ErrorBottomSheet
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import timber.log.Timber

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class AppleActivity : AppCompatActivity() {

    private val tag = AppleActivity::class.java.simpleName.toString()

    private lateinit var binding: ActivityAppleBinding
    private lateinit var adapter: AppleAdapter
    private val appleViewModel: AppleViewModel by viewModels()

    private var today : String = ""
    private var type : String = ""
    private var apiKey : String = ""
    private var id : String = ""
    private var name : String = ""

    // paging
    private var page = 1
    private var totalPage = 1
    private var isLoading = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAppleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        today = CurrentDate.getToday()
        type = intent.getStringExtra("type").toString()
        apiKey = intent.getStringExtra("api_key").toString()

        // setup toolbar
        toolbarSetup()

        // search
        binding.layoutToolbar.edtSearch.doOnTextChanged { text, start, before, count ->
            appleViewModel.searchQuery.value = text.toString()
        }

        // method
        setupObserver(type,apiKey)
        buildList()


    }

    private fun toolbarSetup() {

        binding.layoutToolbar.apply {
            ivNavigationBack.setOnClickListener { onBackPressed() }

            Glide.with(this@AppleActivity)
                .load(R.drawable.background_news)
                .into(imageView)

            tvTittle1.text = getString(R.string.title_apple_news)
            tvTittle2.text = today
        }
    }

    private fun setupObserver(typeClicked: String, apiKey: String) {

        appleViewModel.getApple(typeClicked,apiKey).observe(this, { data ->

            if (data != null) {
                when (data) {
                    is Resource.Loading -> binding.progressBarApple.visibility = View.VISIBLE
                    is Resource.Success -> {
                        binding.progressBarApple.visibility = View.GONE
                        adapter.setData(data.data)
                        Timber.tag(tag).d("observer_apple_adapter ${data.data}")
                    }
                    is Resource.Error -> {
                        binding.progressBarApple.visibility = View.GONE
                        val message = ErrorMessageSplit.message(data.message.toString())
                        val code = ErrorMessageSplit.code(data.message.toString())
//                        SimpleDialog.newInstance(code, message)
//                            .show(supportFragmentManager, SimpleDialog.TAG)
                        ErrorBottomSheet.instance(code, message)
                            .show(supportFragmentManager, ErrorBottomSheet.TAG)
                    }
                }

            }
        })

        appleViewModel.search.observe(this, { data ->
            adapter.setData(data)

        })
    }

    private fun buildList() {

        adapter = AppleAdapter()
        binding.rvListApple.setHasFixedSize(true)
        binding.rvListApple.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.rvListApple.adapter = adapter

        binding.rvListApple.addItemDecoration(
            DividerItemDecoration(
                this,
                LinearLayoutManager.VERTICAL
            )
        )

        adapter.onItemClick = { selectData ->
            Toast.makeText(this, "id : ${selectData.id} \n name : ${selectData.name}", Toast.LENGTH_SHORT).show()

            val mIntent = Intent(this, DetailAppleActivity::class.java)
            mIntent.putExtra(DetailAppleActivity.EXTRA_DATA, selectData)
            startActivity(mIntent)
        }
    }


}