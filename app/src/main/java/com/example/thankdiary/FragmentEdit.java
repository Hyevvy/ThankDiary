package com.example.thankdiary;

import android.graphics.Typeface;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.TypefaceSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FragmentEdit extends Fragment {
    View view;
    TextView tvToday;
    EditText etTodayDt;
    long now;
    Date date;
    SimpleDateFormat mFormat = new SimpleDateFormat("yyyy. MM. dd  EE");
    boolean isExist = false;
    Button updateBtn, submitBtn;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_edit, container,false);
        tvToday = (TextView)view.findViewById(R.id.tvToday);
        etTodayDt = (EditText)view.findViewById(R.id.etTodayTD);
        updateBtn = (Button)view.findViewById(R.id.updateBtn);
        submitBtn = (Button)view.findViewById(R.id.submitBtn);


        Typeface typeface = ResourcesCompat.getFont(getActivity(), R.font.comingcoon);
        etTodayDt.setTypeface(typeface);


        //1. 오늘 날짜를 구해서 tvToday에 넣어준다.
        now = System.currentTimeMillis();
        date = new Date(now);
        tvToday.setText(mFormat.format(date));


        //2. 오늘 작성한 감사 일기가 DB에 있다면 보여주고, 수정이 가능하게 한다.

        //TODO : DB에 있으면 isExist -> true, 아니면 false;

        if(isExist == false){
            //오늘 작성한 감사 일기가 없는 경우

            submitBtn.setOnClickListener(new View.OnClickListener() {
                //작성한 일기가 생김
                //TODO : DB에 올리고, UPDATE 버튼이 보이게
                @Override
                public void onClick(View v) {
                    isExist = true;
                    updateBtn.setVisibility(View.VISIBLE);
                    submitBtn.setVisibility(View.GONE);
                }
            });
        }
        else{
            updateBtn.setVisibility(View.VISIBLE);
            submitBtn.setVisibility(View.GONE);

            //editText의 내용이 조금이라도 바뀌었으면 버튼이 submit 버튼으로 변경되어야함. (또는 update버튼)
        }


        //3. 오늘 작성한 감사 일기가 없다면 작성하기 버튼만 보이게 한다.
        return view;
    }



}
