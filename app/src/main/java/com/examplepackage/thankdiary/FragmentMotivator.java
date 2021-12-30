package com.examplepackage.thankdiary;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

public class FragmentMotivator extends Fragment {
    View view;
    JSONArray array;
    JSONObject jsonObject;
    VideoView mVideoView;
    TextView tv;
    long now;
    Date date;
    ImageView bgImg;
    String[] days = {"mon", "tue", "wed", "thu", "fri", "sat", "sun"};
    private ArrayList<String> videoArray = new ArrayList();
    private int count = 0;
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_motivator, container,false);
        bgImg = (ImageView)view.findViewById(R.id.bgimg);
        tv = (TextView)view.findViewById(R.id.tv);
        now = System.currentTimeMillis();
        date = new Date(now);


        //Random 배경 생성
        int randomNum = new Random().nextInt(10);
        int j = getResources().getIdentifier("bgimg"+randomNum, "drawable", getActivity().getPackageName());
        bgImg.setImageResource(j);

        //Random 문구 생성
        JSONObject ret = getJSON("jsons/ments.json");

        try{
            //요일에 맞는 ments 배열 parsing
            array = ret.getJSONArray(days[date.getDay()]);//배열의 이름
            int randomNumForMents = new Random().nextInt(array.length());
            tv.setText(array.getString(randomNumForMents));

        }catch (JSONException e) {
            System.out.println("Array nope!");
            e.printStackTrace();
        }

        return view;
    }

    private JSONObject getJSON(String fileName) {
        String json = "";
        System.out.println(fileName);
        try{
            InputStream is = getActivity().getAssets().open(fileName);

            int fileSize = is.available();
            byte[] buffer = new byte[fileSize];
            is.read(buffer);
            is.close();
            json = new String(buffer,"UTF-8");
            jsonObject = new JSONObject(json);

            return jsonObject;

        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        return null;
    }


}
