package com.examplepackage.thankdiary;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.CalendarMode;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.Executors;

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

        String str = calendarView.getCurrentDate().toString();
        String restr = str.replaceAll("[^0-9]",""); System.out.println(str + " ==> " + restr);
        String year = restr.substring(0,4);
        Integer month = (restr.charAt(4)-'0')*10 + restr.charAt(5)-'0' + 1;

        ret = myDB.getThisMonthDiary(month, Integer.parseInt(year));

        ArrayList<String> results = new ArrayList<String>();

        //TODO: result??? ??? ?????? ????????? ?????? ????????? ????????????
        for(int i=0; i<ret.size(); i++){
            results.add(year+ month + Integer.toString(ret.get(i)));
        }

        new ApiSimulator(results).executeOnExecutor(Executors.newSingleThreadExecutor());


        //???????????? ????????? ?????? ????????? ????????? ????????????, ????????? ????????? ????????? ???????????? ??????
        calendarView.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
                int Year = date.getYear();
                int Month = date.getMonth() + 1;
                int Day = date.getDay();

                choiceDate = String.format("%d. %d. %d", Year, Month, Day);

                // getTodayDiaryList??? ?????? ??????(??????)??? ???????????? ????????? ????????? ??? ????????? ????????????, ????????? null??? ???????????????.
                String choiceDateDiary = myDB.getTodayDiaryList(choiceDate);
                if(choiceDateDiary != null){
                    //????????? ????????? ?????? ????????? ????????? dialog??? ?????? ????????????

                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setTitle(choiceDate);
                    builder.setMessage(choiceDateDiary);
                    builder.setPositiveButton("??????", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });
                    builder.show();
                }
                else {
                    Toast.makeText(getActivity(), choiceDate + "??? ????????? ????????? ?????????", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_month, container,false);
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
