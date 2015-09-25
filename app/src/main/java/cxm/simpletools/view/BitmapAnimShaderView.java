package cxm.simpletools.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import cxm.simpletools.R;
import cxm.simpletools.tool.MeasureTool;

/**
 * Created by Terry.Chen on 2015/9/24 17:00.
 * Description: Bitmap动画shader
 * Email:cxm_lmz@163.com
 */
public class BitmapAnimShaderView extends View {

    private Paint mStrokePaint, mFillPaint;
    
    private static final int CIRCLE_RADIUS = 150;
    
    private float x, y;


    public BitmapAnimShaderView(Context context) {
        this(context, null);
    }

    public BitmapAnimShaderView(Context context, AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public BitmapAnimShaderView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initData(context);
    }

    private void initData(Context context) {
        int[] screenWH = MeasureTool.getScreenWH((Activity) context);
        x = screenWH[0]/2;
        y = screenWH[1]/2;

        mStrokePaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        mFillPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        mStrokePaint.setColor(Color.BLACK);
        mStrokePaint.setStyle(Paint.Style.STROKE);
        mStrokePaint.setStrokeWidth(5);

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.zhuan_1);
        mFillPaint.setShader(new BitmapShader(bitmap, Shader.TileMode.REPEAT, Shader.TileMode.REPEAT));
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE:
                x = event.getX();
                y = event.getY();
                invalidate();
        }
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.GRAY);
        canvas.drawCircle(x, y, CIRCLE_RADIUS, mStrokePaint);
        canvas.drawCircle(x, y , CIRCLE_RADIUS, mFillPaint);
    }
}
