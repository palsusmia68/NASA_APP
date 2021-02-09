package com.example.nasaapodapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    String json = null;
    TextView t;
    RecyclerView recyclerView;
    ArrayList<ImageModel> dataModelArrayList;
    public static ArrayList<String> ImageList = new ArrayList<>();
    public static ArrayList<String> Title = new ArrayList<>();
    public static ArrayList<String> Description = new ArrayList<>();
    public static ArrayList<String> Date = new ArrayList<>();
    private RecyclerviewAdapter recyclerviewAdapter;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recyclerview);

        loadJSONFromAsset();

        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(getApplicationContext(), recyclerView ,new RecyclerItemClickListener.OnItemClickListener() {
                    public void onItemClick(View view, int position) {
                        Intent intent = new Intent(MainActivity.this,ImageDetail.class);
                        intent.putExtra("position",position);
                        ImageDetail.ShowDate.clear();
                        ImageDetail.ImageShowList.clear();
                        ImageDetail.ShowDescription.clear();
                        ImageDetail.ShowTitle.clear();
                        intent.putExtra("Url",dataModelArrayList.get(position).getUrl());
                        intent.putExtra("title",Title.get(position));
                        intent.putExtra("date",Date.get(position));
                        intent.putExtra("description",Description.get(position));
                        startActivity(intent);
                    }

                    public void onLongItemClick(View view, int position) {
                        // do whatever
                    }
                })
        );
    }
    public String loadJSONFromAsset() {
        try {
            InputStream is = getAssets().open("data.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
            GetValueFromJson();
            Log.d("json ",json);


        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;

    }

    private void GetValueFromJson() {
        try {
            JSONArray jsonArray = new JSONArray(json);
            dataModelArrayList = new ArrayList<>();
            for (int i=0;i<jsonArray.length();i++){
                ImageModel imageModel = new ImageModel();
                JSONObject obj = jsonArray.getJSONObject(i);
                imageModel.setUrl(obj.getString("url"));
                ImageList.add(obj.getString("url"));
                Title.add(obj.getString("title"));
                Description.add(obj.getString("explanation"));
                Date.add(obj.getString("date"));
                dataModelArrayList.add(imageModel);
            }
            recyclerviewAdapter = new RecyclerviewAdapter(getApplicationContext(),dataModelArrayList);
            recyclerView.setAdapter(recyclerviewAdapter);
            recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(),2,GridLayoutManager.VERTICAL,false));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}