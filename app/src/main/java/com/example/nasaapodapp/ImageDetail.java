package com.example.nasaapodapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class ImageDetail extends AppCompatActivity {

    ViewPager mViewPager;
    ViewPagerAdapter mViewPagerAdapter;
    public static int position;
    String url,title,description,date;
    public static ArrayList<String> ImageShowList = new ArrayList<>();
    public static ArrayList<String> ShowTitle = new ArrayList<>();
    public static ArrayList<String> ShowDescription = new ArrayList<>();
    public static ArrayList<String> ShowDate = new ArrayList<>();
    String json = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_detail);
        Intent intent = getIntent();
        position = intent.getIntExtra("position",0);
        url = intent.getStringExtra("Url");
        title = intent.getStringExtra("title");
        description = intent.getStringExtra("description");
        date = intent.getStringExtra("date");
        ImageShowList.add(url);
        ShowTitle.add(title);
        ShowDescription.add(description);
        ShowDate.add(date);
        loadJSONFromAsset();
        mViewPager = (ViewPager)findViewById(R.id.viewPagerMain);
        mViewPagerAdapter = new ViewPagerAdapter(ImageDetail.this, ImageShowList);
        mViewPager.setAdapter(mViewPagerAdapter);
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
            for (int i=position;i<jsonArray.length();i++){
                JSONObject obj = jsonArray.getJSONObject(i);
                if (obj.getString("url").equals(url)) {
                }else {
                    ImageShowList.add(obj.getString("url"));
                    ShowTitle.add(obj.getString("title"));
                    ShowDescription.add(obj.getString("explanation"));
                    ShowDate.add(obj.getString("date"));
                }
            }
            for (int j=0;j<position;j++){
                JSONObject obj = jsonArray.getJSONObject(j);
                ImageShowList.add(obj.getString("url"));
                ShowTitle.add(obj.getString("title"));
                ShowDescription.add(obj.getString("explanation"));
                ShowDate.add(obj.getString("date"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}