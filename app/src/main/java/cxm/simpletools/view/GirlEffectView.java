package cxm.simpletools.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;

import cxm.simpletools.R;
import cxm.simpletools.tool.MeasureTool;

/**
 * Created by Terry.Chen on 2015/9/25 14:44.
 * Description:妹子倒影照片
 * Email:cxm_lmz@163.com
 */
public class GirlEffectView extends View {

    private Bitmap mGirlBitmap, mRefBitmap;

    private Paint mPaint;

    private int x, y;//位图起点坐标

    private PorterDuffXfermode mPorMode;

    public GirlEffectView(Context context) {
        this(context, null);
    }

    public GirlEffectView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initData(context);
    }

    private void initData(Context context) {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);


        mGirlBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.lakesi_1);

        Matrix matrix = new Matrix();
        matrix.setScale(1f, -1f);

        mRefBitmap = Bitmap.createBitmap(mGirlBitmap, 0, 0, mGirlBitmap.getWidth(), mGirlBitmap.getHeight(), matrix, true);

        int scrW = MeasureTool.getScreenWH((Activity) context)[0];
        int scrH = MeasureTool.getScreenWH((Activity) context)[1];

        x = scrW / 2 - mGirlBitmap.getWidth() / 2;
        y = scrH / 2 - mGirlBitmap.getHeight() / 2;

        mPaint.setShader(new LinearGradient(x, y + mGirlBitmap.getHeight(), x, y + mGirlBitmap.getHeight() + mGirlBitmap.getHeight() / 4, Color.GRAY, Color.TRANSPARENT, Shader.TileMode.MIRROR));

        mPorMode = new PorterDuffXfermode(PorterDuff.Mode.DST_IN);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.BLACK);
        canvas.drawBitmap(mGirlBitmap, x, y, null);
        int sc = canvas.saveLayer(x, y + mGirlBitmap.getHeight(), x + mGirlBitmap.getWidth(), y + mGirlBitmap.getHeight() * 2, null, Canvas.ALL_SAVE_FLAG);
        canvas.drawBitmap(mRefBitmap, x, y + mGirlBitmap.getHeight(), null);
        mPaint.setXfermode(mPorMode);
        canvas.drawRect(x, y + mGirlBitmap.getHeight(), x + mGirlBitmap.getWidth(), y + mGirlBitmap.getHeight() * 2, mPaint);
        mPaint.setXfermode(null);
        canvas.restoreToCount(sc);
    }
}
