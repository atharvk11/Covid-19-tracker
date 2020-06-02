package com.example.covid19tracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private RecyclerView recyclerView;
    private TextView totalcases, activetv, dead, recovered;
    Toolbar toolbar;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ArrayList<states> statelist;
    public static String json_url = "https://api.covid19india.org/data.json";
    Adapter adapter;
    states s;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        recyclerView = findViewById(R.id.recyclerView);
        totalcases = findViewById(R.id.totalcasesTV);
        activetv = findViewById(R.id.ActivecasesTV);
        dead = findViewById(R.id.DeathsTV);
        recovered = findViewById(R.id.recoveredTV);
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        navigationView.bringToFront();
        statelist = new ArrayList<>();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        navigationView.setNavigationItemSelectedListener(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new Adapter(getApplicationContext(), statelist, true);
        recyclerView.setAdapter(adapter);
        extractData();

    }

    public void extractData() {
        RequestQueue queue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonArrayRequest = new JsonObjectRequest(Request.Method.GET, json_url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    JSONArray jsonArray = response.getJSONArray("statewise");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject object = jsonArray.getJSONObject(i);
                        if (i == 0) {
                            totalcases.setText(object.getString("confirmed"));
                            activetv.setText(object.getString("active"));
                            dead.setText(object.getString("deaths"));
                            recovered.setText(object.getString("recovered"));
                        } else {
                            s = new states();
                            s.setState(object.getString("state"));
                            s.setConfirm(object.getString("confirmed"));
                            s.setActive(object.getString("active"));
                            s.setDeath(object.getString("deaths"));
                            s.setCured(object.getString("recovered"));
                            statelist.add(s);
                        }
                    }
                    adapter.notifyDataSetChanged();

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Toast.makeText(this,"Network Error",Toast.LENGTH_SHORT).show();
                Log.i("Result", "Error");
            }
        });
        Log.i("size",String.valueOf(statelist.size()));
        queue.add(jsonArrayRequest);

    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_home: {
                drawerLayout.closeDrawer(GravityCompat.START);
                break;
            }
            case R.id.open_in_web_nav:{
                Intent myWebLink = new Intent(android.content.Intent.ACTION_VIEW);
                myWebLink.setData(Uri.parse("https://covid19indiadata.herokuapp.com/"));
                startActivity(myWebLink);
                break;
            }
            case R.id.nav_info: {
                Intent intent=new Intent(getApplicationContext(),Info.class);
                startActivity(intent);
                break;
            }
            case R.id.nav_feedback:{
                try{
                    Intent intent = new Intent (Intent.ACTION_VIEW , Uri.parse("mailto:" + "atharvsk11@gmail.com"));
                    intent.putExtra(Intent.EXTRA_SUBJECT, "Feedback");
                    //intent.putExtra(Intent.EXTRA_TEXT, "your_text");
                    startActivity(intent);
                }catch(ActivityNotFoundException e){
                    e.printStackTrace();
                }
            }
        }
        return true;
    }

    public void linkedInClick(View view) {
        Uri uri = Uri.parse("https://www.linkedin.com/in/atharv-kulkarni-3704861a4/");
        Intent likeIng = new Intent(Intent.ACTION_VIEW, uri);

        likeIng.setPackage("com.linkedin.android");

        try {
            startActivity(likeIng);
        } catch (ActivityNotFoundException e) {
            startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("https://www.linkedin.com/in/atharv-kulkarni-3704861a4/")));
        }

    }

    public void instagramClick(View view) {
        Uri uri = Uri.parse("http://instagram.com/_u/atharvk11");
        Intent likeIng = new Intent(Intent.ACTION_VIEW, uri);

        likeIng.setPackage("com.instagram.android");

        try {
            startActivity(likeIng);
        } catch (ActivityNotFoundException e) {
            startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://instagram.com/atharvk11")));
        }

    }

    public void twitterClick(View view) {
        Uri uri = Uri.parse("https://twitter.com/atharvk11");
        Intent likeIng = new Intent(Intent.ACTION_VIEW, uri);

        likeIng.setPackage("com.twitter.android");

        try {
            startActivity(likeIng);
        } catch (ActivityNotFoundException e) {
            startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("https://twitter.com/atharvk11")));
        }
    }

}
