package com.example.as.five;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by as on 2016/6/4.
 */
public class gamebox extends View {
    int side = 1;
    int[][] data = new int[15][15];
    Paint paint;
    Bitmap black,white;
    private Rect rect;
    int l = (getWidth()-55)/14;
    public gamebox(Context context){
        super(context);
    }
    public gamebox(Context context, AttributeSet attributeSet){
        super(context,attributeSet);
        black = ((BitmapDrawable)getResources().getDrawable(R.drawable.black)).getBitmap();
        white = ((BitmapDrawable)getResources().getDrawable(R.drawable.white)).getBitmap();
    }
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        l = (getWidth()-55)/14;
        paint = new Paint();
        for(int i = 0;i<15;i++){
            for(int j = 0;j<15;j++){
                rect = new Rect(-22+i*l,-22+j*l,85+i*l,85+j*l);
                if(data[i][j] == 1)canvas.drawBitmap(black,null,rect,paint);
                if(data[i][j] == -1)canvas.drawBitmap(white,null,rect,paint);

            }
        }
    }
}
