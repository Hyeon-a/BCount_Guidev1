package com.example.sohee.guide_v1;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends  AppCompatActivity {
    static String phonenum;

    Button loginbt;
    ProgressDialog dialog = null;
    EditText inputID, inputPW;
    HttpPost httpPost;
    HttpResponse response;
    HttpClient httpClient;
    List<NameValuePair> nameValuePairs;

    protected  void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        inputID = (EditText)findViewById(R.id.et_1);
        inputPW = (EditText)findViewById(R.id.et_2);
        loginbt = (Button)findViewById(R.id.lgnbutton2);

        loginbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog = ProgressDialog.show(LoginActivity.this, "", "Validating user...", true);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Looper.prepare();
                        login();
                        Looper.loop();
                    }
                }).start();
            }
        });
    }
    void login() {
        try {
            httpClient = new DefaultHttpClient();
            httpPost = new HttpPost("http://192.168.0.91/login_g.php");
            nameValuePairs = new ArrayList<NameValuePair>(2);
            nameValuePairs.add(new BasicNameValuePair("id", inputID.getText().toString()));
            nameValuePairs.add(new BasicNameValuePair("password", inputPW.getText().toString()));
            phonenum = inputPW.getText().toString();//
            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            response = httpClient.execute(httpPost);
            ResponseHandler<String> responseHandler = new BasicResponseHandler();
            final String response = httpClient.execute(httpPost, responseHandler);
            System.out.println("Respone:" + response);
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    dialog.dismiss();
                }
            });
            Log.v("response 값: ", response);
            if (response.equalsIgnoreCase("User Found")) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(LoginActivity.this, "Login Success", Toast.LENGTH_SHORT).show();
                    }
                });

                startActivity(new Intent(LoginActivity.this, MenuActivity.class));
                finish();
            } else {
                Toast.makeText(LoginActivity.this, "Login Fail", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            dialog.dismiss();
            System.out.println("Exception : " + e.getMessage());
        }
    }
}
