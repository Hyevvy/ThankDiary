package com.example.thankdiary;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.text.SimpleDateFormat;

public class FragmentMonth extends Fragment {
    View view;
    DatePicker datePicker;
    String choiceDate;
    DBHelper myDB;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_month, container,false);
        datePicker = (DatePicker)view.findViewById(R.id.datePicker);
        myDB = new DBHelper(getActivity());

        datePicker.init(datePicker.getYear(), datePicker.getMonth(), datePicker.getDayOfMonth(), new DatePicker.OnDateChangedListener(){

            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                choiceDate = String.format("%d. %d. %d", year,monthOfYear + 1, dayOfMonth);
                System.out.println(choiceDate);

                //getTodayDiaryList는 받은 인자(날짜)에 해당하는 일기가 있다면 그 내용을 리턴하고, 아니면 null을 리턴합니다.
                String choiceDateDiary = myDB.getTodayDiaryList(choiceDate);
                if(choiceDateDiary != null){
                  //선택한 날짜에 맞는 일기가 있으면 dialog를 통해 보여준다

                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setTitle(choiceDate);
                    builder.setMessage(choiceDateDiary);
                    builder.setPositiveButton("닫기", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });
                    builder.show();
                }
                else {
                    Toast.makeText(getActivity(), choiceDate + "에 작성한 일기가 없어요", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }
}
