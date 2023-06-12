package com.trongminh8811.rollthediceyoualwayslose;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    ImageView imgDice1, imgDice2, imgDice3;
    TextView tvDisplay, tvMessage;
    Button btnTai, btnXiu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imgDice1 = findViewById(R.id.imgDice1);
        imgDice2 = findViewById(R.id.imgDice2);
        imgDice3 = findViewById(R.id.imgDice3);
        btnTai = findViewById(R.id.btnTai);
        btnXiu = findViewById(R.id.btnXiu);
        tvDisplay = findViewById(R.id.tvDisplayResult);
        tvMessage = findViewById(R.id.tvMessage);

        btnTai.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View view) {
                Display(1);
            }
        });

        btnXiu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Display(0);
            }
        });
    }

    public int RollDice(){
        Random r = new Random();
        return r.nextInt(6) + 1;
    }

    public int CheatRoll(int min, int max){
        Random r = new Random();
        return r.nextInt(max-min+1) + min;
    }

    public String CheckTaiXiu(int a, int b, int c){
        if (a+b+c >= 10)
            return "Tài";
        else return "Xỉu";
    }

    @SuppressLint("SetTextI18n")
    public void Display(int type){
        int a = RollDice();
        int b = RollDice();
        int c = RollDice();

        int[] arr = CheatTool(type);
        a = arr[0];
        b = arr[1];
        c = arr[2];

        String res = CheckTaiXiu(a, b, c);
        imgDice1.setImageResource(Functions.getImageID(MainActivity.this, "dice"+a));
        imgDice2.setImageResource(Functions.getImageID(MainActivity.this, "dice"+b));
        imgDice3.setImageResource(Functions.getImageID(MainActivity.this, "dice"+c));

        tvDisplay.setText(a + " + " + b + " + " + c + " = "  + (a+b+c));
        tvMessage.setText("Kết quả: " + res + ", bạn thua");
//        if (type==1 && res.equalsIgnoreCase("Tài"))
//            Show("Thắng");
//        else if (type==0 && res.equalsIgnoreCase("Xỉu"))
//            Show("Thắng");
//        else
//            Show("Thua");

    }

    public void Show(String res){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Kết quả");
        builder.setMessage(res);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        builder.create().show();
    }

    public int[] CheatTool(int type){
        //type = 0 => xiu
        //type = 1 => tai
        int maxTotal = 18;
        int a, b, c;
        if (type==1){
            maxTotal = 9;
            a = RollDice();
            b = RollDice();
            while (a+b >= maxTotal){
                a = RollDice();
                b = RollDice();
            }
            c = CheatRoll(1, maxTotal-b-a);
        }
        else {
            a = RollDice();
            b = RollDice();
            c = RollDice();
            while (a+b+c <= 9){
                a = RollDice();
                b = RollDice();
                c = RollDice();
            }
        }
        return new int[]{a, b, c};
    }
}