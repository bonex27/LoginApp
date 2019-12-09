package com.ipack.loginpage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.ipack.loginpage.ui.login.LoginActivity;


public class MainActivity extends AppCompatActivity {

    private Button Login;
    private Button Reg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Login = (Button)findViewById(R.id.btnLog);
        Login.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                login();
            }
        });
    }
    private void  login()
    {
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
    }
}
