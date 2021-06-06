package com.example.core.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.Interceptor
import okhttp3.Response
import timber.log.Timber
import javax.inject.Inject

class NetworkConnectionInterceptor @Inject constructor(@ApplicationContext context: Context) :
    Interceptor {

    private val applicationContext = context
//    private val session = (applicationContext)

//    private lateinit var mPreference : UserPreference
//    private lateinit var mPreferenceEntity : PreferenceEntity

//    private lateinit var mPreference
//    private lateinit var mPreferenceEntity = mPreference.getPref()

    override fun intercept(chain: Interceptor.Chain): Response {

//        mPreference = UserPreference(applicationContext)
//        mPreferenceEntity = mPreference.getPref()
//        val token = mPreferenceEntity.token

        if (!isInternetAvailable())
            throw ApiException("Make sure you have an active data connection")

        val request = chain.request().newBuilder()
//            .addHeader("Authorization", "Bearer $token")
            .build()

        val response = chain.proceed(request)

        when (response.code) {
            400 -> {
                //Show Bad Request Error Message
                val thowableMassage = "${response.message}:${response.code}"
                throw ApiException(thowableMassage)
//                throw ApiException(response.message)
            }
            401 -> {
                //Show UnauthorizedError Message
//                val thowableMassage = "${response.message}/${response.code}"
//                throw ApiException(response.message)
                val thowableMassage = "Your token is Expired:${response.code}"
                throw ApiException(thowableMassage)

            }

            403 -> {
                //Show Forbidden Message
//                val thowableMassage = "${response.message}/${response.code}"
//                throw ApiException(response.message)
                val thowableMassage = "${response.message}:${response.code}"
                throw ApiException(thowableMassage)
            }
            404 -> {
                //Show NotFound Message
//                throw ApiException(response.message)
                val thowableMassage = "${response.message}:${response.code}"
                throw ApiException(thowableMassage)

            }
            422 -> {
//                val thowableMassage = "${response.message}/${response.code}"
//                throw ApiException(response.message)
                val thowableMassage = "${response.message}:${response.code}"
                throw ApiException(thowableMassage)
            }
            500 -> {
                //Show Internal Server Error
//                val thowableMassage = "${response.message}/${response.code}"
//                throw ApiException(response.message)
                val thowableMassage = "${response.message}:${response.code}"
                throw ApiException(thowableMassage)
            }
            // ... and so on
        }

        println("request ${request.body}")
        println("response ${response.code}")

//        Timber.tag("networkInterception").d("token $token")
        return response
    }

    private fun isInternetAvailable(): Boolean {
        var result = false
        val connectivityManager =
            applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
        connectivityManager?.let {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                it.getNetworkCapabilities(connectivityManager.activeNetwork)?.apply {
                    result = when {
                        hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                        hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                        else -> false
                    }
                }
            } else {

                it.run {
                    it.activeNetworkInfo?.run {
                        result = when {
                            isConnected -> true
                            type == ConnectivityManager.TYPE_WIFI -> true
                            type == ConnectivityManager.TYPE_MOBILE -> true
                            else -> false
                        }
                    }
                }
            }
        }
        return result
    }
}