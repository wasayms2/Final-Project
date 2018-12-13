package com.example.wasay.finalproject;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.health.SystemHealthManager;
import android.view.Menu;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ImageView;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.*;
import java.io.*;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;

public class MainActivity extends Activity {
    TextView txt;
    TextView urlTxt;
    Button btn;
    ImageView img;
    RequestQueue requestQueue;

    String baseURL = "https://api.imgflip.com/get_memes";
    String[] urlArray = new String[100];
    String[] nameArray = new String[100];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txt = findViewById(R.id.myTxt);
        btn = findViewById(R.id.myButton);
        img = findViewById(R.id.myImage);

        requestQueue = Volley.newRequestQueue(this);
        getMemeList();

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int index = (int) (Math.random() * urlArray.length);
                txt.setText(nameArray[index]);
                Picasso.with(getBaseContext()).load(urlArray[index]).resize(550,550).centerCrop().into(img);




            }
        });

    }

    private void getMemeList() {
        JsonObjectRequest jsonObjectRequst = new JsonObjectRequest(Request.Method.GET,
                baseURL,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONObject data = response.getJSONObject("data");
                            JSONArray memes = data.getJSONArray("memes");
                            for (int i = 0; i < memes.length(); i++) {
                                JSONObject currentMeme = memes.getJSONObject(i);
                                nameArray[i] = currentMeme.getString("name");
                                urlArray[i] = currentMeme.getString("url");
                            }
                            System.out.println(urlArray[0]);
                        } catch (Exception e) {
                            System.out.println(e.toString());
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println(error.toString());
            }
        });
        requestQueue.add(jsonObjectRequst);
    }

}

