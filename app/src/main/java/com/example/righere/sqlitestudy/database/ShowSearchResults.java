package com.example.righere.sqlitestudy.database;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.example.righere.sqlitestudy.R;

import java.util.List;
import java.util.Map;

/**
 * Created by Righere on 2016/8/21.
 * 显示数据库的搜索结果
 */
public class ShowSearchResults extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showsearchresults);
        ListView listview = (ListView) findViewById(R.id.show);
        Intent intent = getIntent();
        //获取bundle的数据
        Bundle data = intent.getExtras();
        @SuppressWarnings("unchecked")
        List<Map<String,String>> showlist = (List<Map<String, String>>) data.getSerializable("data");

        //将showlist封装进adapter
        SimpleAdapter adapter = new SimpleAdapter(ShowSearchResults.this,
                                                    showlist,R.layout.listviewadapter,
                                                    new String[] {DataBaseHelper.TIME,DataBaseHelper.WORD,DataBaseHelper.DETAILS},
                                                    new int[]{R.id.date_search,R.id.word_search,R.id.details_search});
        assert listview != null;
        listview.setAdapter(adapter);
    }
}
