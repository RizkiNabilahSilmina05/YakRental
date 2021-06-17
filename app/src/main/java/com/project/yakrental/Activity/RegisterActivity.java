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

public class RegisterActivity extends AppCompatActivity {
    private EditText inputNama, inputEmail, inputPassword, inputNik, inputNohp;
    private View btnSubmit;
    private TextView btnLogin;
    private AlertDialogManager alert = new AlertDialogManager();
    private SessionManager session;
    private DatabaseHelper dbHelper;
    private SQLiteDatabase db;
    private String nama, email, password, nik, nohp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        dbHelper = new DatabaseHelper(this);
        db = dbHelper.getWritableDatabase();

        session = new SessionManager(getApplicationContext());

        inputNama = findViewById(R.id.reg_uname_input);
        inputEmail = findViewById(R.id.reg_email_input);
        inputPassword = findViewById(R.id.reg_pass_input);
        inputNik = findViewById(R.id.reg_nik_input);
        inputNohp = findViewById(R.id.reg_nohp_input);

        btnSubmit = findViewById(R.id.reg_submit_button);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nama = inputNama.getText().toString();
                email = inputEmail.getText().toString();
                password = inputPassword.getText().toString();
                nik = inputNik.getText().toString();
                nohp = inputNohp.getText().toString();

                try {
                    if (email.trim().length() > 0 && password.trim().length() > 0 && nama.trim().length() > 0 && nik.trim().length() > 0 && nohp.trim().length() > 0) {
                        dbHelper.open();
                        dbHelper.Register(nik, email, nama, nohp, password);
                        Toast.makeText(RegisterActivity.this, "Daftar berhasil", Toast.LENGTH_LONG).show();
                        finish();
                    } else {
                        Toast.makeText(RegisterActivity.this, "Daftar gagal, lengkapi form!", Toast.LENGTH_LONG).show();
                    }
                } catch (Exception e) {
                    Toast.makeText(RegisterActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });
        btnLogin = findViewById(R.id.reg_have_account);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(i);
            }
        });
    }
}
