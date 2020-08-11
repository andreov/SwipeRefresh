package com.example.simpleadapter;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.lang.Math.E;

public class ListViewActivity extends AppCompatActivity {

    private static final String KEY1="key1";
    private static final String KEY2="key2";
    private SimpleAdapter adapter;
    private String[] values;
    private SharedPreferences myListSharedPref;
    private static String LIST_TEXT = "list_text";
    private String noteTxt;
    private List<Map<String,String>>result=new ArrayList<>();
    private ArrayList<Integer>indexDel=new ArrayList<>();
    private static final String KEY_STATE = "keyOnState";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        myListSharedPref = getSharedPreferences("MyList",MODE_PRIVATE);
        saveString();
        values = prepareContent();
        final ListView list = findViewById(R.id.list);
        data();

        adapter=createAdapter();
        list.setAdapter(adapter);



        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
                result.remove(position);
                indexDel.add(position);
                adapter.notifyDataSetChanged();

            }

        });



        final SwipeRefreshLayout refreshLayout=findViewById(R.id.swipe_refresh_layout);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                result.clear();
                data();
                adapter.notifyDataSetChanged();
                refreshLayout.setRefreshing(false);
            }
        });

    }

    @Override
    protected  void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
        Log.d("Lifecycle", "onSaveInstanceState");
        outState.putIntegerArrayList(KEY_STATE,indexDel);
        //saveString();
        Toast.makeText(ListViewActivity.this, indexDel.toString(), Toast.LENGTH_LONG).show();
    }

    @Override
    protected  void onRestoreInstanceState(Bundle savedInstanceState){
        super.onRestoreInstanceState(savedInstanceState);
        Log.d("Lifecycle", "onRestoreInstanceState");
        if(savedInstanceState!=null) {
            ArrayList<Integer> restore = new ArrayList(savedInstanceState.getIntegerArrayList(KEY_STATE));
            for(int i=0;i<restore.size();i++){
                int x = restore.get(i).intValue();
                result.remove(x);
                //adapter.notifyDataSetChanged();
            }
        adapter.notifyDataSetChanged();
        }else return;

    }

    private void saveString(){
        SharedPreferences.Editor myEditor = myListSharedPref.edit();
        //noteTxt = myListSharedPref.getString(LIST_TEXT, "");
        //if(noteTxt.equals("")){
        myEditor.putString(LIST_TEXT, getString(R.string.large_text));
        myEditor.apply();
        //Toast.makeText(ListViewActivity.this, "Данные строки сохранены", Toast.LENGTH_LONG).show();
        //}
    }

    private String[] prepareContent() {
        noteTxt = myListSharedPref.getString(LIST_TEXT, "");
        //return getString(R.string.large_text).split("\n\n");
        return noteTxt.split("\n\n");
    }

    private void data (){
        for (int i=0; i<values.length;i++){
            Map<String,String>map=new HashMap<>();
            map.put(KEY1,values[i]);
            map.put(KEY2,values[i].length()+"");
            result.add(map);
        }

    }
    private SimpleAdapter createAdapter(){
        //saveString();
        return new SimpleAdapter(this,result,R.layout.list_item,new String[]{KEY1,KEY2},new int[]{R.id.text1,R.id.text2});
    }
}