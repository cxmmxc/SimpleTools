package cxm.simpletools.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by Terry.Chen on 2015/9/24 17:00.
 * Description: Bitmap动画shader
 * Email:cxm_lmz@163.com
 */
public class BitmapAnimShaderView extends View {
    
    private Paint mPaint;
    
    
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
        
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }
}
