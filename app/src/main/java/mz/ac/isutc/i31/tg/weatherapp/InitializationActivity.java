package mz.ac.isutc.i31.tg.weatherapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.app.DownloadManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import mz.ac.isutc.i31.tg.weatherapp.databinding.ActivityInitializationBinding;


public class InitializationActivity extends AppCompatActivity {
    ActivityInitializationBinding initializationBinding;
    AppCompatButton backBtn;
    ListView listView;
    Double Celsius;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        initializationBinding = ActivityInitializationBinding.inflate(getLayoutInflater());
        setContentView(initializationBinding.getRoot());

        backBtn = findViewById(R.id.backBtn1);
        FindWeather();
        try {
            listView = initializationBinding.suggestions;
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.list_item, showSugestao(50.5));
            listView.setAdapter(adapter);
        }catch (Exception e){
            e.printStackTrace();
        }

        Database database = new Database(InitializationActivity.this);
        try {
            initializationBinding.nomeLog.setText("Hello, "+database.exibirNomeUtilizador());
        }catch (Exception e){
            e.printStackTrace();
            Toast.makeText(this, "Error Showing User!", Toast.LENGTH_SHORT).show();
        }
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(InitializationActivity.this,ListTarefas.class);
                startActivity(intent);

            }
        });
    }

    public void FindWeather()
    {
        final String city = "Maputo";
        String url ="https://api.openweathermap.org/data/2.5/weather?q="+city+"&appid=6fc291c29de626fdf2988ac1e00721bc";
        StringRequest stringRequest = new StringRequest(Request.Method.GET,url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {

                            JSONObject jsonObject = new JSONObject(response);
                            JSONObject object = jsonObject.getJSONObject("main");
                            double temp = object.getDouble("temp");
                            Celsius = Double.valueOf(Math.round(((temp-32)*5)/9));
                            initializationBinding.graus.setText(Celsius+"°C");

                            String city = jsonObject.getString("name");
                            initializationBinding.city.setText(city);

                            JSONObject object9 = jsonObject.getJSONObject("wind");
                            String wind_find = object9.getString("speed");
                            initializationBinding.windSpeed.setText("      "+wind_find+"km/h");

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(InitializationActivity.this,error.getLocalizedMessage(),Toast.LENGTH_SHORT).show();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(InitializationActivity.this);
        requestQueue.add(stringRequest);
    }

    public ArrayList showSugestao(Double temp){
        ArrayList<String> list = new ArrayList<>();
        if(temp <= 24){
            list.add("Correr");
            list.add("Assitir Netflix");
            list.add("Ler um livro");
        } else if (temp >25 && temp < 30) {
            list.add("Dar um role");
            list.add("Tomar um sorvete");
            list.add("Passear com amigos");
        }else if(temp > 30){
            list.add("Ir a praia");
            list.add("Andar a motorizada");
            list.add("Andar a skate");
        }
        return list;
    }
}