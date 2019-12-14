package com.ipack.loginpage;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.ipack.loginpage.ui.login.LoginActivity;


public class MainActivity extends AppCompatActivity {

    private Button Login;
    private Button Reg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Login = findViewById(R.id.btnLog);
        Reg = findViewById(R.id.btnReg);
        Login.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                login();
            }
        });
        Reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reg();
            }
        });
    }
    private void  login()
    {
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
    }

    private void reg() {
        Intent intent = new Intent(MainActivity.this, RegActivity.class);
        startActivity(intent);
    }
}
