package com.webingate.paysmartcustomerapp.activity.customerapp;

import android.app.DownloadManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

import com.webingate.paysmartcustomerapp.R;

import java.io.File;
import java.io.IOException;

public class DownloadPdf  extends AppCompatActivity {
    WebView webview;
    ProgressBar progressbar;
    Button download;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.webview_pdf);
        //webview = (WebView)findViewById(R.id.webview);
        //webview = (WebView)findViewById(R.id.webview);
        progressbar = (ProgressBar) findViewById(R.id.progressbar);
        //webview.getSettings().setJavaScriptEnabled(true);
        String filename ="http://www3.nd.edu/~cpoellab/teaching/cse40816/android_tutorial.pdf";
        //webview.loadUrl("http://docs.google.com/gview?embedded=true&url=" + filename);

//        webview.setWebViewClient(new WebViewClient() {
//
//            public void onPageFinished(WebView view, String url) {
//                // do your stuff here
//                progressbar.setVisibility(View.GONE);
//            }
//        });

        download=findViewById(R.id.button1);
        download.setOnClickListener(view -> {
            download(view);
        });
    }
    public void download(View v)
    {
       // new DownloadFile().execute("http://maven.apache.org/maven-1.x/maven.pdf", "maven.pdf");
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse("http://124.123.41.203:8088/UI/EmailTemplates/BoardingPass.pdf"));
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "BoardingPass.pdf");
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED); // to notify when download is complete
        request.allowScanningByMediaScanner();// if you want to be available from media players
        DownloadManager manager = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
        manager.enqueue(request);
    }
//    private class DownloadFile extends AsyncTask<String, Void, Void> {
//
//        @Override
//        protected Void doInBackground(String... strings) {
//            String fileUrl = strings[0];   // -> http://maven.apache.org/maven-1.x/maven.pdf
//            String fileName = strings[1];  // -> maven.pdf
//
//
//            String extStorageDirectory = Environment.getExternalStorageDirectory().toString();
//            File folder = new File(Environment.DIRECTORY_DOWNLOADS, "testthreepdf");
//            folder.mkdir();
//
//            File pdfFile = new File(folder, fileName);
//
//            try{
//                pdfFile.createNewFile();
//            }catch (IOException e){
//                e.printStackTrace();
//            }
//            FileDownloader.downloadFile(fileUrl, pdfFile);
//            return null;
//        }
//    }

}
