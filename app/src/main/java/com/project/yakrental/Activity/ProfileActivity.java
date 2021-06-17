package com.project.yakrental.Activity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.project.yakrental.Database.DatabaseHelper;
import com.project.yakrental.R;
import com.project.yakrental.Session.SessionManager;

import java.util.HashMap;

public class ProfileActivity extends AppCompatActivity {

    protected Cursor cursor;
    private DatabaseHelper dbHelper;
    private SQLiteDatabase db;
    private SessionManager session;
    private String name, email, nik, nohp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        dbHelper = new DatabaseHelper(this);

        session = new SessionManager(getApplicationContext());

        HashMap<String, String> user = session.getUserDetails();
        email = user.get(SessionManager.KEY_EMAIL);

        db = dbHelper.getReadableDatabase();
        cursor = db.rawQuery("SELECT * FROM TB_USER WHERE email = '" + email + "'", null);
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            cursor.moveToPosition(0);
            name = cursor.getString(4);
            nik = cursor.getString(0);
            nohp = cursor.getString(3);
        }

        TextView lblName = findViewById(R.id.profil_username);
        TextView lblEmail = findViewById(R.id.profil_email);
        TextView lblNik = findViewById(R.id.profil_nik);
        TextView lblNohp = findViewById(R.id.profil_nohp);

        lblName.setText(name);
        lblEmail.setText(email);
        lblNik.setText(nik);
        lblNohp.setText(nohp);


        View btn_back = findViewById(R.id.back_button);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

}
