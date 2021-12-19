package com.example.thankdiary;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.CalendarMode;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import com.roomorama.caldroid.CaldroidFragment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.Executors;

import static java.util.Collections.singleton;

public class FragmentMonth extends Fragment {
    View view;
    //CalendarView calendarView;
    String choiceDate;
    DBHelper myDB;
    ArrayList<Integer> ret;
    MaterialCalendarView calendarView;
    EventDecorator dotDecorator;
    @Override
    public void onResume() {
        super.onResume();
        myDB = new DBHelper(getActivity());
        calendarView = (MaterialCalendarView)view.findViewById(R.id.calendarView);
        calendarView.state().edit()
                .setFirstDayOfWeek(Calendar.SUNDAY)
                .setMinimumDate(CalendarDay.from(2021,1,1))
                .setCalendarDisplayMode(CalendarMode.MONTHS)
                .commit();
        calendarView.addDecorators(
                new SaturdayDecorator(),
                new SundayDecorator()
        );
        calendarView.setSelectedDate(CalendarDay.today());

        System.out.println("현재 ㅗ고 있는 날 : " + calendarView.getCurrentDate());
        String str = calendarView.getCurrentDate().toString();
        String restr = str.replaceAll("[^0-9]",""); System.out.println(str + " ==> " + restr);
        String year = restr.substring(0,4);
        Integer month = (restr.charAt(4)-'0')*10 + restr.charAt(5)-'0' + 1;

        System.out.println("year : " + year);
        System.out.println("month : " + month);
        ret = myDB.getThisMonthDiary(month, Integer.parseInt(year));

        System.out.println("현재 ㅗ고 있는 달 : " + month);
        ArrayList<String> results = new ArrayList<String>();

        //TODO: result에 그 달에 일기가 있는 날들을 넣어야함
        for(int i=0; i<ret.size(); i++){
            results.add(year+ month + Integer.toString(ret.get(i)));
            System.out.println("일기가 있는 날 " + ret.get(i));
        }

        new ApiSimulator(results).executeOnExecutor(Executors.newSingleThreadExecutor());


        //사용자가 선택한 날에 일기가 있으면 보여주고, 없으면 없다는 토스트 메시지를 출력
        calendarView.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
                int Year = date.getYear();
                int Month = date.getMonth() + 1;
                int Day = date.getDay();

                choiceDate = String.format("%d. %d. %d", Year, Month, Day);

                // getTodayDiaryList는 받은 인자(날짜)에 해당하는 일기가 있다면 그 내용을 리턴하고, 아니면 null을 리턴합니다.
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

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_month, container,false);
      //  calendarView = (CalendarView) view.findViewById(R.id.calendarView);
        //myDB = new DBHelper(getActivity());

//        calendarView = (MaterialCalendarView)view.findViewById(R.id.calendarView);
//        calendarView.state().edit()
//                .setFirstDayOfWeek(Calendar.SUNDAY)
//                .setMinimumDate(CalendarDay.from(2021,1,1))
//                .setCalendarDisplayMode(CalendarMode.MONTHS)
//                .commit();
//        calendarView.addDecorators(
//                new SaturdayDecorator(),
//                new SundayDecorator()
//        );
//        calendarView.setSelectedDate(CalendarDay.today());
//
//        System.out.println("현재 ㅗ고 있는 날 : " + calendarView.getCurrentDate());
//        String str = calendarView.getCurrentDate().toString();
//        String restr = str.replaceAll("[^0-9]",""); System.out.println(str + " ==> " + restr);
//        String year = restr.substring(0,4);
//        Integer month = (restr.charAt(4)-'0')*10 + restr.charAt(5)-'0' + 1;
//
//        System.out.println("year : " + year);
//        System.out.println("month : " + month);
//        ret = myDB.getThisMonthDiary(month, Integer.parseInt(year));
//
//        System.out.println("현재 ㅗ고 있는 달 : " + month);
//        ArrayList<String> results = new ArrayList<String>();
//
//        //TODO: result에 그 달에 일기가 있는 날들을 넣어야함
//        for(int i=0; i<ret.size(); i++){
//            results.add(year+ month + Integer.toString(ret.get(i)));
//            System.out.println("일기가 있는 날 " + ret.get(i));
//        }
//
//        new ApiSimulator(results).executeOnExecutor(Executors.newSingleThreadExecutor());
//
//
//        //사용자가 선택한 날에 일기가 있으면 보여주고, 없으면 없다는 토스트 메시지를 출력
//        calendarView.setOnDateChangedListener(new OnDateSelectedListener() {
//            @Override
//            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
//                int Year = date.getYear();
//                int Month = date.getMonth() + 1;
//                int Day = date.getDay();
//
//                choiceDate = String.format("%d. %d. %d", Year, Month, Day);
//
//               // getTodayDiaryList는 받은 인자(날짜)에 해당하는 일기가 있다면 그 내용을 리턴하고, 아니면 null을 리턴합니다.
//                String choiceDateDiary = myDB.getTodayDiaryList(choiceDate);
//                if(choiceDateDiary != null){
//                  //선택한 날짜에 맞는 일기가 있으면 dialog를 통해 보여준다
//
//                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
//                    builder.setTitle(choiceDate);
//                    builder.setMessage(choiceDateDiary);
//                    builder.setPositiveButton("닫기", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                        }
//                    });
//                    builder.show();
//                }
//                else {
//                    Toast.makeText(getActivity(), choiceDate + "에 작성한 일기가 없어요", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });

        return view;
    }

    private class ApiSimulator extends AsyncTask<Void, Void, List<CalendarDay>> {
        ArrayList<String> Time_Result;

        ApiSimulator(ArrayList<String> Time_Result){
            this.Time_Result = Time_Result;
        }

        protected List<CalendarDay> doInBackground(@NonNull Void... voids) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            Calendar calendar = Calendar.getInstance();
            ArrayList<CalendarDay> dates = new ArrayList<>();


            for(int i = 0 ; i < Time_Result.size() ; i ++){
                System.out.println("TIme Result : "+Time_Result.get(i));
                String str = Time_Result.get(i);
                String year = str.substring(0,4);
                String month = str.substring(4,6);
                String date = str.substring(6,8);

                calendar.set(Integer.parseInt(year), Integer.parseInt(month)-1, Integer.parseInt(date));
                CalendarDay day = CalendarDay.from(calendar);
                dates.add(day);
            }


            return dates;
        }

        @Override
        protected void onPostExecute(@NonNull List<CalendarDay> calendarDays) {
            super.onPostExecute(calendarDays);

            calendarView.addDecorator(new EventDecorator(Color.RED, calendarDays,getActivity()));
        }
    }
}
