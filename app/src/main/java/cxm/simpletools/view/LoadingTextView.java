package cxm.simpletools.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import cxm.simpletools.R;

/**
 * Created by Terry.Chen on 2015/11/5 14:02.
 * Email:cxm_lmz@163.com
 */
public class LoadingTextView extends View {
    private Paint mPaint,mBackPaint;
    private Rect mFirRect;
    private int mScrW, mScrH;
    private Bitmap mBitmap;
    private int mBmpW, mBmpH;
    
    private int mRecTop;
    
    public LoadingTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initData(context);
    }

    private void initData(Context context) {
        mBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.trans_text_2);
        mBmpW = mBitmap.getWidth();
        mBmpH = mBitmap.getHeight();
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.RED);

        mBackPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawColor(Color.GRAY);
        int count = canvas.saveLayer(0, 0, getWidth(), getHeight(), mPaint, Canvas.ALL_SAVE_FLAG);
        canvas.drawBitmap(mBitmap, 0, 0, mBackPaint);
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawRect(mFirRect, mPaint);
        mPaint.setXfermode(null);
        canvas.restoreToCount(count);
        if(mRecTop <=0) {
            mRecTop = mBmpH;
        }
        mRecTop -= 1;
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        mFirRect.top = mRecTop;
        invalidate();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        mScrW = w;
        mScrH = h;
        mRecTop = mBmpH;
        mFirRect = new Rect(0, mRecTop, 0 + mBmpW, mBmpH * 2);
    }
}
