package com.project.yakrental.Activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import com.project.yakrental.Adapter.ListCarAdapter;
import com.project.yakrental.Database.DatabaseHelper;
import com.project.yakrental.Model.CarModel;
import com.project.yakrental.R;
import com.project.yakrental.Session.SessionManager;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private Spinner spinMenu;
    private String sMenu;
    private SessionManager session;
    protected Cursor cursor;
    private DatabaseHelper dbHelper;
    private SQLiteDatabase db;
    private String jenisMobil, noMobil, tipeMobil, warnaMobil, hargaSewa;
    private int gambarMobil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        session = new SessionManager(getApplicationContext());
        session.checkLogin();

        dbHelper = new DatabaseHelper(MainActivity.this);
        db = dbHelper.getReadableDatabase();

        final String[] menu = {"", "Profil", "Logout"};
        spinMenu = findViewById(R.id.menu);
        ArrayAdapter<CharSequence> adapterAsal = new ArrayAdapter<CharSequence>(this, R.layout.simple_spinner_item, menu);
        adapterAsal.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
        spinMenu.setAdapter(adapterAsal);

        spinMenu.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                sMenu = parent.getItemAtPosition(position).toString();
                if(sMenu.equals(menu[1])) {
                    Intent i = new Intent(MainActivity.this, ProfileActivity.class);
                    startActivity(i);
                }
                else if(sMenu.equals(menu[2])) {
                    AlertDialog dialog = new AlertDialog.Builder(MainActivity.this)
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

        refreshList();
    }

    public void refreshList() {
        final ArrayList<CarModel> myCar = new ArrayList<>();
        cursor = db.rawQuery("SELECT * FROM TB_MOBIL", null);
        cursor.moveToFirst();
        for (int i = 0; i < cursor.getCount(); i++) {
            cursor.moveToPosition(i);
            noMobil = cursor.getString(0);
            jenisMobil = cursor.getString(1);
            tipeMobil = cursor.getString(2);
            warnaMobil = cursor.getString(3);
            hargaSewa = cursor.getString(4);
            gambarMobil = cursor.getInt(5);

            myCar.add(new CarModel(jenisMobil, noMobil, tipeMobil, warnaMobil, hargaSewa, gambarMobil));
        }
        ListView listCar = findViewById(R.id.list_car);
        ListCarAdapter adapter = new ListCarAdapter(this, myCar);
        listCar.setAdapter(adapter);

        listCar.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(MainActivity.this, DetailActivity.class);

                intent.putExtra("noKendaraan", myCar.get(i).getNoKendaraan());
                startActivity(intent);
            }
        });
    }
}
