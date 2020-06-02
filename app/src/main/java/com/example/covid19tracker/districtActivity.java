package com.example.covid19tracker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class districtActivity extends AppCompatActivity {
    TextView stateTV;
    String districturl="https://api.covid19india.org/v2/state_district_wise.json";
    ArrayList<states> districts=new ArrayList<>();
    TextView confirmcasesAlldistricts,activecasesAllistricts,deathsAlldistricts,curedAlldistricts;
    String s,s1;
    Adapter districtsAdapter;
    RecyclerView districtsRCV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_district);
        stateTV=findViewById(R.id.stateTVdistrict);
        Intent intent=getIntent();
        s=intent.getStringExtra("state");
        stateTV.setText(s);
        districts= new ArrayList<>();
        confirmcasesAlldistricts=findViewById(R.id.totalcasesTVdistrict);
        activecasesAllistricts=findViewById(R.id.ActivecasesTVdistrict);
        deathsAlldistricts=findViewById(R.id.DeathsTVdistrict);
        curedAlldistricts=findViewById(R.id.recoveredTVdistrict);
        s1=intent.getStringExtra("total");
        confirmcasesAlldistricts.setText(s1);
        s1=intent.getStringExtra("active");
        activecasesAllistricts.setText(s1);
        s1=intent.getStringExtra("dead");
        deathsAlldistricts.setText(s1);
        s1=intent.getStringExtra("cured");
        curedAlldistricts.setText(s1);

        districtsRCV=findViewById(R.id.districtsrRCV);
        Log.i("state",s);
        extactDisctrictdata();

    }
    public void extactDisctrictdata(){
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest=new JsonArrayRequest(Request.Method.GET, districturl, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for(int i=0;i<response.length();i++){
                    try {
                        JSONObject jsonObject=response.getJSONObject(i);
                        if(jsonObject.getString("state").equals(s)){
                            JSONArray array=jsonObject.getJSONArray("districtData");
                            states statesobj;
                            for (int j=0;j<array.length();j++){
                                statesobj=new states();
                                JSONObject object=array.getJSONObject(j);
                                statesobj.setState(object.getString("district"));
                                statesobj.setConfirm(object.getString("confirmed"));
                                statesobj.setActive(object.getString("active"));
                                statesobj.setDeath(object.getString("deceased"));
                                statesobj.setCured(object.getString("recovered"));
                                districts.add(statesobj);
                            }
                            break;
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                districtsRCV.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                districtsAdapter=new Adapter(getApplicationContext(),districts,false);
                districtsRCV.setAdapter(districtsAdapter);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("Error",error.toString());
            }
        });
        requestQueue.add(jsonArrayRequest);

    }
}
