package com.zjanvier.cataloguedefilms.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.zjanvier.cataloguedefilms.Data.PokeRecyclerViewAdapter;
import com.zjanvier.cataloguedefilms.Model.Poke;
import com.zjanvier.cataloguedefilms.Util.Constants;
import com.zjanvier.cataloguedefilms.Util.Prefs;
import com.zjanvier.cataloguedefilms.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private PokeRecyclerViewAdapter pokeRecyclerViewAdapter;
    private List<Poke> pokeList;
    private RequestQueue queue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        queue= Volley.newRequestQueue(this);

        recyclerView=findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        pokeList=new ArrayList<>();

        //Chercher les préferences pour le dernière recherche...

        Prefs prefs = new Prefs(MainActivity.this);
        String search = prefs.getSearch();

        pokeList = getPokes(search);
        pokeRecyclerViewAdapter = new PokeRecyclerViewAdapter(this, pokeList );
        recyclerView.setAdapter(pokeRecyclerViewAdapter);
        pokeRecyclerViewAdapter.notifyDataSetChanged();


    }

    //Tester
    Prefs prefs =new Prefs(MainActivity.this);
    String search=prefs.getSearch();
    getPokes(search);

    // Methode pour recupérer les différents films...
    public List<Poke> getPokes(String searchTerm) {
        pokeList.clear();

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                Constants.URL_LEFT + searchTerm,
                null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {

                try{
                    JSONArray pokesArray = response.getJSONArray("Search");

                    for (int i = 0; i < pokesArray.length(); i++) {

                        JSONObject pokeObj = pokesArray.getJSONObject(i);

                        Poke poke = new Poke();
                        poke.setName(pokeObj.getString("Name"));
                        poke.setType("Type: ");
                        poke.setSprite(pokeObj.getString("Sprite"));
                        poke.setNumber(pokeObj.getString("Number"));
                        Log.d("Pokes =: ", poke.getName());
                        pokeList.add(poke);

                    }

                    // pour mettre à jour les résultats de la recherche
                    pokeRecyclerViewAdapter.notifyDataSetChanged();


                }catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        queue.add(jsonObjectRequest);

        return pokeList;

    }

//Recherche
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem menuItem=menu.findItem(R.id.new_search);
        SearchView searchView=(SearchView)menuItem.getActionView();
        searchView.setQueryHint("Écrire le nom à rechercher");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Prefs prefs = new Prefs(MainActivity.this);
                if(!query.isEmpty())
                {
                    prefs.setSearch(query);
                    pokeList.clear();
                    getPokes(query);

                    //gestion du clavier

                    InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    if (getCurrentFocus() != null)
                    {
                        inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                    }
                }
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return true;
    }

}