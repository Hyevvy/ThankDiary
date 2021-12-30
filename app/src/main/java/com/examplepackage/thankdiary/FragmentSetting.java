package com.examplepackage.thankdiary;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.Calendar;

public class FragmentSetting extends Fragment {
    View view;
    Switch one;
    TextView two, three, four, five;
    FragmentSettingListener listener;
    private AlarmManager alarmManager;

    public interface FragmentSettingListener{
        void onInputSettingSend(Boolean input);
    }

    public void onAttach(@NonNull Context context){
        super.onAttach(context);
        if(context instanceof FragmentSettingListener){
            listener = (FragmentSettingListener)context;
        }
        else {
            throw new RuntimeException(context.toString() + "must implement FragmentSettingListener");
        }
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_setting, container,false);
        one = (Switch)view.findViewById(R.id.setting_first_tab);
        two = (TextView)view.findViewById(R.id.setting_second_tab);
        three = (TextView)view.findViewById(R.id.setting_third_tab);
        four = (TextView)view.findViewById(R.id.setting_fourth_tab);
        five = (TextView)view.findViewById(R.id.setting_fifth_tab);


        final Calendar cal = Calendar.getInstance();
        one.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    //True, 이 때만 TimePicker 보임
                    //이 때는 TimePicker안 보임
                    TimePickerDialog dialog = new TimePickerDialog(getActivity(),android.R.style.Theme_Holo_Light_Dialog_NoActionBar, new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker timePicker, int hour, int min) {
                            String msg = String.format("%d 시 %d 분", hour, min);
                            Calendar reserveTime = Calendar.getInstance();
                            reserveTime.setTimeInMillis(System.currentTimeMillis());
                            reserveTime.set(Calendar.SECOND, 0);
                            reserveTime.set(Calendar.MINUTE, min);
                            reserveTime.set(Calendar.HOUR_OF_DAY, hour);
                            Toast.makeText(getActivity(), msg + "에 일기 알림을 드릴게요", Toast.LENGTH_SHORT).show();
                            startAlarm(reserveTime);
                        }
                    }, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), true);  //마지막 boolean 값은 시간을 24시간으로 보일지 아닐지
                    dialog.setTitle("원하는 알림 시간을 설정해주세요");
                    dialog.show();
                }
                else {
                    Toast.makeText(getActivity(), "알림 설정이 해제되었습니다", Toast.LENGTH_SHORT).show();
                    cancelAlarm();
                }
            }
            });


        //배경음악 ON / OFF 설정
        two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("MUSIC ON / OFF");
                builder.setPositiveButton("음악 끄기", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        listener.onInputSettingSend(true);
                        //Toast.makeText(getActivity(), "음악 끄기", Toast.LENGTH_SHORT).show();
                    }
                });
                builder.setNegativeButton("음악 켜기", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        listener.onInputSettingSend(false);
                        //Toast.makeText(getActivity(), "음악 켜기", Toast.LENGTH_SHORT).show();
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
                builder.setMessage("hyevvy@naver.com로 문의 부탁드립니다.");

                builder.setNegativeButton("닫기", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which){
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
                builder.setMessage("'매일을 소중히'는 절대 개인 정보를 저장하지 않습니다. 자세한 이용약관은 다음과 같습니다." +
                        "< 매일을 소중히 >은(는) 「개인정보 보호법」 제30조에 따라 정보주체의 개인정보를 보호하고 이와 관련한 고충을 신속하고 원활하게 처리할 수 있도록 하기 위하여 다음과 같이 개인정보 처리방침을 수립·공개합니다.\n" +
                        "\n" +
                        "○ 이 개인정보처리방침은 2021년 12월 24부터 적용됩니다.\n" +
                        "\n" +
                        "\n" +
                        "제1조(개인정보의 처리 목적)\n" +
                        "\n" +
                        "< 매일을 소중히 >은(는) 다음의 목적을 위하여 개인정보를 처리합니다. 처리하고 있는 개인정보는 다음의 목적 이외의 용도로는 이용되지 않으며 이용 목적이 변경되는 경우에는 「개인정보 보호법」 제18조에 따라 별도의 동의를 받는 등 필요한 조치를 이행할 예정입니다.\n" +
                        "\n" +
                        "6. 기타\n" +
                        "\n" +
                        "이용하지않음 등을 목적으로 개인정보를 처리합니다.\n" +
                        "\n" +
                        "\n" +
                        "\n" +
                        "제2조(개인정보의 처리 및 보유 기간)\n" +
                        "\n" +
                        "① < 매일을 소중히 >은(는) 법령에 따른 개인정보 보유·이용기간 또는 정보주체로부터 개인정보를 수집 시에 동의받은 개인정보 보유·이용기간 내에서 개인정보를 처리·보유합니다.\n" +
                        "\n" +
                        "② 각각의 개인정보 처리 및 보유 기간은 다음과 같습니다.\n" +
                        "\n" +
                        "1.<기타>\n" +
                        "<기타>와 관련한 개인정보는 수집.이용에 관한 동의일로부터<지체없이 파기>까지 위 이용목적을 위하여 보유.이용됩니다.\n" +
                        "보유근거 : 보유하지않음\n" +
                        "관련법령 :\n" +
                        "예외사유 :\n" +
                        "\n" +
                        "\n" +
                        "제3조(정보주체와 법정대리인의 권리·의무 및 그 행사방법)\n" +
                        "\n" +
                        "\n" +
                        "\n" +
                        "① 정보주체는 매일을 소중히에 대해 언제든지 개인정보 열람·정정·삭제·처리정지 요구 등의 권리를 행사할 수 있습니다.\n" +
                        "\n" +
                        "② 제1항에 따른 권리 행사는매일을 소중히에 대해 「개인정보 보호법」 시행령 제41조제1항에 따라 서면, 전자우편, 모사전송(FAX) 등을 통하여 하실 수 있으며 매일을 소중히은(는) 이에 대해 지체 없이 조치하겠습니다.\n" +
                        "\n" +
                        "③ 제1항에 따른 권리 행사는 정보주체의 법정대리인이나 위임을 받은 자 등 대리인을 통하여 하실 수 있습니다.이 경우 “개인정보 처리 방법에 관한 고시(제2020-7호)” 별지 제11호 서식에 따른 위임장을 제출하셔야 합니다.\n" +
                        "\n" +
                        "④ 개인정보 열람 및 처리정지 요구는 「개인정보 보호법」 제35조 제4항, 제37조 제2항에 의하여 정보주체의 권리가 제한 될 수 있습니다.\n" +
                        "\n" +
                        "⑤ 개인정보의 정정 및 삭제 요구는 다른 법령에서 그 개인정보가 수집 대상으로 명시되어 있는 경우에는 그 삭제를 요구할 수 없습니다.\n" +
                        "\n" +
                        "⑥ 매일을 소중히은(는) 정보주체 권리에 따른 열람의 요구, 정정·삭제의 요구, 처리정지의 요구 시 열람 등 요구를 한 자가 본인이거나 정당한 대리인인지를 확인합니다.\n" +
                        "\n" +
                        "\n" +
                        "\n" +
                        "제4조(처리하는 개인정보의 항목 작성)\n" +
                        "\n" +
                        "① < 매일을 소중히 >은(는) 다음의 개인정보 항목을 처리하고 있습니다.\n" +
                        "\n" +
                        "1< 기타 >\n" +
                        "필수항목 : 보유하지않음\n" +
                        "선택항목 :\n" +
                        "\n" +
                        "\n" +
                        "제5조(개인정보의 파기)\n" +
                        "\n" +
                        "\n" +
                        "① < 매일을 소중히 > 은(는) 개인정보 보유기간의 경과, 처리목적 달성 등 개인정보가 불필요하게 되었을 때에는 지체없이 해당 개인정보를 파기합니다.\n" +
                        "\n" +
                        "② 정보주체로부터 동의받은 개인정보 보유기간이 경과하거나 처리목적이 달성되었음에도 불구하고 다른 법령에 따라 개인정보를 계속 보존하여야 하는 경우에는, 해당 개인정보를 별도의 데이터베이스(DB)로 옮기거나 보관장소를 달리하여 보존합니다.\n" +
                        "1. 법령 근거 :\n" +
                        "2. 보존하는 개인정보 항목 : 계좌정보, 거래날짜\n" +
                        "\n" +
                        "③ 개인정보 파기의 절차 및 방법은 다음과 같습니다.\n" +
                        "1. 파기절차\n" +
                        "< 매일을 소중히 > 은(는) 파기 사유가 발생한 개인정보를 선정하고, < 매일을 소중히 > 의 개인정보 보호책임자의 승인을 받아 개인정보를 파기합니다.\n" +
                        "\n" +
                        "2. 파기방법\n" +
                        "\n" +
                        "전자적 파일 형태의 정보는 기록을 재생할 수 없는 기술적 방법을 사용합니다.\n" +
                        "\n" +
                        "종이에 출력된 개인정보는 분쇄기로 분쇄하거나 소각을 통하여 파기합니다.\n" +
                        "\n" +
                        "제6조(개인정보의 안전성 확보 조치)\n" +
                        "\n" +
                        "< 매일을 소중히 >은(는) 개인정보의 안전성 확보를 위해 다음과 같은 조치를 취하고 있습니다.\n" +
                        "\n" +
                        "1. 정기적인 자체 감사 실시\n" +
                        "개인정보 취급 관련 안정성 확보를 위해 정기적(분기 1회)으로 자체 감사를 실시하고 있습니다.\n" +
                        "\n" +
                        "2. 개인정보에 대한 접근 제한\n" +
                        "개인정보를 처리하는 데이터베이스시스템에 대한 접근권한의 부여,변경,말소를 통하여 개인정보에 대한 접근통제를 위하여 필요한 조치를 하고 있으며 침입차단시스템을 이용하여 외부로부터의 무단 접근을 통제하고 있습니다.\n" +
                        "\n" +
                        "\n" +
                        "\n" +
                        "\n" +
                        "제7조(개인정보 자동 수집 장치의 설치•운영 및 거부에 관한 사항)\n" +
                        "\n" +
                        "\n" +
                        "\n" +
                        "매일을 소중히 은(는) 정보주체의 이용정보를 저장하고 수시로 불러오는 ‘쿠키(cookie)’를 사용하지 않습니다.\n" +
                        "\n" +
                        "제8조 (개인정보 보호책임자)\n" +
                        "\n" +
                        "① 매일을 소중히 은(는) 개인정보 처리에 관한 업무를 총괄해서 책임지고, 개인정보 처리와 관련한 정보주체의 불만처리 및 피해구제 등을 위하여 아래와 같이 개인정보 보호책임자를 지정하고 있습니다.\n" +
                        "\n" +
                        "▶ 개인정보 보호 담당부서\n" +
                        "부서명 :\n" +
                        "담당자 :\n" +
                        "연락처 :hyevvy@naver.com ,\n" +
                        "② 정보주체께서는 매일을 소중히 의 서비스(또는 사업)을 이용하시면서 발생한 모든 개인정보 보호 관련 문의, 불만처리, 피해구제 등에 관한 사항을 개인정보 보호책임자 및 담당부서로 문의하실 수 있습니다. 매일을 소중히 은(는) 정보주체의 문의에 대해 지체 없이 답변 및 처리해드릴 것입니다.\n" +
                        "\n" +
                        "제9조(개인정보 열람청구)\n" +
                        "정보주체는 ｢개인정보 보호법｣ 제35조에 따른 개인정보의 열람 청구를 아래의 부서에 할 수 있습니다.\n" +
                        "< 매일을 소중히 >은(는) 정보주체의 개인정보 열람청구가 신속하게 처리되도록 노력하겠습니다.\n" +
                        "\n" +
                        "▶ 개인정보 열람청구 접수·처리 부서\n" +
                        "부서명 : 작성자\n" +
                        "담당자 : 작성자\n" +
                        "연락처 : hyevvy@naver.com,\n" +
                        "\n" +
                        "\n" +
                        "제10조(권익침해 구제방법)\n" +
                        "\n" +
                        "\n" +
                        "\n" +
                        "정보주체는 개인정보침해로 인한 구제를 받기 위하여 개인정보분쟁조정위원회, 한국인터넷진흥원 개인정보침해신고센터 등에 분쟁해결이나 상담 등을 신청할 수 있습니다. 이 밖에 기타 개인정보침해의 신고, 상담에 대하여는 아래의 기관에 문의하시기 바랍니다.\n" +
                        "\n" +
                        "1. 개인정보분쟁조정위원회 : (국번없이) 1833-6972 (www.kopico.go.kr)\n" +
                        "2. 개인정보침해신고센터 : (국번없이) 118 (privacy.kisa.or.kr)\n" +
                        "3. 대검찰청 : (국번없이) 1301 (www.spo.go.kr)\n" +
                        "4. 경찰청 : (국번없이) 182 (ecrm.cyber.go.kr)\n" +
                        "\n" +
                        "「개인정보보호법」제35조(개인정보의 열람), 제36조(개인정보의 정정·삭제), 제37조(개인정보의 처리정지 등)의 규정에 의한 요구에 대 하여 공공기관의 장이 행한 처분 또는 부작위로 인하여 권리 또는 이익의 침해를 받은 자는 행정심판법이 정하는 바에 따라 행정심판을 청구할 수 있습니다.\n" +
                        "\n" +
                        "※ 행정심판에 대해 자세한 사항은 중앙행정심판위원회(www.simpan.go.kr) 홈페이지를 참고하시기 바랍니다.\n" +
                        "\n" +
                        "제11조(개인정보 처리방침 변경)\n" +
                        "\n" +
                        "\n" +
                        "① 이 개인정보처리방침은 2021년 12월 24부터 적용됩니다.\n" +
                        "\n");


                builder.setNegativeButton("닫기", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
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
                builder.setMessage("'매일을 소중히'는 절대 개인 정보를 저장하지 않습니다. 자세한 처리방침은 다음과 같습니다." +
                        "< 매일을 소중히 >은(는) 「개인정보 보호법」 제30조에 따라 정보주체의 개인정보를 보호하고 이와 관련한 고충을 신속하고 원활하게 처리할 수 있도록 하기 위하여 다음과 같이 개인정보 처리방침을 수립·공개합니다.\n" +
                        "\n" +
                        "○ 이 개인정보처리방침은 2021년 12월 24부터 적용됩니다.\n" +
                        "\n" +
                        "\n" +
                        "제1조(개인정보의 처리 목적)\n" +
                        "\n" +
                        "< 매일을 소중히 >은(는) 다음의 목적을 위하여 개인정보를 처리합니다. 처리하고 있는 개인정보는 다음의 목적 이외의 용도로는 이용되지 않으며 이용 목적이 변경되는 경우에는 「개인정보 보호법」 제18조에 따라 별도의 동의를 받는 등 필요한 조치를 이행할 예정입니다.\n" +
                        "\n" +
                        "6. 기타\n" +
                        "\n" +
                        "이용하지않음 등을 목적으로 개인정보를 처리합니다.\n" +
                        "\n" +
                        "\n" +
                        "\n" +
                        "제2조(개인정보의 처리 및 보유 기간)\n" +
                        "\n" +
                        "① < 매일을 소중히 >은(는) 법령에 따른 개인정보 보유·이용기간 또는 정보주체로부터 개인정보를 수집 시에 동의받은 개인정보 보유·이용기간 내에서 개인정보를 처리·보유합니다.\n" +
                        "\n" +
                        "② 각각의 개인정보 처리 및 보유 기간은 다음과 같습니다.\n" +
                        "\n" +
                        "1.<기타>\n" +
                        "<기타>와 관련한 개인정보는 수집.이용에 관한 동의일로부터<지체없이 파기>까지 위 이용목적을 위하여 보유.이용됩니다.\n" +
                        "보유근거 : 보유하지않음\n" +
                        "관련법령 :\n" +
                        "예외사유 :\n" +
                        "\n" +
                        "\n" +
                        "제3조(정보주체와 법정대리인의 권리·의무 및 그 행사방법)\n" +
                        "\n" +
                        "\n" +
                        "\n" +
                        "① 정보주체는 매일을 소중히에 대해 언제든지 개인정보 열람·정정·삭제·처리정지 요구 등의 권리를 행사할 수 있습니다.\n" +
                        "\n" +
                        "② 제1항에 따른 권리 행사는매일을 소중히에 대해 「개인정보 보호법」 시행령 제41조제1항에 따라 서면, 전자우편, 모사전송(FAX) 등을 통하여 하실 수 있으며 매일을 소중히은(는) 이에 대해 지체 없이 조치하겠습니다.\n" +
                        "\n" +
                        "③ 제1항에 따른 권리 행사는 정보주체의 법정대리인이나 위임을 받은 자 등 대리인을 통하여 하실 수 있습니다.이 경우 “개인정보 처리 방법에 관한 고시(제2020-7호)” 별지 제11호 서식에 따른 위임장을 제출하셔야 합니다.\n" +
                        "\n" +
                        "④ 개인정보 열람 및 처리정지 요구는 「개인정보 보호법」 제35조 제4항, 제37조 제2항에 의하여 정보주체의 권리가 제한 될 수 있습니다.\n" +
                        "\n" +
                        "⑤ 개인정보의 정정 및 삭제 요구는 다른 법령에서 그 개인정보가 수집 대상으로 명시되어 있는 경우에는 그 삭제를 요구할 수 없습니다.\n" +
                        "\n" +
                        "⑥ 매일을 소중히은(는) 정보주체 권리에 따른 열람의 요구, 정정·삭제의 요구, 처리정지의 요구 시 열람 등 요구를 한 자가 본인이거나 정당한 대리인인지를 확인합니다.\n" +
                        "\n" +
                        "\n" +
                        "\n" +
                        "제4조(처리하는 개인정보의 항목 작성)\n" +
                        "\n" +
                        "① < 매일을 소중히 >은(는) 다음의 개인정보 항목을 처리하고 있습니다.\n" +
                        "\n" +
                        "1< 기타 >\n" +
                        "필수항목 : 보유하지않음\n" +
                        "선택항목 :\n" +
                        "\n" +
                        "\n" +
                        "제5조(개인정보의 파기)\n" +
                        "\n" +
                        "\n" +
                        "① < 매일을 소중히 > 은(는) 개인정보 보유기간의 경과, 처리목적 달성 등 개인정보가 불필요하게 되었을 때에는 지체없이 해당 개인정보를 파기합니다.\n" +
                        "\n" +
                        "② 정보주체로부터 동의받은 개인정보 보유기간이 경과하거나 처리목적이 달성되었음에도 불구하고 다른 법령에 따라 개인정보를 계속 보존하여야 하는 경우에는, 해당 개인정보를 별도의 데이터베이스(DB)로 옮기거나 보관장소를 달리하여 보존합니다.\n" +
                        "1. 법령 근거 :\n" +
                        "2. 보존하는 개인정보 항목 : 계좌정보, 거래날짜\n" +
                        "\n" +
                        "③ 개인정보 파기의 절차 및 방법은 다음과 같습니다.\n" +
                        "1. 파기절차\n" +
                        "< 매일을 소중히 > 은(는) 파기 사유가 발생한 개인정보를 선정하고, < 매일을 소중히 > 의 개인정보 보호책임자의 승인을 받아 개인정보를 파기합니다.\n" +
                        "\n" +
                        "2. 파기방법\n" +
                        "\n" +
                        "전자적 파일 형태의 정보는 기록을 재생할 수 없는 기술적 방법을 사용합니다.\n" +
                        "\n" +
                        "종이에 출력된 개인정보는 분쇄기로 분쇄하거나 소각을 통하여 파기합니다.\n" +
                        "\n" +
                        "제6조(개인정보의 안전성 확보 조치)\n" +
                        "\n" +
                        "< 매일을 소중히 >은(는) 개인정보의 안전성 확보를 위해 다음과 같은 조치를 취하고 있습니다.\n" +
                        "\n" +
                        "1. 정기적인 자체 감사 실시\n" +
                        "개인정보 취급 관련 안정성 확보를 위해 정기적(분기 1회)으로 자체 감사를 실시하고 있습니다.\n" +
                        "\n" +
                        "2. 개인정보에 대한 접근 제한\n" +
                        "개인정보를 처리하는 데이터베이스시스템에 대한 접근권한의 부여,변경,말소를 통하여 개인정보에 대한 접근통제를 위하여 필요한 조치를 하고 있으며 침입차단시스템을 이용하여 외부로부터의 무단 접근을 통제하고 있습니다.\n" +
                        "\n" +
                        "\n" +
                        "\n" +
                        "\n" +
                        "제7조(개인정보 자동 수집 장치의 설치•운영 및 거부에 관한 사항)\n" +
                        "\n" +
                        "\n" +
                        "\n" +
                        "매일을 소중히 은(는) 정보주체의 이용정보를 저장하고 수시로 불러오는 ‘쿠키(cookie)’를 사용하지 않습니다.\n" +
                        "\n" +
                        "제8조 (개인정보 보호책임자)\n" +
                        "\n" +
                        "① 매일을 소중히 은(는) 개인정보 처리에 관한 업무를 총괄해서 책임지고, 개인정보 처리와 관련한 정보주체의 불만처리 및 피해구제 등을 위하여 아래와 같이 개인정보 보호책임자를 지정하고 있습니다.\n" +
                        "\n" +
                        "▶ 개인정보 보호 담당부서\n" +
                        "부서명 :\n" +
                        "담당자 :\n" +
                        "연락처 :hyevvy@naver.com ,\n" +
                        "② 정보주체께서는 매일을 소중히 의 서비스(또는 사업)을 이용하시면서 발생한 모든 개인정보 보호 관련 문의, 불만처리, 피해구제 등에 관한 사항을 개인정보 보호책임자 및 담당부서로 문의하실 수 있습니다. 매일을 소중히 은(는) 정보주체의 문의에 대해 지체 없이 답변 및 처리해드릴 것입니다.\n" +
                        "\n" +
                        "제9조(개인정보 열람청구)\n" +
                        "정보주체는 ｢개인정보 보호법｣ 제35조에 따른 개인정보의 열람 청구를 아래의 부서에 할 수 있습니다.\n" +
                        "< 매일을 소중히 >은(는) 정보주체의 개인정보 열람청구가 신속하게 처리되도록 노력하겠습니다.\n" +
                        "\n" +
                        "▶ 개인정보 열람청구 접수·처리 부서\n" +
                        "부서명 : 작성자\n" +
                        "담당자 : 작성자\n" +
                        "연락처 : hyevvy@naver.com,\n" +
                        "\n" +
                        "\n" +
                        "제10조(권익침해 구제방법)\n" +
                        "\n" +
                        "\n" +
                        "\n" +
                        "정보주체는 개인정보침해로 인한 구제를 받기 위하여 개인정보분쟁조정위원회, 한국인터넷진흥원 개인정보침해신고센터 등에 분쟁해결이나 상담 등을 신청할 수 있습니다. 이 밖에 기타 개인정보침해의 신고, 상담에 대하여는 아래의 기관에 문의하시기 바랍니다.\n" +
                        "\n" +
                        "1. 개인정보분쟁조정위원회 : (국번없이) 1833-6972 (www.kopico.go.kr)\n" +
                        "2. 개인정보침해신고센터 : (국번없이) 118 (privacy.kisa.or.kr)\n" +
                        "3. 대검찰청 : (국번없이) 1301 (www.spo.go.kr)\n" +
                        "4. 경찰청 : (국번없이) 182 (ecrm.cyber.go.kr)\n" +
                        "\n" +
                        "「개인정보보호법」제35조(개인정보의 열람), 제36조(개인정보의 정정·삭제), 제37조(개인정보의 처리정지 등)의 규정에 의한 요구에 대 하여 공공기관의 장이 행한 처분 또는 부작위로 인하여 권리 또는 이익의 침해를 받은 자는 행정심판법이 정하는 바에 따라 행정심판을 청구할 수 있습니다.\n" +
                        "\n" +
                        "※ 행정심판에 대해 자세한 사항은 중앙행정심판위원회(www.simpan.go.kr) 홈페이지를 참고하시기 바랍니다.\n" +
                        "\n" +
                        "제11조(개인정보 처리방침 변경)\n" +
                        "\n" +
                        "\n" +
                        "① 이 개인정보처리방침은 2021년 12월 24부터 적용됩니다.\n" +
                        "\n");

                builder.setNegativeButton("닫기", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                builder.show();
            }
        });

        return view;
    } // onCreateView end


    private void startAlarm(Calendar c){
        AlarmManager alarmManager = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(getActivity(), AlertReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getActivity(), 1, intent , 0);
        if(c.before(Calendar.getInstance())){
            c.add(Calendar.DATE, 1);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), pendingIntent);
        }
    }

    private void cancelAlarm(){
        AlarmManager alarmManager = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(getActivity(), AlertReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getActivity(), 1, intent, 0);
        alarmManager.cancel(pendingIntent);

    }
    
}
