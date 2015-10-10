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
    
    private boolean isOffset;

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

        mPath = new Path();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
//        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;

        endY = 1 / 4F * mHeight;

        ctrY = -1 / 16F * mHeight;
    }

    @Override
    protected void onDraw(Canvas canvas) {
//        super.onDraw(canvas);
        mPath.moveTo(-1 / 2F * mWidth, endY);
        mPath.quadTo(ctrX, ctrY, 5 / 3F * mWidth, endY);
        mPath.lineTo(5 / 3F * mWidth, mHeight);
        mPath.lineTo(-1 / 2F * mWidth, mHeight);
        mPath.close();
        canvas.drawPath(mPath, mPaint);
        if(ctrX <= -1 / 2F * mWidth) {
            isOffset = true;
        }else if (ctrX >= 5 / 3F * mWidth) {
            isOffset = false;
        }
        ctrX = isOffset ? ctrX + 20 : ctrX - 20;
        
        if (ctrY <= mHeight) {
            ctrY += 3;
            endY += 3;
        }
        mPath.reset();
        invalidate();
    }
    
    
}
