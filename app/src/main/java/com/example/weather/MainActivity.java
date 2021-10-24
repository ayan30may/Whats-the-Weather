package com.example.weather;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {
    EditText location;
    Button go;
    TextView forecast;
    TextView temperature;
    TextView city;
    TextView humidity_text;
    TextView pressure_text;
    TextView wind_text;
    TextView humidity_value;
    TextView pressure_value;
    TextView wind_value;

    private final String url = "https://api.openweathermap.org/data/2.5/weather";
    private final String appId = "f5464d8f9d0080a3f85f31b040dfd73b&units=metric";
    DecimalFormat df = new DecimalFormat("#.##");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        location = findViewById(R.id.location);
        go = findViewById(R.id.go);
        forecast = findViewById(R.id.forecast);
        temperature = findViewById(R.id.temperature);
        city = findViewById(R.id.city);
        humidity_text = findViewById(R.id.humidity_text);
        humidity_value = findViewById(R.id.humidity_value);
        pressure_text = findViewById(R.id.pressure_text);
        pressure_value = findViewById(R.id.pressure_value);
        wind_text = findViewById(R.id.wind_text);
        wind_value = findViewById(R.id.wind_value);
    }
    public void search(final View view){
        String appId = "f5464d8f9d0080a3f85f31b040dfd73b&units=metric";
        final String loc = location.getText().toString().trim();
        String url = "https://api.openweathermap.org/data/2.5/weather?q="+loc+"&appid=f5464d8f9d0080a3f85f31b040dfd73b&units=metric";
        if(loc.equals("")){
            location.setError("Enter the location");
        }
        else {
            RequestQueue queue = Volley.newRequestQueue(this);
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                @Override
                public void onResponse(JSONObject response) {
                    try {
                        JSONObject jsonObject = response.getJSONObject("main");
                        String temp = jsonObject.getString("temp");
                        temperature.setVisibility(View.VISIBLE);
                        temperature.setText(temp+"°c");
                        String humidity = jsonObject.getString("humidity");
                        String pressure = jsonObject.getString("pressure");
                        humidity_value.setVisibility(View.VISIBLE);
                        humidity_value.setText(humidity);
                        pressure_value.setVisibility(View.VISIBLE);
                        pressure_value.setText(pressure);
                        JSONObject jsonObject1 = response.getJSONObject("wind");
                        String wind = jsonObject1.getString("speed");
                        wind_value.setText(wind + "Km/h");
                        wind_value.setVisibility(View.VISIBLE);
                        city.setText(location.getText().toString().trim());
                        city.setVisibility(View.VISIBLE);
                        JSONObject jsonObject2 = response.getJSONObject("weather");
                        String desc = jsonObject2.getString("description");
                        forecast.setText(desc);
                        forecast.setVisibility(View.VISIBLE);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    // TODO: Handle error
                    Toast.makeText(MainActivity.this, "Please Try Again!", Toast.LENGTH_SHORT).show();
                }
            });
            queue.add(jsonObjectRequest);
        }
    }
}