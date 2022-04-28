package com.example.okhttp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    TextView responseText;
    EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText = (EditText) findViewById(R.id.city);
        Button button = (Button) findViewById(R.id.send_request);
        responseText = (TextView) findViewById(R.id.response_text);
        button.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.send_request) {
            String url = getUrl(editText.getText().toString());
            sendRequestWithOkHttp(url);
        }
    }

    private void sendRequestWithOkHttp(final String path) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder()
                            .url(path)
                            .build();
                    Response response = client.newCall(request).execute();
                    String responseData = response.body().string();
                    showResponnse(responseData);
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        }).start();
    }

    private void showResponnse(final String responnse) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                responseText.setText(responnse);
            }
        });
    }

    private String getUrl(String city) {
        return "http://wthrcdn.etouch.cn/weather_mini?city=" + city;
    }
}
