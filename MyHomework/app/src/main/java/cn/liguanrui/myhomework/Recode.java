package cn.liguanrui.myhomework;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class Recode extends Activity {

    DBManager dbManager = new DBManager(this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recode);

        Button returngame = (Button) findViewById(R.id.returngame);


        final DBManager dbManager = new DBManager(this);
        String[] data = dbManager.allSql();

        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(Recode.this, android.R.layout.simple_list_item_1, data);
        final ListView lv_data = (ListView) findViewById(R.id.lv_data);
        lv_data.setAdapter(adapter);


        returngame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Recode.this, Sign.class);
                startActivity(intent);
            }
        });
    }

}
