package com.project.yakrental.Activity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.project.yakrental.Adapter.AlertDialogManager;
import com.project.yakrental.Database.DatabaseHelper;
import com.project.yakrental.R;
import com.project.yakrental.Session.SessionManager;

public class LoginActivity extends AppCompatActivity {
    private EditText inputUname, inputPassword;
    private View submitBtn;
    private TextView btnLupa;
    private SessionManager session;
    private AlertDialogManager alert = new AlertDialogManager();
    private DatabaseHelper dbHelper;
    private SQLiteDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        dbHelper = new DatabaseHelper(this);
        db = dbHelper.getWritableDatabase();

        session = new SessionManager(getApplicationContext());

        inputUname = findViewById(R.id.login_username_input);
        inputPassword = findViewById(R.id.login_pass_input);

        submitBtn = findViewById(R.id.login_submit_button);
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get username, password from EditText
                String username = inputUname.getText().toString();
                String password = inputPassword.getText().toString();

                // Check if username, password is filled
                try {
                    // Check if username, password is filled
                    if (username.trim().length() > 0 && password.trim().length() > 0) {
                        dbHelper.open();

                        if (dbHelper.Login(username, password)) {
                            session.createLoginSession(username);

                            finish();
                            Intent i = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(i);

                        } else {
                            alert.showAlertDialog(LoginActivity.this, "Login gagal..", "Email atau Password salah!", false);

                        }
                    } else {
                        alert.showAlertDialog(LoginActivity.this, "Login gagal..", "Form tidak boleh kosong!", false);
                    }
                } catch (Exception e) {
                    Toast.makeText(LoginActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });

        btnLupa = findViewById(R.id.login_lupa_pass);
        btnLupa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this, LupaPassActivity.class);
                startActivity(i);
            }
        });
    }
}
