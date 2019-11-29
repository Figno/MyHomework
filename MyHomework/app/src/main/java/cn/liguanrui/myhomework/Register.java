package cn.liguanrui.myhomework;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Register extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        final DBManager dbManager = new DBManager(this);
        final EditText et_register_name = (EditText)findViewById(R.id.et_register_name);
        final EditText et_register_pwd = (EditText)findViewById(R.id.et_register_pwd);

        Button bt_register_true = (Button)findViewById(R.id.bt_register_true);

        bt_register_true.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String et_name = et_register_name.getText().toString();
                String et_pwd = et_register_pwd.getText().toString();

                //判断新注册的用户名是否已存在
                Cursor cursor = dbManager.requery(et_name);
                if (cursor.moveToNext()){
                    Toast.makeText(Register.this,"该用户已被注册！",Toast.LENGTH_LONG).show();
                }else {
                    dbManager.insert(et_name, et_pwd, "0");
                    Intent intent = new Intent(Register.this, Sign.class);
                    intent.putExtra("name",et_name);
                    startActivity(intent);
                }
            }
        });
    }
}