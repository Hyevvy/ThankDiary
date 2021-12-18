package com.example.thankdiary;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class FragmentTop extends Fragment {
    View view;
    Integer day, totalDay;
    EditText etNum, etTotalNum;
    DBHelper myDB;

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

        totalDay = myDB.getTotalDay();
        System.out.println(totalDay);
        Bundle data = getArguments();
        if (data != null) {
            day = data.getInt("day");
           // totalDay = data.getInt("totalDay");
        } else {
            day = 0;
            //totalDay = 0;
        }

        etNum.setText(String.valueOf(day));
        etTotalNum.setText(String.valueOf(totalDay));

        return view;
    }
}
