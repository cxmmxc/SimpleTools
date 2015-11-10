package cxm.simpletools.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.lidroid.xutils.util.LogUtils;

import cxm.simpletools.tool.MeasureTool;

/**
 * Created by lmz_cxm on 2015/11/10.
 * 可编辑的View
 */
public class EditView extends View {

    private Bitmap mBitmap;//底层的Bitmap图
    private Bitmap mFinalBitmap;//最终编辑完成的图

    private Paint mPaint;//画笔

    private int mColor;//画笔的颜色,默认是红色

    private Canvas mCanvas;

    private int mScW, mScH;

    private EditMode mEditMode;

    private Matrix mCurrentMatrix, mSaveMatrix;//当前matrix和保存的matrix；

    private Path mPath;

    public EditView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initData(context);
    }

    private void initData(Context context) {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mColor = 0xFFFF0000;
        mPaint.setColor(mColor);
        int[] screenWH = MeasureTool.getScreenWH((Activity) context);
        mScW = screenWH[0];
        mScH = screenWH[1];
        mEditMode = EditMode.NONE;
        mCurrentMatrix = new Matrix();
        mSaveMatrix = new Matrix();
        mPath = new Path();
        mBitmap = Bitmap.createScaledBitmap(mBitmap, mScW, mScH, true);
        mCanvas = new Canvas(mBitmap);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mBitmap != null) {
            canvas.drawBitmap(mBitmap, mCurrentMatrix, null);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                mPath.reset();
                //单指触摸，为编辑模式
                LogUtils.v("ACTION_DOWN");
                mPath.moveTo(x, y);
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                //双指触摸为缩放模式
                LogUtils.v("ACTION_POINTER_DOWN");
                break;

            case MotionEvent.ACTION_MOVE:
                float move_x = event.getX();
                float move_y = event.getY();
                //移动
                mPath.lineTo( move_x, move_y);
                mCanvas.drawPath(mPath, mPaint);
                LogUtils.v("ACTION_MOVE");
                break;
            case MotionEvent.ACTION_POINTER_UP:
                //第二指抬起
                LogUtils.v("ACTION_POINTER_UP");
                break;

            case MotionEvent.ACTION_UP:
                //指头抬起
                LogUtils.v("ACTION_UP");
                break;
        }
        invalidate();
        return true;
    }

    //传入Bitmap，供编辑
    public void setBitmap(Bitmap bitmap) {
        if (bitmap != null) {
            mBitmap = bitmap;
            invalidate();
        }
    }

    //设置画笔的颜色
    private void setColor(int color) {
        if (color != -1)
            mColor = color;
    }

    enum EditMode {
        NONE, EDIT, SCALE;
    }
}
