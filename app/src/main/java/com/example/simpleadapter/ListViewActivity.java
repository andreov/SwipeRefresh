package com.example.simpleadapter;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ListViewActivity extends AppCompatActivity {

    private static final String KEY1="key1";
    private static final String KEY2="key2";
    private ListAdapter adapter;
    private String[] values;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ListView list = findViewById(R.id.list);

        values = prepareContent();

        adapter=createAdapter();

        list.setAdapter(adapter);
    }


    private String[] prepareContent() {

        return getString(R.string.large_text).split("\n\n");
    }

    private static List<Map<String, String>> data (String KEY1, String KEY2, String[] values){
        List<Map<String,String>>result=new ArrayList<>();
        for (int i=0; i<values.length;i++){
            Map<String,String>map=new HashMap<>();
            map.put(KEY1,values[i]);
            map.put(KEY2,values[i].length()+"");
            result.add(map);
        }
        return result;
    }
    private ListAdapter createAdapter(){
        List<Map<String,String>>mapData=data(KEY1,KEY2,values);
        return new SimpleAdapter(this,mapData,R.layout.list_item,new String[]{KEY1,KEY2},new int[]{R.id.text1,R.id.text2});
    }
}