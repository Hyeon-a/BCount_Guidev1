package com.example.sohee.guide_v1;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.String;
import java.util.ArrayList;
import java.util.List;

public class CheckActivity extends Activity {

    HttpPost httpPost;
    HttpResponse response;
    HttpResponse response2;
    HttpResponse response3;
    HttpResponse response4;
    HttpClient httpClient;
    List<NameValuePair> nameValuePairs;

    static String total;
    static String in_place;

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check);

        final TextView textView = (TextView) findViewById(R.id.total);
        final TextView textView2 = (TextView) findViewById(R.id.in_place);
        final TextView textView3 = (TextView) findViewById(R.id.textView3);
        final TextView textView10 = (TextView) findViewById(R.id.textView10);




        try {
            httpClient = new DefaultHttpClient();
            httpPost = new HttpPost("http://192.168.0.91/totalnum.php");
            nameValuePairs = new ArrayList<NameValuePair>(1);
            nameValuePairs.add(new BasicNameValuePair("phonenum", LoginActivity.phonenum));
            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            response = httpClient.execute(httpPost);
            ResponseHandler<String> responseHandler = new BasicResponseHandler();
            final String response = httpClient.execute(httpPost, responseHandler);
            total = response;
            textView.setText(total);

            httpClient = new DefaultHttpClient();
            httpPost = new HttpPost("http://192.168.0.91/innum.php");
            nameValuePairs = new ArrayList<NameValuePair>(1);
            nameValuePairs.add(new BasicNameValuePair("phonenum", LoginActivity.phonenum));
            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            response2 = httpClient.execute(httpPost);
            ResponseHandler<String> responseHandler2 = new BasicResponseHandler();
            final String response2 = httpClient.execute(httpPost, responseHandler2);
            in_place = response2;
            textView2.setText(in_place);

            httpClient = new DefaultHttpClient();
            httpPost = new HttpPost("http://192.168.0.91/check.php");
            nameValuePairs = new ArrayList<NameValuePair>(1);
            nameValuePairs.add(new BasicNameValuePair("phonenum", LoginActivity.phonenum));
            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            response3 = httpClient.execute(httpPost);
            ResponseHandler<String> responseHandler3 = new BasicResponseHandler();
            final String response3 = httpClient.execute(httpPost, responseHandler3);
            JSONArray jsonarray = new JSONArray(response3);
            for (int i = 0; i < jsonarray.length(); i++) {
                JSONObject jsonobject = jsonarray.getJSONObject(i);
                String name = jsonobject.getString("name");
                textView3.append(name + "\n");
            }

            httpClient = new DefaultHttpClient();
            httpPost = new HttpPost("http://192.168.0.91/unchecked.php");
            nameValuePairs = new ArrayList<NameValuePair>(1);
            nameValuePairs.add(new BasicNameValuePair("phonenum", LoginActivity.phonenum));
            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            response4 = httpClient.execute(httpPost);
            ResponseHandler<String> responseHandler4 = new BasicResponseHandler();
            final String response4 = httpClient.execute(httpPost, responseHandler4);
            JSONArray jsonarray2 = new JSONArray(response4);
            for (int i = 0; i < jsonarray2.length(); i++) {
                JSONObject jsonobject2 = jsonarray2.getJSONObject(i);
                String name = jsonobject2.getString("name");
                textView10.append(name + "\n");
            }

        } catch (Exception e) {
            System.out.println("Exception : " + e.getMessage());
        }
    }
}
