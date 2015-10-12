package cxm.simpletools.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
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
    
    private int mClipX;
    
    private float mAutoLeft, mAutoRight;
    
    private float mValidDis;
    
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
        
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
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
        if(bitmaps == null || bitmaps.isEmpty()) {
            throw new IllegalArgumentException("bitmaps is must not be null");
        }
        
        if(bitmaps.size() <2) {
            throw new RuntimeException("fuck so little bitmap");
        }
        mBitmaps = bitmaps;
        invalidate();
    }
}
