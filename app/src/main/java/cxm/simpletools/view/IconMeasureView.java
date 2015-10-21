package cxm.simpletools.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

import com.lidroid.xutils.util.LogUtils;

import cxm.simpletools.R;
import cxm.simpletools.tool.MeasureTool;

/**
 * Created by Terry.Chen on 2015/10/21 09:48.
 * Description:Measure测量
 * Email:cxm_lmz@163.com
 */
public class IconMeasureView extends View {

    private TextPaint mTextPaint;

    private Bitmap mBitmap;


    private float mTextSize;

    private String mStr;

    private Context mContext;


    public enum MeaType {
        WIDTH, HEIGHT;
    }

    public IconMeasureView(Context context) {
        super(context);
    }

    public IconMeasureView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        initData();
    }

    private void initData() {
        mTextPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG | Paint.LINEAR_TEXT_FLAG);
        mTextPaint.setColor(Color.GREEN);
        if (null == mBitmap) {
            mBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.lakesi_1);
        }
        if (mStr == null) {
            mStr = "cxm";
        }
        int[] screenWH = MeasureTool.getScreenWH((Activity) mContext);
        LogUtils.e("1--"+getWidth()+","+mBitmap.getWidth()+","+getHeight()+","+mBitmap.getHeight());
        mTextSize = 1 / 10F * screenWH[0];
        mTextPaint.setTextAlign(Paint.Align.CENTER);
        mTextPaint.setTextSize(mTextSize);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(getMeasureSize(widthMeasureSpec, MeaType.WIDTH), getMeasureSize(heightMeasureSpec, MeaType.HEIGHT));
    }

    private int getMeasureSize(int spec, MeaType type) {
        int size = MeasureSpec.getSize(spec);
        int mode = MeasureSpec.getMode(spec);
        int result = 0;
        switch (mode) {
            case MeasureSpec.EXACTLY:
                result = size;
                break;
            default:
                if (type == MeaType.WIDTH) {
                    float textWidth = mTextPaint.measureText(mStr);
                    result = ((int) (textWidth >= mBitmap.getWidth() ? textWidth : mBitmap.getWidth())) + getPaddingLeft() + getPaddingRight();
                } else if (type == MeaType.HEIGHT) {
                    LogUtils.e("scent--"+mTextPaint.descent()+","+mTextPaint.ascent());
                    result = ((int) (mTextPaint.descent() - mTextPaint.ascent()) * 2 + mBitmap.getHeight()) + getPaddingTop() + getPaddingBottom();
                }
                if (mode == MeasureSpec.AT_MOST) {
                    result = Math.min(result, size);
                }
                break;
        }
        return result;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        LogUtils.e(getWidth()+","+mBitmap.getWidth()+","+getHeight()+","+mBitmap.getHeight());
        canvas.drawBitmap(mBitmap, getWidth() / 2 - mBitmap.getWidth() / 2, getHeight() / 2 - mBitmap.getHeight() / 2, null);
        canvas.drawText(mStr, mBitmap.getWidth() / 2, mBitmap.getHeight() + getHeight() / 2 - mBitmap.getHeight() / 2- mTextPaint.ascent(), mTextPaint);
    }
}
