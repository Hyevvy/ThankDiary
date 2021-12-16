package com.example.thankdiary;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    FragmentSetting fragmentsetting;
    FragmentMonth fragmentMonth;
    FragmentMotivator fragmentMotivator;
    FragmentEdit fragmentEdit;
    FragmentTop fragTop;
    ImageButton homebtn;
    FragmentTransaction fragmentTransaction;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragTop = new FragmentTop();
        fragmentsetting = new FragmentSetting();
        fragmentMonth = new FragmentMonth();
        fragmentEdit= new FragmentEdit();
        fragmentMotivator = new FragmentMotivator();
        homebtn = (ImageButton)findViewById(R.id.homeBtn);

        //HomeBtn이 눌리면 다시 메인화면으로 돌아오게
        homebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().beginTransaction().replace(R.id.container, fragTop).commit();
            }
        });



        getSupportFragmentManager().beginTransaction().replace(R.id.container, fragTop).commit();


        BottomNavigationView bottom_menu = findViewById(R.id.bottom_menu);
        bottom_menu.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()) {
                    case R.id.first_tab:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, fragmentEdit).commit();
                        return true;
                    case R.id.second_tab:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, fragmentMonth).commit();
                        return true;

                    case R.id.third_tab:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, fragmentMotivator).commit();
                        return true;

                    case R.id.fourth_tab:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, fragmentsetting).commit();
                        return true;
                }
                return false;
            }
        });

    }
}