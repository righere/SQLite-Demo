package com.example.righere.sqlitestudy;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.righere.sqlitestudy.database.DataBaseHelper;
import com.example.righere.sqlitestudy.database.ShowSearchResults;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Righere on 2016/8/16.
 */
public class DataBaseActivity extends AppCompatActivity {

    private SQLiteDatabase sqLiteOpenHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_database);
        DataBaseHelper dataBaseHelper = new DataBaseHelper(this);
        sqLiteOpenHelper = dataBaseHelper.getReadableDatabase();

        //添加单词
        Button mAddword = (Button) findViewById(R.id.add_word);
        //添加单词到数据库
        assert mAddword != null;
        mAddword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //输入单词
                EditText mEditText_word = (EditText) findViewById(R.id.word);
                //输入单词解释
                EditText mEditText_details = (EditText) findViewById(R.id.details);

                String word = mEditText_word.getText().toString();

                String details = mEditText_details.getText().toString();
                //获取本地时间
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy年MM月dd日 hh:mm:ss");
                Date CurDate = new Date(System.currentTimeMillis());
                String time = dateFormat.format(CurDate);

                //先将数据存入到ContentValues对象当中
                ContentValues contentValues = new ContentValues();
                contentValues.put(DataBaseHelper.TIME,time);
                contentValues.put(DataBaseHelper.WORD,word);
                contentValues.put(DataBaseHelper.DETAILS,details);

                //将contentValues插入到数据库到中
                long rowNum = sqLiteOpenHelper.insert(DataBaseHelper.WORD_DICTIONARY,null,contentValues);
                if(rowNum != -1){
                    Toast.makeText(DataBaseActivity.this,"add succeed！",Toast.LENGTH_SHORT).show();
                }
            }
        });

        //显示查询的数据库
        Button searchButton = (Button) findViewById(R.id.search);
        assert searchButton != null;
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String key = ((EditText)findViewById(R.id.search_key)).getText().toString();
                final String SQL_SEARCH = "select * from "
                        + DataBaseHelper.WORD_DICTIONARY
                        + "where "
                        + DataBaseHelper.WORD
                        + "like "+ key + " or "
                        + DataBaseHelper.DETAILS
                        + "like " + key;
//                Cursor cursor = sqLiteOpenHelper.rawQuery(SQL_SEARCH ,new String[] {"%" + key + "%","%" + key +"%"});
                  Cursor cursor = sqLiteOpenHelper.rawQuery("select * from "
                          + DataBaseHelper.WORD_DICTIONARY
                          + " where "
                          + DataBaseHelper.WORD
                          + " like "+ "?" + " or "
                          + DataBaseHelper.DETAILS
                          + " like " + "?",new String[] {"%" + key + "%","%" + key +"%"});

                //创建一个bundle对象用来存储查询的数据
                Bundle data = new Bundle();
                //将数据序列化
                data.putSerializable("data", (Serializable) cursor2list(cursor));

                //创建一个Intent在activity之间传递数据
                Intent intent = new Intent(DataBaseActivity.this,ShowSearchResults.class);
                //将数据绑定在intent对象上
                intent.putExtras(data);
                //切换到显示列表的activity
                startActivity(intent);
            }
        });
    }
    //将cursor里面的数据转换成列表list
    protected List<Map<String,Object>> cursor2list(Cursor cursor){

        //新建一个List集合，集合的元素是Map
        List<Map<String,Object>> listitems = new ArrayList<>();

        //遍历cursor结果中的数据
        while(cursor.moveToNext()){
            //将收集到的数据导入到arrayList中
            Map<String,Object> item = new HashMap<>();
            item.put(DataBaseHelper.TIME,cursor.getString(0));
            item.put(DataBaseHelper.WORD,cursor.getString(1));
            item.put(DataBaseHelper.DETAILS,cursor.getString(2));
            listitems.add(item);
        }
        return listitems;
    }

    //关闭数据库
    @Override
    public void onDestroy(){
        super.onDestroy();
        if (sqLiteOpenHelper != null){
            sqLiteOpenHelper.close();
        }
    }
}
