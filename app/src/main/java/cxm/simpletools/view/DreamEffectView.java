package cxm.simpletools.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RadialGradient;
import android.graphics.Shader;
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

    private Paint mPaint, mShaderPaint;

    private Bitmap mBitmap, mDarkConorBitmap;

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
        mBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.shader);
        mMode = new PorterDuffXfermode(PorterDuff.Mode.SCREEN);
        int[] screenWH = MeasureTool.getScreenWH((Activity) context);
        x = screenWH[0] / 2 - mBitmap.getWidth()/2;
        y = screenWH[1] / 2 - mBitmap.getHeight()/2;
        // 去饱和、提亮、色相矫正  
        mPaint.setColorFilter(new ColorMatrixColorFilter(new float[]{0.8587F, 0.2940F, -0.0927F, 0, 6.79F, 0.0821F, 0.9145F, 0.0634F, 0, 6.79F, 0.2019F, 0.1097F, 0.7483F, 0, 6.79F, 0, 0, 0, 1, 0}));
        mShaderPaint = new Paint();
        mDarkConorBitmap = Bitmap.createBitmap(mBitmap.getWidth(), mBitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(mDarkConorBitmap);
        float radius = canvas.getHeight() * 3f / 5f;
        RadialGradient radialGradient = new RadialGradient(canvas.getWidth()/2, canvas.getHeight()/2, radius, new int[]{0, 0, 0XAA000000}, new float[]{0f, 0.7f, 1.0f}, Shader.TileMode.CLAMP);

        Matrix matrix = new Matrix();
        matrix.setScale(canvas.getWidth() / radius * 2, 1.0f);
        matrix.preTranslate(((radius * 1.5f) - canvas.getWidth()) / 2, 0);
        radialGradient.setLocalMatrix(matrix);
        mShaderPaint.setShader(radialGradient);
        canvas.drawRect(0, 0, canvas.getWidth(), canvas.getHeight(), mShaderPaint);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.BLACK);

        int sr = canvas.saveLayer(x, y, x + mBitmap.getWidth(), y + mBitmap.getHeight(), mPaint, Canvas.ALL_SAVE_FLAG);
        // 绘制混合颜色  
        canvas.drawColor(0xcc1c093e);
//        canvas.drawColor(Color.GREEN);
        mPaint.setXfermode(mMode);
        canvas.drawBitmap(mBitmap, x, y, mPaint);
        mPaint.setXfermode(null);
        canvas.restoreToCount(sr);
//        canvas.drawRect(x, y, x+mBitmap.getWidth(), y+mBitmap.getHeight(), mShaderPaint);
        canvas.drawBitmap(mDarkConorBitmap,x, y, null);
    }
}
