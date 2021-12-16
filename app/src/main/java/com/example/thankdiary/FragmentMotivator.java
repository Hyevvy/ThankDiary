package com.example.thankdiary;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

public class FragmentMotivator extends Fragment {
    View view;
    JSONArray Array;
    JSONObject jsonObject;
    VideoView mVideoView;
    private ArrayList<String> videoArray = new ArrayList();
    private int count = 0;
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_motivator, container,false);

        //Background Video 설정
        videoArray.add("android.resource://" + getActivity().getPackageName() + "/raw/ocean");
        videoArray.add("android.resource://" + getActivity().getPackageName() + "/raw/ocean");
        videoArray.add("android.resource://" + getActivity().getPackageName() + "/raw/ocean");
        count = 0;


        mVideoView = (VideoView) view.findViewById(R.id.screenVideoView);
        Uri uri = Uri.parse("android.resource://" + getActivity().getPackageName() + "/raw/ocean");
        mVideoView.setVideoURI(uri);

        //Background Video 무한 재생
        MediaPlayer.OnCompletionListener mComplete = new MediaPlayer.OnCompletionListener() {

            @Override
            public void onCompletion(MediaPlayer mp) {
                //재생할 비디오가 남아있을 경우
                if (videoArray.size() > count) {

                    Uri video1 = Uri.parse(videoArray.get(count).toString());

                    count++;
                    mVideoView.setVideoURI(video1);

                    mVideoView.start();

                }
            }
        };

        //리스너 등록
        mVideoView.setOnCompletionListener(mComplete);
        //비디오 시작
        mVideoView.start();



//        // 리스너 등록
//        mVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
//            @Override
//            public void onPrepared(MediaPlayer mp) {
//                // 준비 완료되면 비디오 재생
//                mp.start();
//            }
//        });











        JSONObject ret = getJSON("jsons/ments.json");
        try{
            Array = ret.getJSONArray("tue");//배열의 이름


        }catch (JSONException e) {
            System.out.println("Array nope!");
            e.printStackTrace();
        }
        System.out.println(Array.length());
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
