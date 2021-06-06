package com.example.newsapp.ui.apple.DetailApple

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.app.DownloadManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.webkit.*
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import com.example.newsapp.R

class Webview : AppCompatActivity() {


    lateinit var webView: WebView
    lateinit var context: Context
    lateinit var activity: Activity
    lateinit var downloadListener: DownloadListener
    var writeAccess = false

    /** Permission Request Code */
    private val PERMISSION_REQUEST_CODE = 1234


    /** Sample Page from which we will download the file */
    private val downloadPage = ""
    private var url : String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_webview)
        /** Application Context and Main Activity */
        context = applicationContext
        activity = this

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        setTitle("Presence Report")

        /** Initialize main layout and web view */
        webView = findViewById(R.id.webview)

        url = intent.getStringExtra("url")
        Log.d("test intent webview", " test intent webview url : $url")





        /** Check permission to write in external storage */
        checkWriteAccess()
        /** Create a Download Listener */
        createDownloadListener()
        /** Display Toast Message When Download Complete */
        onDownloadComplete()
        /** Configure Web View */
        configureWebView()

    }

    private fun onDownloadComplete()
    {
        /**  Code that receives and handles broadcast intents sent by Context.sendBroadcast(Intent) */
        val onComplete = object : BroadcastReceiver() {
            override fun onReceive(ctxt: Context, intent: Intent) {
                Toast.makeText(context,"File Downloaded", Toast.LENGTH_LONG).show()
            }
        }

        /** Register to receives above broadcast */
        registerReceiver(onComplete, IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun configureWebView()
    {

        /** Web View General Setup */

        /**
        When user clicks on an URL the default behaviour is android open the default application
        which handles URL. It means android will open a default browser. We need to handle this.
        Why? Because we need to open the URL in the same web view. In our MyWebViewClient we
        will override shouldOverrideUrlLoading function to again load the new url in our
        web view.
         */
        webView.webViewClient = MyWebViewClient()
        /** getSettings() : Gets the WebSettings object used to control the settings for this WebView. */
        /** We will use it to enable the Java Script Support. */
        webView.settings.javaScriptEnabled = true
        /** loadUrl : Loads the given URL. */
        webView.loadUrl(url!!)

        /** File Download Listener */
        webView.setDownloadListener(downloadListener)
    }


    /**
     * Custom WebViewClient to override URL Loading.
     */

    private inner class MyWebViewClient : WebViewClient() {

        /**
         * Override to open URL in WebView
         * */
        override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
            view.loadUrl(url)
            return true
        }

        /**
         * Override to open URL in WebView
         * */
        @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
        override fun shouldOverrideUrlLoading(view: WebView, request: WebResourceRequest): Boolean {
            view.loadUrl(request.url.toString())
            return true
        }
    }


    private fun createDownloadListener()
    {


        /** A New Download Listener for our WebView */
        downloadListener = DownloadListener { url, userAgent, contentDescription, mimetype, contentLength ->

            /**
             * This class contains all the information necessary to request a new download.
             * The URI is the only required parameter. Note that the default download destination
             * is a shared volume where the system might delete your file if it needs to reclaim
             * space for system use.
             * */
            val request = DownloadManager.Request(Uri.parse(url))

            /**
             * If the file to be downloaded is to be scanned by MediaScanner, this method should
             * be called before DownloadManager.enqueue(Request) is called.
             */
            request.allowScanningByMediaScanner()

            /**
             * Control whether a system notification is posted by the download manager while this
             * download is running or when it is completed.
             * */
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)

            /**
             * Guesses canonical filename that a download would have, using the
             * URL and contentDisposition.
             * */
            val fileName = URLUtil.guessFileName(url, contentDescription, mimetype)

            /**
             * Set the local destination for the downloaded file to a path within the public
             * external storage directory (as returned by Environment.getExternalStoragePublicDirectory(String)).
             * */
            request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, fileName)

            /**
             * Get Download Manager Service
             * */
            val dManager = getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager

            /**
             * Enqueue a new request to Download our File.
             * */

            if(writeAccess)
                dManager.enqueue(request)
            else
            {
                Toast.makeText(context,"Unable to download file. Required Privileges are not available.",
                    Toast.LENGTH_LONG).show()
                checkWriteAccess()
            }

        }
    }

    private fun checkWriteAccess()
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        {
            /**
             * Check for permission status.
             * */
            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
            {
                if (shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE))
                {
                    val builder = AlertDialog.Builder(activity)
                    builder.setMessage("Required permission to write external storage to save downloaded file.")
                    builder.setTitle("Please Grant Write Permission")
                    builder.setPositiveButton("OK") { _, _->
                        ActivityCompat.requestPermissions(
                            activity,
                            arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                            PERMISSION_REQUEST_CODE
                        )
                    }
                    builder.setNeutralButton("Cancel", null)
                    val dialog = builder.create()
                    dialog.show()
                } else {
                    ActivityCompat.requestPermissions(
                        activity,
                        arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                        PERMISSION_REQUEST_CODE
                    )
                }
            }
            else {
                /**
                 * Already have required permission.
                 * */
                writeAccess = false
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            PERMISSION_REQUEST_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    writeAccess=true
                } else {
                    // Permission denied
                    writeAccess=false
                    Toast.makeText(context,"Permission Denied. This app will not work with right permission.",
                        Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
//        onBackPressed()
        finish()
        return true
    }
}