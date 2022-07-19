package com.greymatter.sprint;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;

public class JsoupTestActivity extends AppCompatActivity{
    String url = "https://bscscan.com/address/0xbc88b220482b8e8626d2652919cd6f53e099692e";
    Document doc = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jsoup_test);
        new DataGrabber().execute();

    }
    private class DataGrabber extends AsyncTask<Void, Void, Void>{

        @Override
        protected Void doInBackground(Void... params) {
            // NO CHANGES TO UI TO BE DONE HERE
            try {
                doc = Jsoup.connect(url).get();

            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            //This is where we update the UI with the acquired data
            if (doc != null){
                Elements text = doc.select("div[class=col-md-8]");
                String txtstr = text.text();
                String[] splitStr = txtstr.split("\\s+");
                Log.d("JSOUP_TEXT",splitStr[0]);
            }else{
                Log.d("JSOUP_TEXT","FAILED");
            }
        }
    }

}