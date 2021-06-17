package com.project.yakrental.Activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.project.yakrental.Adapter.AlertDialogManager;
import com.project.yakrental.R;

public class LupaPassActivity extends AppCompatActivity {
    private EditText input;
    private AlertDialogManager alert = new AlertDialogManager();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lupa_password);

        input = findViewById(R.id.lupa_pass_email);

        View btnSubmit = findViewById(R.id.lupa_submit_button);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sInput = input.getText().toString();
                //do something
                alert.showAlertDialog(LupaPassActivity.this, "Berhasil", "Password telah dikirim!", false);
            }
        });

        View btn_back = findViewById(R.id.back_button);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

}
