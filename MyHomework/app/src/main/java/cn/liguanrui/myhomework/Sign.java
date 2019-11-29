package cn.liguanrui.myhomework;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Sign extends Activity {
    public String deleteName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign);

        final DBManager dbManager = new DBManager(this);

        final EditText et_sign_name = (EditText)findViewById(R.id.et_sign_name);
        final EditText et_sign_pwd = (EditText)findViewById(R.id.et_sign_pwd);
        Button bt_register = (Button)findViewById(R.id.bt_register);
        Button bt_login = (Button)findViewById(R.id.bt_login);
        Button bt_record = (Button)findViewById(R.id.bt_record);
        Button bt_delete = (Button)findViewById(R.id.bt_delete);

        Intent intent = getIntent();
        String newname = intent.getStringExtra("name");
        et_sign_name.setText(newname);

        //注册
        bt_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Sign.this, Register.class);
                startActivity(intent);
            }
        });

        //查看记录
        bt_record.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Sign.this, Recode.class);
                startActivity(intent);

            }
        });


        //登录
        bt_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 String login_name = et_sign_name.getText().toString();
                 String login_pwd = et_sign_pwd.getText().toString();
                if (login_name.equals("") || login_pwd.equals("")){
                    Toast.makeText(Sign.this,"用户名或密码不能为空！",Toast.LENGTH_SHORT).show();
                }else{
                    //判断用户名和密码是否正确
                    Cursor cursor = dbManager.query(login_name, login_pwd);
                    if (cursor.moveToNext()){
                        Toast.makeText(Sign.this, "登录成功", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(Sign.this, MainActivity.class);
                        intent.putExtra("name",login_name);
                        startActivity(intent);
                    }else{
                        Toast.makeText(Sign.this,"用户名或密码错误！",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        //注销用户
        bt_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbManager.delete(et_sign_name.getText().toString());
                Toast.makeText(Sign.this,"已成功删除"+et_sign_name.getText().toString(),Toast.LENGTH_SHORT).show();
            }
        });
    }

}
