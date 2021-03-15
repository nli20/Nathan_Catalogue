package com.zjanvier.cataloguedefilms.Activities;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;
import com.zjanvier.cataloguedefilms.Model.Poke;
import com.zjanvier.cataloguedefilms.R;
import com.zjanvier.cataloguedefilms.Util.Constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class DetailsActivity extends AppCompatActivity {
    private Poke poke;

    private TextView pokeName;
    private ImageView pokeSprite;
    private String pokeNumber;
    private TextView pokeAbility;
    private TextView moves;
    private TextView pokeHeight;
    private TextView pokeWeight;
    private TextView pokeType;

    private RequestQueue queue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        queue = Volley.newRequestQueue(this);

        poke = (Poke) getIntent().getSerializableExtra("poke"); // récupérer tous les éléments
        pokeNumber = poke.getNumber();

        setUpUI();
        getPokeDetails(pokeNumber);

    }

    private void setUpUI() {

        pokeName=findViewById(R.id.pokeNameID);
        pokeType=findViewById(R.id.pokeTypeID);
        pokeSprite=findViewById(R.id.pokeImageID);
        pokeAbility=findViewById(R.id.pokeAbilityID);
        pokeHeight=findViewById(R.id.pokeHeightID);
        pokeWeight=findViewById(R.id.pokeWeightID);

    }

    private void getPokeDetails(String id) {


        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                Constants.URL + id + //Constants.API_KEY,
                null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try{
                    String source = null;
                    String value = null;
                    pokeName.setText(response.getString("name"));
                    pokeType.setText(response.getString(""));


                    /*if (response.has("Ratings")) { //n'existe pas pour certains cas
                        JSONArray ratings = response.getJSONArray("Ratings");

                        String source = null;
                        String value = null;
                        if (ratings.length() > 0) {

                            JSONObject mRatings = ratings.getJSONObject(ratings.length() - 1);
                            source = mRatings.getString("Source");
                            value = mRatings.getString("Value");
                            rating.setText(source + " : " + value);

                        }else {
                            rating.setText("Ratings: N/A");
                        }

                        movieTitle.setText(response.getString("Title"));
                        movieYear.setText("Released: " + response.getString("Released"));
                        director.setText("Director: " + response.getString("Director"));
                        writers.setText("Writers: " + response.getString("Writer"));
                        plot.setText("Plot: " + response.getString("Plot"));
                        runtime.setText("Runtime: " + response.getString("Runtime"));
                        actors.setText("Actors: " + response.getString("Actors"));

                        Picasso.get()
                                .load(response.getString("Poster"))
                                .fit()
                                .into(movieImage);
                        boxOffice.setText("Box Office: " + response.getString("BoxOffice"));
                    }*/

                }catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("Error:", error.getMessage());

            }
        });
        queue.add(jsonObjectRequest);

    }

}
