package com.linhao.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.linhao.R;


/**
 * Created by haoshenglin on 2018/1/22.
 */

public class CustomSoundView extends View {

    private Paint paint;
    private int width;
    private int height;
    private final static String TAG = "CustomSoundView==";
    float startX;
    float startY;
    float distance = 50;
    float currentDistance = 0.0f;
    float straightLineDistance = 10.0f;
    float horizontalLine = 10.0f;
    float endX = 0.0f;
    int size = 5;
    int allCount = 4;
    private int colorPaint;

    public CustomSoundView(Context context) {
        this(context, null);
    }

    public CustomSoundView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomSoundView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }


    private void init(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CustomSoundViewType);
        straightLineDistance = typedArray.getFloat(R.styleable.CustomSoundViewType_straightLineDistance, 10.0f);
        colorPaint = typedArray.getColor(R.styleable.CustomSoundViewType_lineColor, Color.BLUE);
        allCount = typedArray.getInt(R.styleable.CustomSoundViewType_allCount, 4);
        size = typedArray.getInt(R.styleable.CustomSoundViewType_lineSize, 5);
        typedArray.recycle();
        paint = new Paint();
        paint.setColor(colorPaint);
        //抗锯齿
        paint.setAntiAlias(true);
        paint.setStrokeWidth(2);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // drawCircles(canvas);
        drawLines(canvas);
    }


    //设置每一段的波纹的个数
    public void setSize(int size1){
        this.size = size1;
    }

    // 设置波纹的颜色
    public void setColorPaint(int colorPaint1){
        this.colorPaint = colorPaint1;
    }

    //设定公共的段数
    public void setAllCount(int allCount){
        this.allCount = allCount;
    }

    public void setDistance(float distances) {
        this.distance = distances;
    }

    public void setStraightLineDistance(float straightLineDistance1) {
        straightLineDistance = straightLineDistance1;
        invalidate();
    }

    private void drawLines(Canvas canvas) {
        for (int i = 1; i <= allCount; i++) {
            drawOneLine(canvas, paint, i);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int mode = MeasureSpec.getMode(widthMeasureSpec);
        int size = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        if (mode == MeasureSpec.AT_MOST) {   //wrap_content
            width = size;
        } else if (mode == MeasureSpec.EXACTLY) {  //match_content   100px
            width = size;
        } else if (mode == MeasureSpec.UNSPECIFIED) {  //不确定大小，需要指定大小
            width = 500;
        }
        setMeasuredDimension(width, height);
    }


    public void drawCircles(Canvas canvas) {
        for (int i = 0; i < 20; i++) {
            float radius = 5;
            float cx = ((i + 1) * (radius + 20)) + 100 + getMeasuredWidth() / 3;
            Log.i(TAG, cx + "==");
            float cy = height + 100;
            canvas.drawCircle(cx, cy, radius, paint);
        }
    }


    public void drawOneLine(Canvas canvas, Paint paint, int position) {
        currentDistance = size * horizontalLine;
        if (position == 1) {
            startX = getMeasuredWidth() / 4 + currentDistance;
        } else {
            startX = endX + currentDistance + horizontalLine;
        }
        startY = 100;
        endX = startX + distance;
        float endY = startY;
        canvas.drawLine(startX, startY, endX, endY, paint);
        for (int i = 1; i <= size; i++) {
            float startX1 = endX + horizontalLine * i;
            float startY1 = startY - (i * straightLineDistance);
            float endX1 = startX1;
            float endY1 = startY + (i * straightLineDistance);
            //i 大于总数的一半加1的时候
            if (i > (size / 2 + 1)) {
                startY1 = startY - (size - i + 1) * straightLineDistance;
                endY1 = startY + (size - i + 1) * straightLineDistance;
            }
            canvas.drawLine(startX1, startY1, endX1, endY1, paint);
        }
        //当是最后一个的时候再画一条线
        if (position == allCount) {
            if (horizontalLine < 5.0f) {
                startX = endX + currentDistance;
            } else {
                startX = endX + currentDistance + horizontalLine;
            }
            endX = startX + distance;
            canvas.drawLine(startX, startY, endX, endY, paint);
        }
    }

}
