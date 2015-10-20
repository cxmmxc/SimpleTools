package cxm.simpletools.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;

import cxm.simpletools.R;
import cxm.simpletools.tool.MeasureTool;

/**
 * Created by Terry.Chen on 2015/10/20 11:03.
 * Description: 各种测试
 * Email:cxm_lmz@163.com
 */
public class CanvasTestView extends View {
    Bitmap mBitmap;
    private int mWidth,mHeight;

    public CanvasTestView(Context context) {
        this(context, null);
    }

    public CanvasTestView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initData(context);
        
    }

    private void initData(Context context) {
        int[] screenWH = MeasureTool.getScreenWH((Activity) context);
        mWidth = screenWH[0];
        mHeight = screenWH[1];
        mBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.lakesi);
//        mBitmap = Bitmap.createScaledBitmap(mBitmap, mWidth, mHeight, true);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawColor(Color.WHITE);
//        canvas.save();
//        
//        canvas.translate(200, 1000);
//        canvas.rotate(90 - 45);
//        canvas.translate(0, -mHeight);
//        canvas.scale(-1, 1, 0, 0);
//        canvas.translate(-mWidth, 0);
        canvas.drawBitmap(mBitmap, 0, 0, null);
//        canvas.restore();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//        setMeasuredDimension(300, 300);
        int tempWidth = 0;
        int mode = MeasureSpec.getMode(widthMeasureSpec);
        int size = MeasureSpec.getSize(widthMeasureSpec);
        switch (mode) {
            case MeasureSpec.UNSPECIFIED:
                tempWidth = size;
                break;
            case MeasureSpec.AT_MOST:
                tempWidth = mBitmap.getWidth();
                break;
            case MeasureSpec.EXACTLY:
                tempWidth = Math.min(mBitmap.getWidth(), size);
                break;
        }

        int tempHeight = 0;
        int heiMode = MeasureSpec.getMode(heightMeasureSpec);
        int heiSize = MeasureSpec.getSize(heightMeasureSpec);
        switch (heiMode) {
            case MeasureSpec.UNSPECIFIED:
                tempHeight = heiSize;
                break;
            case MeasureSpec.AT_MOST:
                tempHeight = mBitmap.getHeight();
                break;
            case MeasureSpec.EXACTLY:
                tempHeight = Math.min(mBitmap.getHeight(), heiSize);
                break;
        }
        setMeasuredDimension(tempWidth, tempHeight);
        
    }
    

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        mWidth = w;
        mHeight = h;
    }
}
