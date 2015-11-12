package cxm.simpletools.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.lidroid.xutils.util.LogUtils;

import cxm.simpletools.R;
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
    
    private float preX,preY;
    
    private PointF mMidP;//双指的中间点
    
    private float mPreDis;
    
    private final static float MIN_DISTANCE = 10f;

    public EditView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initData(context);
    }

    private void initData(Context context) {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mColor = 0xFFFF0000;
        mPaint.setColor(mColor);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(5f);
        int[] screenWH = MeasureTool.getScreenWH((Activity) context);
        mScW = screenWH[0];
        mScH = screenWH[1];
        mEditMode = EditMode.NONE;
        mCurrentMatrix = new Matrix();
        mSaveMatrix = new Matrix();
        mPath = new Path();
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.t6);
        mBitmap = Bitmap.createScaledBitmap(bitmap, mScW, mScH, true);
        mCanvas = new Canvas(mBitmap);
        mMidP = new PointF();
    }


    @Override
    protected void onDraw(Canvas canvas) {
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
                mEditMode = EditMode.EDIT;
                mPath.reset();
                //单指触摸，为编辑模式
                LogUtils.v("ACTION_DOWN");
                mPath.moveTo(x, y);
                preX = x;
                preY = y;
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                //双指触摸为缩放模式
                LogUtils.v("ACTION_POINTER_DOWN");
                mSaveMatrix.set(mCurrentMatrix);
                mPreDis = calSpacing(event);
                if(mPreDis > 15f) {
                    mSaveMatrix.set(mCurrentMatrix);
                    calMid(mMidP, event);
                    mEditMode = EditMode.SCALE;
                }
                break;

            case MotionEvent.ACTION_MOVE:
                float move_x = event.getX();
                float move_y = event.getY();
                if (mEditMode == EditMode.EDIT) {
                    //移动
                    mPath.lineTo( move_x, move_y);
                    mCanvas.drawPath(mPath, mPaint);
                }else if (mEditMode == EditMode.SCALE && event.getPointerCount() == 2) {
                    mCurrentMatrix.set(mSaveMatrix);
                    float space = calSpacing(event);
                    if(space > 10f) {
                        float scale = space / mPreDis;
                        mCurrentMatrix.postScale(scale, scale, mMidP.x, mMidP.y);
                    }
                }
                LogUtils.v("ACTION_MOVE");
                break;
            case MotionEvent.ACTION_POINTER_UP:
                mEditMode = EditMode.EDIT;
                //第二指抬起
                LogUtils.v("ACTION_POINTER_UP");
                break;

            case MotionEvent.ACTION_UP:
                mEditMode = EditMode.NONE;
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

    //计算中点
    private void calMid(PointF mMindP, MotionEvent event) {
        float du_x = (event.getX(0) + event.getX(1)) / 2;
        float du_y = (event.getY(0) + event.getY(1)) / 2;
        mMindP.set(du_x, du_y);
    }

    //计算两点的距离
    private static float calSpacing(MotionEvent event) {
        float du_x = event.getX(0) - event.getX(1);
        float du_y = event.getY(0) - event.getY(1);
        return (float) Math.sqrt(du_x * du_x + du_y * du_y);
    }
}
