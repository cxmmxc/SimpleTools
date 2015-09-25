package cxm.simpletools.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.AttributeSet;
import android.view.View;

import cxm.simpletools.R;
import cxm.simpletools.tool.MeasureTool;

/**
 * Created by Terry.Chen on 2015/9/25 15:50.
 * Description: 妹子特效
 * Email:cxm_lmz@163.com
 */
public class DreamEffectView extends View {

    private Paint mPaint;

    private Bitmap mBitmap;

    private int x, y;

    private PorterDuffXfermode mMode;

    public DreamEffectView(Context context) {
        this(context, null);
    }

    public DreamEffectView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initData(context);
    }

    private void initData(Context context) {
        mPaint = new Paint();
        mBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.meizi);
        mMode = new PorterDuffXfermode(PorterDuff.Mode.SCREEN);
        int[] screenWH = MeasureTool.getScreenWH((Activity) context);
        x = screenWH[0] / 2 - mBitmap.getWidth()/2;
        y = screenWH[1] / 2 - mBitmap.getHeight()/2;
        // 去饱和、提亮、色相矫正  
        mPaint.setColorFilter(new ColorMatrixColorFilter(new float[]{0.8587F, 0.2940F, -0.0927F, 0, 6.79F, 0.0821F, 0.9145F, 0.0634F, 0, 6.79F, 0.2019F, 0.1097F, 0.7483F, 0, 6.79F, 0, 0, 0, 1, 0}));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.BLACK);

        int sr = canvas.saveLayer(x, y, x + mBitmap.getWidth(), y + mBitmap.getHeight(), mPaint, Canvas.ALL_SAVE_FLAG);
        // 绘制混合颜色  
//        canvas.drawColor(0xcc1c093e);
        canvas.drawColor(Color.GREEN);
        mPaint.setXfermode(mMode);
        canvas.drawBitmap(mBitmap, x, y, mPaint);
        mPaint.setXfermode(null);
        canvas.restoreToCount(sr);
    }
}
