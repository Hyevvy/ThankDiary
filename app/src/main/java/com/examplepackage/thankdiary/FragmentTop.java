package com.examplepackage.thankdiary;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.text.SimpleDateFormat;
import java.util.Date;

public class FragmentTop extends Fragment {
    View view;
    Integer seriesDay, totalDay;
    EditText etNum, etTotalNum;
    DBHelper myDB;
    long now;
    Date date;
    String todayDate;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_top, container,false);
        etNum = (EditText) view.findViewById(R.id.etNum);
        etTotalNum = (EditText) view.findViewById(R.id.etNumTotal);
        myDB = new DBHelper(getActivity());

        now = System.currentTimeMillis();
        date = new Date(now);
        totalDay = myDB.getTotalDay();
        seriesDay = myDB.getSeriesDay(new SimpleDateFormat("yyyy. MM. dd").format(date));
        if(totalDay == null){
            totalDay = 0;
        }

        etNum.setText(String.valueOf(seriesDay));
        etTotalNum.setText(String.valueOf(totalDay));

        return view;
    }
}
