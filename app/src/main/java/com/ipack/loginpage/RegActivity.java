package com.ipack.loginpage;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class RegActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg);
        final Button btnReg = findViewById(R.id.btnConfirm);
        final EditText NameEditText = findViewById(R.id.txtName);
        final EditText CognEditText = findViewById(R.id.txtCognome);
        final EditText UserEditText = findViewById(R.id.txtUser);
        final EditText dataNEditText = findViewById(R.id.txtDataN);
        final EditText emailEditText = findViewById(R.id.txtEmail);
        final EditText passEditText = findViewById(R.id.txtPass);
        btnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Sender s = new Sender(RegActivity.this, "http://ipack.dx.am/RegPage.php", true, NameEditText, CognEditText, UserEditText, dataNEditText, emailEditText, passEditText);
                s.execute();
            }
        });
    }
}
