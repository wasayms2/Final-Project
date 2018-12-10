package com.example.wasay.finalproject;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ImageView;
import java.net.URI;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.Response;
import com.android.volley.Request;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;

public class MainActivity extends Activity {
    TextView txt;
    Button btn;
    ImageView img;
    RequestQueue requestQueue;

    String baseURL = "https://api.imgflip.com/get_memes";
    String url;
    String[] urlArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txt = findViewById(R.id.myTxt);
        btn = findViewById(R.id.myButton);
        img = findViewById(R.id.myImage);

        requestQueue = Volley.newRequestQueue(this);


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn.setText("New Meme");
                txt.setText(" ");
                img.setImageResource(R.drawable.meme2);
                //img.setImageURI(new URI("http://imgflip.com/i/1bij"));
            }
        });

    }

    private void getMemeList() {

        JsonArrayRequest arrReq = new JsonArrayRequest( baseURL,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        if (response.length() > 0) {
                            for (int i = 0; i < response.length(); i++) {
                                try {
                                    // For each repo, add a new line to our repo list.
                                    JSONObject jsonObj = response.getJSONObject(i);
                                    String memeName = jsonObj.get("name").toString();
                                    String memeURL = jsonObj.get("url").toString();
                                    urlArray[i] = memeURL;
                                    //addToRepoList(repoName, lastUpdated);
                                } catch (JSONException e) {
                                    Log.e("Volley", "Invalid JSON Object.");
                                }

                            }
                        }
                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(final VolleyError error) {
                        Log.e("Volley", "Did not grab the Meme's");
                    }

                });
        requestQueue.add(arrReq);
    }

}

