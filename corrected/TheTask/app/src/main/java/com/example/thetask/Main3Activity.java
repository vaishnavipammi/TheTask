package com.example.thetask;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class Main3Activity extends AppCompatActivity {
    String code3;

    public ArrayList<String> stoplocations = new ArrayList<String>();



    private String TAG = Main2Activity.class.getSimpleName();
    private ListView lv;


    ArrayList<HashMap<String, String>> busstopList;


    public void viewonthemap(View view) {

        Intent i = new Intent(this, MapsActivity.class);

        i.putExtra("locationlist", stoplocations);
        startActivity(i);
    }
    @Override
    public void onBackPressed()
    {
        Intent intent =new Intent(this,MainActivity.class);
        startActivity(intent);


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        Intent i = getIntent();
        //code3 stores the code of the route which is clicked upon
        code3 = i.getStringExtra("name");
        Log.e(TAG, "code3: " + code3);

        busstopList = new ArrayList<>();
        lv = (ListView) findViewById(R.id.list);

        new Getroutes().execute();
    }


    private class Getroutes extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Toast.makeText(Main3Activity.this, "Loading", Toast.LENGTH_SHORT).show();

        }

        @Override
        protected Void doInBackground(Void... arg0) {

            HttpHandler sh = new HttpHandler();
            // Making a request to url and getting response
            String url = "https://api.commut.co/riders/routes";
            String jsonStr = sh.makeServiceCall(url);

            Log.e(TAG, "Response from url: " + jsonStr);
            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);

                    // Getting JSON Array node
                    JSONArray routes = jsonObj.getJSONArray("data");
                    for (int i = 0; i < routes.length(); i++) {
                        JSONObject jsonnew = routes.getJSONObject(i);
                        String code2 = jsonnew.getString("code");
                        if (code2.equals(code3)) {
                            //
                            JSONArray jsonarrayROW = jsonnew.getJSONArray("busstops");
                            for (int j = 0; j < jsonarrayROW.length(); j++) {
                                JSONObject jsonnew2 = jsonarrayROW.getJSONObject(j);

                                String name = jsonnew2.getString("name");
                                JSONObject json222 = jsonnew2.getJSONObject("geoPoint");
                                String longitude = json222.getString("long");
                                stoplocations.add(2 * j, longitude);
                                String latitude = json222.getString("lat");
                                stoplocations.add(2 * j + 1, latitude);
                                HashMap<String, String> route = new HashMap<>();
                                route.put("name", name);
                                busstopList.add(route);

                            }
                        }

                    }
                } catch (final JSONException e) {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),
                                    "Json parsing error: " + e.getMessage(),
                                    Toast.LENGTH_LONG).show();
                        }
                    });

                }
            } else {
                Log.e(TAG, "Couldn't get json from server.");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                                "Couldn't get json from server. Check LogCat for possible errors!",
                                Toast.LENGTH_LONG).show();
                    }
                });
            }
            return null;
        }
       // @Override
       // public void onbackPressed()
       // {

        //}

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            ListAdapter adapter = new SimpleAdapter(Main3Activity.this, busstopList,
                    R.layout.list_item_2, new String[]{"name"},
                    new int[]{R.id.code});
            lv.setAdapter(adapter);
        }
    }

}
