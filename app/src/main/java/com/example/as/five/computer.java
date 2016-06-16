package com.example.as.five;

import android.os.Handler;
import android.os.Message;

/**
 * Created by as on 2016/6/4.
 */
public class computer extends Thread {
    gamebox gamebox;
    Handler handler;
    int maxscore;
    int x,y;
    public computer(gamebox gamebox,Handler handler) {
        this.gamebox = gamebox;
        this.handler = handler;
    }

    public void run() {
        while (true) {
            if (gamebox.side == -1){
                maxscore = 0;
                int x = 0,y = 0;
                int temp;
                for(int i = 0;i<15;i++){
                    for(int j = 0;j<15;j++){
                        if(gamebox.data[i][j] == 0) {
                            temp = countscore(i, j);
                            if (maxscore < temp) {
                                maxscore = temp;
                                x = i;
                                y = j;
                            }else if(maxscore == temp){
                                if(Math.random()<0.5){
                                    maxscore = temp;
                                    x = i;
                                    y = j;
                                }
                            }
                        }
                    }
                }
                gamebox.data[x][y] = -1;
                gamebox.postInvalidate();
                gamebox.side = 1;
                if(maxscore >= 100000){
                    gamebox.side = 0;
                    Message msg = new Message();
                    handler.sendMessage(msg);
                    break;
                }
            }
        }
    }
    public int getpoint(int x,int y,int dir,int dis){
        int xx = 0;
        int yy = 0;
        switch (dir){
            case 0:
                xx = x+dis;
                yy = y;
                break;
            case 1:
                xx = x+dis;
                yy = y+dis;
                break;
            case 2:
                xx = x;
                yy = y+dis;
                break;
            case 3:
                xx = x-dis;
                yy = y+dis;
                break;
        }
        if(xx < 0 || xx > 14 ||yy < 0 || yy > 14){
            return -2;
        }
        else return gamebox.data[xx][yy];
    }
    private int countscore(int x,int y){
        int wmark = 0;
        int bmark = 0;
        int count = 0;
        StringBuffer wsb,bsb;
        String ws,bs;
        for(int i = 0;i<4;i++){
            wsb = new StringBuffer();
            bsb = new StringBuffer();
            for(int j = -4;j<=4;j++){
                if(j == 0){
                    wsb.append(-1);
                    bsb.append(1);
                }
                else {
                    wsb.append(getpoint(x, y, i, j));
                    bsb.append(getpoint(x, y, i, j));
                }
            }
            ws = wsb.toString();
            bs = bsb.toString();
            if(ws.contains("-1-1-1-1-1")){
                count += 100000;
            }else if(ws.contains("0-1-1-1-10")){
                count += 10000;
            }else if(ws.contains("1-1-1-1-10") || ws.contains("0-1-1-1-11")
                    || ws.contains("1-1-1-10-1") || ws.contains("-10-1-1-11")
                    || ws.contains("1-1-10-1-1")|| ws.contains("-1-10-1-11")
                    || ws.contains("1-10-1-1-1") || ws.contains("-1-1-10-11")){
                if(wmark == 0){
                    wmark = 4;
                    count += 50;
                }else if(wmark == 4){
                    count += 10000;
                }else if(wmark == 3){
                    count += 10000;
                }
            }else if(ws.contains("0-1-1-10")||ws.contains("0-1-10-10")
                    ||ws.contains("0-10-1-10")){
                if(wmark == 0){
                    wmark = 3;
                    count += 50;
                }else if(wmark == 4){
                    count += 10000;
                }else if(wmark == 3){
                    count += 2000;
                }
            }else if(ws.contains("0-1-10") || ws.contains("0-10-10")){
                count += 80;
            }else if(ws.contains("1-1-1-100")||ws.contains("00-1-1-11")
                    ||ws.contains("1-1-10-10")||ws.contains("0-10-1-11")){
                count += 50;
            }

            if(bs.contains("11111")){
                count += 30000;
            }else if(bs.contains("011110")){
                count += 4000;
            }else if(bs.contains("-111110") || bs.contains("01111-1")
                    || bs.contains("-111101") || bs.contains("10111-1")
                    || bs.contains("-111011")|| bs.contains("11011-1")
                    || bs.contains("-110111") || bs.contains("11101-1")){
                if(bmark == 0){
                    bmark = 4;
                    count += 150;
                }else if(bmark == 4){
                    count += 2000;
                }else if(bmark == 3){
                    count += 2000;
                }
            }else if(bs.contains("01110") || bs.contains("011010")
                    ||bs.contains("010110")){
                if(bmark == 0){
                    bmark = 3;
                    count += 150;
                }else if(bmark == 4){
                    count += 2000;
                }else if(bmark == 3){
                    count += 500;
                }
            }else if(bs.contains("0110") ){
                count += 150;
            }else if( bs.contains("01010")){
                count += 130;
            }
            else if(bs.contains("-111100")||bs.contains("00111-1")
                    ||bs.contains("-111010")||bs.contains("01011-1")){
                count += 150;
            }
        }
        return count;
    }
}