package com.project.yakrental.Adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import com.project.yakrental.Model.CarModel;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import com.project.yakrental.R;

public class ListCarAdapter extends ArrayAdapter<CarModel> {

    public ListCarAdapter(Activity context, ArrayList<CarModel> notification) {
        super(context, 0, notification);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @SuppressLint("SetTextI18n")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item_car, parent, false);
        }

        CarModel current = getItem(position);

        ImageView gambar = listItemView.findViewById(R.id.car);
        gambar.setImageResource(current.getGambar());

        return listItemView;
    }
}
