package com.examplepackage.thankdiary;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.text.SimpleDateFormat;
import java.util.Date;

public class FragmentEdit extends Fragment {
    View view;
    TextView tvToday;
    EditText etTodayDt;
    long now;
    Date date;
    SimpleDateFormat mFormat = new SimpleDateFormat("yyyy. MM. dd  EE");
    boolean isExist;
    Button updateBtn, submitBtn, editBtn;
    DBHelper myDB;
    String todayDate, todayContent;
    boolean isTextChanged = false;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_edit, container,false);
        tvToday = (TextView)view.findViewById(R.id.tvToday);
        etTodayDt = (EditText)view.findViewById(R.id.etTodayTD);
        updateBtn = (Button)view.findViewById(R.id.updateBtn);
        submitBtn = (Button)view.findViewById(R.id.submitBtn);
        editBtn = (Button)view.findViewById(R.id.editBtn);
        myDB = new DBHelper(getActivity());


        //1. 오늘 날짜를 구해서 tvToday에 넣어준다.
        now = System.currentTimeMillis();
        date = new Date(now);
        tvToday.setText(new SimpleDateFormat("yyyy. MM. dd").format(date));



        //2. 오늘 작성한 감사 일기가 DB에 있다면 보여주고, 수정이 가능하게 한다.
        todayDate = tvToday.getText().toString();
        String todayDiaryInDB = myDB.getTodayDiaryList(todayDate);
        if(todayDiaryInDB == null){
            //오늘 작성한 감사 일기가 없는 경우
            submitBtn.setOnClickListener(new View.OnClickListener() {
                //TODO : DB에 올리고, UPDATE 버튼이 보이게
                @Override
                public void onClick(View v) {
                    todayContent = etTodayDt.getText().toString();
                    if(todayContent.length() == 0){
                        Toast.makeText(getActivity(), "오늘 일기를 작성해주세요", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        //오늘의 일기 내용이 있을 때만 UPDATE와 EDIT 버튼이 보이도록
                        //일기를 저장 후 etTodayDt는 수정이 불가하게(수정하려면 EDIT버튼을 통해 할 수 있도록)
                        myDB.InsertDiary(todayContent , todayDate);
                        etTodayDt.setEnabled(false);
                        Toast.makeText(getActivity(), "오늘의 감사일기가 저장되었습니다", Toast.LENGTH_SHORT).show();
                        updateBtn.setVisibility(View.VISIBLE);
                        editBtn.setVisibility(View.VISIBLE);
                        submitBtn.setVisibility(View.GONE);
                    }

                }
            });
        }
        else {
            System.out.println("hehehr");
            //오늘 작성한 감사 일기가 있으면
            etTodayDt.setText(todayDiaryInDB);
            etTodayDt.setEnabled(false);
            updateBtn.setVisibility(View.VISIBLE);
            updateBtn.setEnabled(false);
            editBtn.setVisibility(View.VISIBLE);
            submitBtn.setVisibility(View.GONE);
        }

        //EDIT 버튼을 눌러야지만 수정이 가능하도록
        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etTodayDt.setEnabled(true);
                editBtn.setEnabled(false);
            }
        });

        String etTodayDtString = etTodayDt.getText().toString();

        //일기의 내용이 변했는지 확인
        etTodayDt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!etTodayDtString.contentEquals(s)) {
                    isTextChanged = true;
                    updateBtn.setEnabled(true);
                    //일기 내용이 바뀌었을때만 UPDATE 버튼이 동작하도록
                    BeAvailabeUpdateBtn();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                isTextChanged = true;
            }
        });



        return view;
    }

    private void BeAvailabeUpdateBtn(){
        if(isTextChanged == true) {
            //update database
            updateBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    todayContent = etTodayDt.getText().toString();
                    myDB.UpdateDiary(todayContent, todayDate);
                    Toast.makeText(getActivity(), "오늘의 감사일기가 수정되었습니다", Toast.LENGTH_SHORT).show();
                    updateBtn.setEnabled(false);
                    editBtn.setEnabled(true);
                    etTodayDt.setEnabled(false);
                    isTextChanged = false;
                }
            });
        }
    }

}
