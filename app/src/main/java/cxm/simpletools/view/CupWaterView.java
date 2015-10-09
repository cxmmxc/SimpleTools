package cxm.simpletools.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Terry.Chen on 2015/10/9 10:25.
 * Description: 模拟水杯倒水效果
 * Email:cxm_lmz@163.com
 */
public class CupWaterView extends View {
    private Paint mPaint;
    private Path mPath;
    
    private float ctrX, ctrY;
    
    private float endY;
    
    private int mWidth, mHeight;

    public CupWaterView(Context context) {
        this(context, null);
    }

    public CupWaterView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initData();
    }

    private void initData() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        mPaint.setColor(Color.GREEN);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
//        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }
}
