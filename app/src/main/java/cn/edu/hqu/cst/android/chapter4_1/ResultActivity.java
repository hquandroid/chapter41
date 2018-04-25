package cn.edu.hqu.cst.android.chapter4_1;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ResultActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        Intent intent=getIntent();
        Bundle bundle=intent.getExtras();
        List<Map<String,String>> list= (List<Map<String, String>>) bundle.getSerializable("data");
        SimpleAdapter  adapter=new SimpleAdapter(ResultActivity.this,list,R.layout.line,new String[]{Words.Word.WORD,Words.Word.DETAIL},new int[]{R.id.word,R.id.detail});
        ((ListView)findViewById(R.id.result)).setAdapter(adapter);
    }

}
