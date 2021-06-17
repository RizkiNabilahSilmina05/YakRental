package com.project.yakrental.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.project.yakrental.Database.DatabaseHelper;
import com.project.yakrental.Model.CarModel;
import com.project.yakrental.R;
import com.project.yakrental.Session.SessionManager;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class DetailActivity extends AppCompatActivity {
    private Spinner spinMenu, spinHari;
    private String sMenu;
    private SessionManager session;
    protected Cursor cursor;
    private DatabaseHelper dbHelper;
    private SQLiteDatabase db;
    private String nama, nomor, tipe, warna, harga;
    private int gambar;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        dbHelper = new DatabaseHelper(this);

        session = new SessionManager(getApplicationContext());

        final String[] menu = {"", "Profil", "Logout"};
        spinMenu = findViewById(R.id.menu);
        ArrayAdapter<CharSequence> adapterMenu = new ArrayAdapter<CharSequence>(this, R.layout.simple_spinner_item, menu);
        adapterMenu.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
        spinMenu.setAdapter(adapterMenu);

        spinMenu.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                sMenu = parent.getItemAtPosition(position).toString();
                if(sMenu.equals(menu[1])) {
                    Intent i = new Intent(DetailActivity.this, ProfileActivity.class);
                    startActivity(i);
                }
                else if(sMenu.equals(menu[2])) {
                    AlertDialog dialog = new AlertDialog.Builder(DetailActivity.this)
                            .setTitle("Anda yakin ingin keluar ?")
                            .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    finish();
                                    session.logoutUser();
                                }
                            })
                            .setNegativeButton("Tidak", null)
                            .create();
                    dialog.show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        View btn_back = findViewById(R.id.back_button);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        Bundle extras = getIntent().getExtras();
        String idcar = "";
        if(extras == null) {
            idcar = "";
        } else {
            idcar = extras.getString("noKendaraan");
            Log.i("detail of index", idcar);
        }

        db = dbHelper.getReadableDatabase();
        cursor = db.rawQuery("SELECT * FROM TB_MOBIL WHERE no_mobil = '" + idcar + "'", null);
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            cursor.moveToPosition(0);
            nomor = cursor.getString(0);
            nama = cursor.getString(1);
            tipe = cursor.getString(2);
            warna = cursor.getString(3);
            harga = cursor.getString(4);
            gambar = cursor.getInt(5);
        }

        ImageView lblGambar = findViewById(R.id.detail_carimage);
        TextView lblNama = findViewById(R.id.detail_carname);
        TextView lblNo = findViewById(R.id.detail_carnumber);
        TextView lblTipe = findViewById(R.id.detail_cartype);
        TextView lblWarna = findViewById(R.id.detail_carcolor);
        TextView lblHarga = findViewById(R.id.detail_carprice);

        int hargaSewa = Integer.valueOf(harga);

        lblGambar.setImageResource(gambar);
        lblNama.setText(nama);
        lblNo.setText(nomor);
        lblTipe.setText(tipe);
        lblWarna.setText(warna);
        lblHarga.setText(currencyWithChosenLocalisation(hargaSewa, new Locale("id", "ID")));


        final String[] lamaSewa = {"1 hari", "2 hari", "3 hari", "4 hari"};
        spinHari = findViewById(R.id.detail_sewa);
        ArrayAdapter<CharSequence> adapterHari = new ArrayAdapter<CharSequence>(this, android.R.layout.simple_spinner_item, lamaSewa);
        adapterHari.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinHari.setAdapter(adapterHari);

        spinHari.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String hari = parent.getItemAtPosition(position).toString();
                int lamaSewa = Integer.parseInt(String.valueOf(hari.charAt(0)));

                int totalBiaya = lamaSewa * hargaSewa;
                String value = currencyWithChosenLocalisation(totalBiaya, new Locale("id", "ID"));

                TextView detailTotal = findViewById(R.id.detail_total_price);
                detailTotal.setText(value);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        View btnOrder = findViewById(R.id.detail_button_order);
        btnOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog dialog = new AlertDialog.Builder(DetailActivity.this)
                        .setTitle("Ingin Sewa Mobil sekarang?")
                        .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(DetailActivity.this, "Berhasil Memesan!", Toast.LENGTH_LONG).show();
                                finish();
                            }
                        })
                        .setNegativeButton("Tidak", null)
                        .create();
                dialog.show();
            }
        });
    }
    public static String currencyWithChosenLocalisation(double value, Locale locale) {
        NumberFormat nf = NumberFormat.getCurrencyInstance(locale);
        return nf.format(value);
    }
}
