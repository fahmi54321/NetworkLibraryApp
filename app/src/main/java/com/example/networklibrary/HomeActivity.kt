package com.example.networklibrary

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.create

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val progressBar = findViewById<ProgressBar>(R.id.pb_home)

        val datasource = NetworkProvider.provideHttpAdapter().create(HomeDatasource::class.java)

        datasource.discoverMovie().enqueue(object : Callback<HomeResponse> {

            override fun onResponse(call: Call<HomeResponse>, response: Response<HomeResponse>) {
                progressBar.visibility = View.GONE

                val results = response.body()?.result
                val itemAdapter = findViewById<RecyclerView>(R.id.rv_home)
                itemAdapter.addItemDecoration(DividerItemDecoration(this@HomeActivity,DividerItemDecoration.VERTICAL))

                itemAdapter.adapter = HomeAdapter(results?: emptyList())
            }

            override fun onFailure(call: Call<HomeResponse>, t: Throwable) {
                Log.e(HomeActivity::class.java.simpleName,"${t.printStackTrace()}")
            }
            
        })
    }
}
