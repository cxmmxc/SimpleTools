package cxm.simpletools.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Terry.Chen on 2015/10/12 14:54.
 * Description:模拟翻页效果
 * Email:cxm_lmz@163.com
 */
public class PageTurnView extends View {

    private final static float TEXTSIZE_NORMAL = 1 / 40F, TEXTSIZE_LARGE = 1 / 20F;

    private List<Bitmap> mBitmaps;
    private Context context;
    private TextPaint textPaint;
    private int width, height;

    private float textSize_normal, textSize_large;

    private int pageIndex;

    private float mClipX;

    private float mAutoLeft, mAutoRight;

    private float mValidDis;

    private boolean isLastPage, isNextPage;
    
    private float mCurrentX;

    public PageTurnView(Context context) {
        this(context, null);
    }

    public PageTurnView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initData();
    }

    private void initData() {
        textPaint = new TextPaint(TextPaint.ANTI_ALIAS_FLAG | TextPaint.DITHER_FLAG | TextPaint.LINEAR_TEXT_FLAG);
        textPaint.setTextAlign(Paint.Align.CENTER);

        mBitmaps = new ArrayList<Bitmap>();

    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (mBitmaps == null || mBitmaps.size() < 1) {
            desplayScreen(canvas);
            return;
        }
        drawBitmaps(canvas);
    }

    private void drawBitmaps(Canvas canvas) {
        isLastPage = false;
        //限制pageindex范围
        pageIndex = pageIndex < 0 ? 0 : pageIndex;
        pageIndex = pageIndex > mBitmaps.size() ? mBitmaps.size() : pageIndex;
        
        int start = mBitmaps.size() - 2 -pageIndex;
        int end = mBitmaps.size() - pageIndex;

        if (start < 0) {
            isLastPage = true;
            //重置位置
            start = 0;
            end = 1;
        }
        for (int i = start; i < end; i++) {
            canvas.save();
            if (!isLastPage && i == end - 1) {
                canvas.clipRect(0, 0, mClipX, height);
            }
            canvas.drawBitmap(mBitmaps.get(i), 0, 0, null);
            canvas.restore();
        }
        
    }

    private void desplayScreen(Canvas canvas) {
        canvas.drawColor(Color.WHITE);
        textPaint.setTextSize(textSize_large);
        textPaint.setColor(Color.RED);
        canvas.drawText("FBI waring", width / 2, height / 4, textPaint);
        textPaint.setTextSize(textSize_normal);
        textPaint.setColor(Color.BLACK);
        canvas.drawText("no much data display", width / 2, height / 3, textPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        isNextPage = true;
        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                mCurrentX = event.getX();
                if (mCurrentX < mAutoLeft) {
                    isNextPage = false;
                    pageIndex--;
                    mClipX = mCurrentX;
                    invalidate();
                }
                break;
            case MotionEvent.ACTION_MOVE:
                float dis = event.getX() - mCurrentX;
                if (Math.abs(dis) > mValidDis) {
                    mClipX = event.getX();
                    invalidate();
                }
                break;
            case MotionEvent.ACTION_UP:
                //判断是否自动滑动
                judgeSlide();
                if (!isLastPage && isNextPage && mClipX <= 0) {
                    pageIndex++;
                    mClipX = width;
                    invalidate();
                }
                break;
        }
        return true;
    }

    private void judgeSlide() {
        if(mClipX <= mAutoLeft) {
            while (mClipX > 0) {
                mClipX--;
                invalidate();
            }
        }

        if (mClipX > mAutoRight) {
            while (mClipX < width) {
                mClipX++;
                invalidate();
            }
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        width = w;
        height = h;
        textSize_normal = height * TEXTSIZE_NORMAL;
        textSize_large = height * TEXTSIZE_LARGE;

        initBitmaps();

        mClipX = width;

        mAutoLeft = 1 / 5F * width;
        mAutoRight = 4 / 5F * width;
        mValidDis = 1 / 100F * width;

    }

    private void initBitmaps() {
        List<Bitmap> tempBs = new ArrayList<Bitmap>();
        for (int i = mBitmaps.size() - 1; i >= 0; i--) {
            Bitmap bitmap = Bitmap.createScaledBitmap(mBitmaps.get(i), width, height, true);
            tempBs.add(bitmap);
        }
        mBitmaps = tempBs;
    }

    public synchronized void setBitmaps(ArrayList<Bitmap> bitmaps) {
        if (bitmaps == null || bitmaps.isEmpty()) {
            throw new IllegalArgumentException("bitmaps is must not be null");
        }

        if (bitmaps.size() < 2) {
            throw new RuntimeException("fuck so little bitmap");
        }
        mBitmaps = bitmaps;
        invalidate();
    }
}
