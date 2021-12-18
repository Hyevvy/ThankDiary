package com.example.thankdiary;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import static com.example.thankdiary.R.drawable.switch_selector;

public class FragmentSetting extends Fragment {
    View view;
    TextView one, two, three, four, five;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_setting, container,false);
        one = (TextView)view.findViewById(R.id.setting_first_tab);
        two = (TextView)view.findViewById(R.id.setting_second_tab);
        three = (TextView)view.findViewById(R.id.setting_third_tab);
        four = (TextView)view.findViewById(R.id.setting_fourth_tab);
        five = (TextView)view.findViewById(R.id.setting_fifth_tab);

        final Calendar cal = Calendar.getInstance();

        one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Switch mSwitch = new Switch(getActivity());

                    mSwitch.setTrackResource(switch_selector);
                    mSwitch.setThumbResource(R.drawable.switch_thumb);
                    mSwitch.setText("알림 설정");
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("ALERT ON / OFF");
                builder.setMessage("알림 해제 ------ 알림 시간 설정");
                builder.setView(mSwitch);


                //Switch Button 가운데 정렬
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT); //create a new one
                layoutParams.weight = (float) 1.0;
                layoutParams.gravity = Gravity.CENTER; //this is layout_gravity
                mSwitch.setLayoutParams(layoutParams);

                mSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                       if(isChecked){
                           //True, 이 때만 TimePicker 보임
                           //이 때는 TimePicker안 보임
                           TimePickerDialog dialog = new TimePickerDialog(getActivity(),android.R.style.Theme_Holo_Light_Dialog_NoActionBar, new TimePickerDialog.OnTimeSetListener() {
                               @Override
                               public void onTimeSet(TimePicker timePicker, int hour, int min) {
                                   String msg = String.format("%d 시 %d 분", hour, min);
                                   Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
                               }
                           }, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), true);  //마지막 boolean 값은 시간을 24시간으로 보일지 아닐지
                           dialog.setTitle("원하는 알림 시간을 설정해주세요");
                           dialog.show();
                       }
                       else {
                           mSwitch.setText("알림 해제");
                           //이 때는 TimePicke 안 보임
                           TextView tv = new TextView(getActivity());
                           tv.setText("알림 설정을 해제했습니다.");
                           builder.setView(tv);
                       }
                    }
                }
                );

                builder.setPositiveButton("예", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getActivity(), "예 선택", Toast.LENGTH_SHORT).show();
                    }
                });

                builder.setNegativeButton("아니오", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getActivity(), "아니오 선택", Toast.LENGTH_SHORT).show();
                    }
                                    });
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                }
            });


        two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("MUSIC ON / OFF");
                builder.setMessage("노래를 끌까요?");
                builder.setPositiveButton("예", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getActivity(), "예 선택", Toast.LENGTH_SHORT).show();
                    }
                });
                builder.setNegativeButton("아니오", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getActivity(), "아니오 선택", Toast.LENGTH_SHORT).show();
                    }
                });
                builder.show();
            }
        });


        three.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("문의하기");
                builder.setMessage("문의는 hyevvy@naver.com로 부탁드립니다.");

                builder.setNegativeButton("닫기", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getActivity(), "닫기 선택", Toast.LENGTH_SHORT).show();
                    }
                });
                builder.show();
            }
        });


        four.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("이용약관");
                builder.setMessage("이용약관 페이지로 연결합니다.");

                builder.setNegativeButton("닫기", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getActivity(), "닫기 선택", Toast.LENGTH_SHORT).show();
                    }
                });
                builder.show();
            }
        });

        five.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("개인정보처리방침");
                builder.setMessage("?는 절대 개인 정보를 저장하지 않습니다. 자세한 처리방침은 다음과 같습니다.");

                builder.setNegativeButton("닫기", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getActivity(), "닫기 선택", Toast.LENGTH_SHORT).show();
                    }
                });
                builder.show();
            }
        });

        return view;
    } // onCreateView end

//    private TimePickerDialog.OnTimeSetListener listener = new TimePickerDialog.OnTimeSetListener() {
//
//        @Override
//
//        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
//
//            if(flag == 0) {
//                binding.textBorrow.setTextSize(15);
//                binding.textBorrow.setText(hourOfDay + ":" + minute);
//                str_borrow_time = hourOfDay + ":" + minute;
//                borrow_time = hourOfDay*60+minute;
//            } else if (flag == 1){
//                binding.textReturn.setTextSize(15);
//                binding.textReturn.setText(hourOfDay + ":" + minute);
//                str_return_time = hourOfDay + ":" + minute;
//                return_time = hourOfDay*60+minute;
//            }
//
//
//        }
//
//    };


//    TimePickerDialog mTimePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
//
//        @Override
//        public void onTimeSet(TimePicker timePicker, int i, int i1) {
//            int hour = timePicker.getHour(); // == i
//            int minute = timePicker.getMinute(); // == i1
//        }
//    }, Calendar.HOUR_OF_DAY, Calendar.MINUTE, true).show();


//    private void showTimePicker() {
//        Calendar cal = Calendar.getInstance();
//        int hour = cal.get(Calendar.HOUR_OF_DAY);
//        int min = cal.get(Calendar.MINUTE);
//        TimePickerDialog mTimePickerDialog = new TimePickerDialog(getActivity(), listener, hour, min, true);
//        return mTimePickerDialog;
//    }
//


}
