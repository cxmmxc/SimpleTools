package cxm.simpletools.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Terry.Chen on 2015/9/24 10:51.
 * Description:心电图模拟
 * Email:cxm_lmz@163.com
 */
public class HeartView extends View {
    
    //画线的paint
    private Paint mPaint;
    private Path mPath;
    
    private int screenW, screenH;
    
    private int transX, moveX;//向左平移的距离
    
    private int x, y;//起点坐标
    
    private int initX;//开始移动的x坐标
    
    private boolean isCanMove;
    
    public HeartView(Context context) {
        this(context, null);
    }

    public HeartView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HeartView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        
        initData(context);
    }

    private void initData(Context context) {
//        int[] screenWH = MeasureTool.getScreenWH((Activity) context);
//        screenW = screenWH[0];
//        screenH = screenWH[1];

        transX = 0;

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(5);
        mPaint.setColor(Color.GREEN);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStrokeJoin(Paint.Join.ROUND);

        mPaint.setShadowLayer(7, 0, 0, Color.GREEN);

        mPath = new Path();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.BLACK);
//        mPath.lineTo(x, y);
        mPath.quadTo(x,y, x, y);
        canvas.translate(-transX, 0);
        countInfo();
        canvas.drawPath(mPath, mPaint);
        invalidate();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        screenW = w;
        screenH = h;
        x = 0; 
        y = h/2;
        
        initX = screenW / 2 + screenW / 4;
        moveX = screenW/24;
        
        mPath.moveTo(x, y);
    }

    private void countInfo() {
        if(isCanMove) {
            transX += 4;
        }
        
        if(x< initX) {
            x += 8;
        }else {
            if(x< initX + moveX) {
                x += 2;
                y -= 8;
            }else {
                if(x< initX + moveX*2) {
                    x += 2;
                    y += 14;
                }else {
                    if(x< initX + moveX*3) {
                        x += 2;
                        y -= 12;
                    }else {
                        if(x < initX + moveX*4) {
                            x += 2;
                            y += 6;
                        } else {
                            if(x < screenW) {
                                x+=8;
                            }else {
                                isCanMove = true;
                                initX = initX +screenW;
                            }
                        }
                    }
                }
            }
        }
    }
}
