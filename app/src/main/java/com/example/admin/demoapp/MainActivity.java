package com.example.admin.demoapp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.SystemClock;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Adapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity implements MySpeechRecongnizer.onResultsReady {
    private List<String> listPer;

    private MySpeechRecongnizer mySpeechRecongnizer;

    private TeamFootball teamFootballOne;
    private TeamFootball teamFootballTwo;

    private List<FootballPlayer> footballPlayers;
    private List<FootballPlayer> footballPlayersH;

    private TextView tvRecongnizer, tvTime;


    TimerTask timerTask;
    Timer timer;
    SimpleDateFormat sdf = new SimpleDateFormat("mm:ss aaa");
    private long startTime = 0L;
    private Handler myHandler = new Handler();
    long timeInMillies = 0L;
    long timeSwap = 0L;
    long finalTime = 0L;

    private List<ItemData> list;
    private AdapterInfor adapter;
    private ListView listView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvRecongnizer = findViewById(R.id.tv_recongnizer);
        tvTime = findViewById(R.id.tv_time);
        listView = findViewById(R.id.lv_item);

        listPer = new ArrayList<>();
        listPer.add("android.permission.RECORD_AUDIO");
        footballPlayers = new ArrayList<>();
        footballPlayersH = new ArrayList<>();
        list = new ArrayList<>();
        adapter = new AdapterInfor(list);
        listView.setAdapter(adapter);

        //Danh sách cầu thủ đội 1
        footballPlayers.add(new FootballPlayer("Đoàn", "Văn Hậu", 1));
        footballPlayers.add(new FootballPlayer("Nguyễn", "Quang Hải", 2));
        footballPlayers.add(new FootballPlayer("Đỗ", "Duy Mạnh", 3));
        footballPlayers.add(new FootballPlayer("Phạm", "Thành Lương", 8));
        footballPlayers.add(new FootballPlayer("Nguyễn", "Văn Quyết", 10));
        footballPlayers.add(new FootballPlayer("Nguyễn", "Quang Hải", 6));
        footballPlayers.add(new FootballPlayer("Trần", " Đình Trọng", 4));
        footballPlayers.add(new FootballPlayer("Nguyễn", "Mạnh Tiến", 5));
        footballPlayers.add(new FootballPlayer("Trần", "Văn Kiên", 11));
        footballPlayers.add(new FootballPlayer("Phạm", "Đức Huy", 16));
        footballPlayers.add(new FootballPlayer("Nguyễn", "Văn Công", 20));
        teamFootballOne = new TeamFootball("Ha Noi-FC", footballPlayers);


        //Danh sách cầu thủ đội 2
        footballPlayersH.add(new FootballPlayer("Lê", "Văn Sơn", 2));
        footballPlayersH.add(new FootballPlayer("Phạm", "Đăng Tuấn", 4));
        footballPlayersH.add(new FootballPlayer("Lương", "Xuân Trường", 3));
        footballPlayersH.add(new FootballPlayer("Nguyễn", "Văn Toàn", 8));
        footballPlayersH.add(new FootballPlayer("Nguyễn", "Công Phượng", 10));
        footballPlayersH.add(new FootballPlayer("Phan ", "Thanh Hậu", 12));
        footballPlayersH.add(new FootballPlayer("Vũ", "Văn Thanh", 4));
        footballPlayersH.add(new FootballPlayer("Hoàng", "Thanh Tùng", 18));
        footballPlayersH.add(new FootballPlayer("Trần", "Thanh Sơn", 19));
        footballPlayersH.add(new FootballPlayer("Châu", "Ngọc Quang", 24));
        footballPlayersH.add(new FootballPlayer("Trần Hữu ", " Đông Triều", 5));

        teamFootballTwo = new TeamFootball("HAGL-FC", footballPlayersH);



//        Gson gson = new Gson();
//        String json = gson.toJson(teamFootballOne);
//        Log.d("XX", "onCreate: " + json);



//        String json2 = gson.toJson(teamFootballTwo);
//        Log.d("XX", "onCreate2==: " + json);

          checkPermissonVoice();




    }
    private boolean isContainExactWord(String fullString, String partWord){
        String pattern = "\\b"+partWord+"\\b";
        Pattern p=Pattern.compile(pattern);
        Matcher m=p.matcher(fullString);
        return m.find();



    }
    public void initVoice() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mySpeechRecongnizer = new MySpeechRecongnizer(MainActivity.this, MainActivity.this);
                tvRecongnizer.setText("Speech To Text");
            }
        });

//        timerTask = new TimerTask() {
//            @Override
//            public void run() {
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                       Calendar calendar = Calendar.getInstance();
//
//                        String value = sdf.format(calendar.getTime());
//                        tvTime.setText(value);
//                    }
//                });
//
//
//            }
//        };
//        timer = new Timer();
//        timer.schedule(timerTask, 0, 1000);

        startTime = SystemClock.uptimeMillis();
        myHandler.postDelayed(updateTimerMethod, 0);


    }

    private Runnable updateTimerMethod = new Runnable() {

        public void run() {
            timeInMillies = SystemClock.uptimeMillis() - startTime;
            finalTime = timeSwap + timeInMillies;

            int seconds = (int) (finalTime / 1000);
            int minutes = seconds / 60;
            seconds = seconds % 60;
            int milliseconds = (int) (finalTime % 1000);
            tvTime.setText("" + minutes + ":" + String.format("%02d", seconds));
            //+ ":" + String.format("%03d", milliseconds)
            myHandler.postDelayed(this, 0);
        }

    };


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 10) {
            if (Utils.checkPermisson(MainActivity.this, listPer)) {
                initVoice();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 100) {
            for (int grant : grantResults) {
                if (grant == PackageManager.PERMISSION_GRANTED) {//neu duoc chap thuan
                    initVoice();
                } else {
                    Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.parse("package:" + getPackageName()));
                    startActivityForResult(intent, 10);
                }
            }
        }

    }

    public void checkPermissonVoice() {
        //// TODO: 4/17/18 check permission RECORD_AUDIO
        if (Utils.checkPermisson(this, listPer)) {
            initVoice();
        }
    }

    @Override
    public void onResults(List<String> results) {

        if (null != results) {
            Toast.makeText(MainActivity.this, "text= " + results.get(0), Toast.LENGTH_LONG).show();
            String name = results.get(0);
            for (FootballPlayer footballPlayer : teamFootballOne.getList()) {
                if (name.contains(footballPlayer.getLastName())) {
                    ItemData itemData = new ItemData(footballPlayer.getFirstName(), footballPlayer.getLastName(), footballPlayer.getClothers_number(), teamFootballOne.getName(), tvTime.getText().toString());
                    list.add(itemData);
                    adapter.notifyDataSetChanged();
                }
            }
            for (FootballPlayer footballPlayer : teamFootballTwo.getList()) {
                if (name.contains(footballPlayer.getLastName())) {
                    ItemData itemData = new ItemData(footballPlayer.getFirstName(), footballPlayer.getLastName(), footballPlayer.getClothers_number(), teamFootballTwo.getName(), tvTime.getText().toString());
                    list.add(itemData);
                    adapter.notifyDataSetChanged();
                }
            }


        }


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (null != mySpeechRecongnizer) {
            mySpeechRecongnizer.destroy();
        }
    }
}
