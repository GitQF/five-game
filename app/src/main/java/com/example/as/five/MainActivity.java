package com.example.as.five;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private gamebox gamebox;
    private Handler handler;
    computer computer;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        gamebox = (gamebox)findViewById(R.id.gamebox);
        handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                Toast.makeText(MainActivity.this,"you lost",Toast.LENGTH_SHORT).show();
            }
        };
        gamebox.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent me) {
                for(int i = 0;i<15;i++){
                    for(int j = 0;j<15;j++){
                        if(gamebox.side == 1 && Math.abs(me.getX()-30-gamebox.l*i)<gamebox.l/2 && Math.abs(me.getY()-30-gamebox.l*j)<gamebox.l/2) {
                            gamebox.data[i][j] = 1;
                            gamebox.side = -gamebox.side;
                            gamebox.postInvalidate();
                            if(isgameover()){
                                gamebox.side = 0;
                                Toast.makeText(MainActivity.this,"you win",Toast.LENGTH_SHORT).show();
                                break;
                            }

                        }
                    }
                }
                return false;
            }
        });
        computer = new computer(gamebox,handler);
        computer.start();
    }
    private boolean isgameover(){
        for(int i = 4;i<=10;i++){
            for(int j = 4;j<=10;j++){
                for(int k = 0;k<4;k++){
                    int count = 0;
                    for(int ii = -4;ii<5;ii++){
                        if(computer.getpoint(i,j,k,ii) == 1){
                            count++;
                        }else{
                            count = 0;
                        }if(count == 5){
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }
}
