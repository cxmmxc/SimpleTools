package cxm.simpletools.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import cxm.simpletools.R;
import cxm.simpletools.tool.MeasureTool;

/**
 * Created by Terry.Chen on 2015/9/29 11:28.
 * Description: 拖拉缩放View
 * Email:cxm_lmz@163.com
 */
public class DragScaleView extends ImageView {


    private final static int MODE_NONE = 0x03;//平移模式
    private final static int MODE_TRANSLATE = 0x01;//平移模式
    private final static int MODE_SCALE = 0x02;//缩放旋转模式

    private int current_mode;

    private Matrix mCurrentMatrix, mSaveMatrix;

    private Bitmap mBitmap;

    private PointF mStartP, mMindP;

    private float mRotate, mSaveRotate, mPremove;

    private int mScW, mScH;

    private boolean isTwoPoint;

    public DragScaleView(Context context) {
        this(context, null);
    }

    public DragScaleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initData(context);

    }

    private void initData(Context context) {
        current_mode = MODE_NONE;
        mCurrentMatrix = new Matrix();
        mSaveMatrix = new Matrix();
        mStartP = new PointF();
        mMindP = new PointF();

        int[] screenWH = MeasureTool.getScreenWH((Activity) context);
        mScW = screenWH[0];
        mScH = screenWH[1];
        mBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.lakesi_1);
        mBitmap = Bitmap.createScaledBitmap(mBitmap, mScW, mScH, true);
        setImageBitmap(mBitmap);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                mSaveMatrix.set(mCurrentMatrix);
                mStartP.set(event.getX(), event.getY());
                current_mode = MODE_TRANSLATE;
                isTwoPoint = false;
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                //计算两点 的距离
                mPremove = calSpacing(event);
                if (mPremove > 15f) {
                    mSaveMatrix.set(mCurrentMatrix);
                    calMid(mMindP, event);
                    current_mode = MODE_SCALE;
                }
                mSaveRotate = calRotate(event);
                isTwoPoint = true;

            case MotionEvent.ACTION_MOVE:
                if (current_mode == MODE_TRANSLATE) {
                    mCurrentMatrix.set(mSaveMatrix);
                    float dx = event.getX() - mStartP.x;
                    float dy = event.getY() - mStartP.y;
                    mCurrentMatrix.postTranslate(dx, dy);
                } else if (current_mode == MODE_SCALE && event.getPointerCount() == 2) {
                    float cuSpace = calSpacing(event);
                    mCurrentMatrix.set(mSaveMatrix);
                    if(cuSpace > 10f) {
                        //大于10，缩放
                        float scale = cuSpace / mPremove;
                        mCurrentMatrix.postScale(scale, scale, mMindP.x, mMindP.y);
                    }
                    if(isTwoPoint) {
                        mRotate = calRotate(event);
                        float rotate = mRotate - mSaveRotate;
                        mCurrentMatrix.postRotate(rotate, mScW/2, mScH/2);
                    }

                }
                break;

            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_POINTER_UP:
                isTwoPoint = false;
                current_mode = MODE_NONE;
                break;
        }
        setImageMatrix(mCurrentMatrix);
        return true;

    }

    private float calRotate(MotionEvent event) {
        float du_x = event.getX(0) - event.getX(1);
        float du_y = event.getY(0) - event.getY(1);
        double v = Math.atan2(du_y, du_x);
        return (float) Math.toDegrees(v);
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
