package cxm.simpletools.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import cxm.simpletools.R;
import cxm.simpletools.tool.MeasureTool;

/**
 * Created by Terry.Chen on 2015/11/2 10:09.
 * Description:模拟QQ选择头像的功能
 * Email:cxm_lmz@163.com
 */
public class QqHeadView extends View {
    
    private Paint mFrontPaint, mRoundPaint, mPaint;//圆形透明蒙板层
    
    private Canvas mFrontCanvas;
    
    private int mCircleRadius, mRingWidth;
    
    private Point mCenterPoint;
    
    private int mScreenW, mScreenH;
    
    private RectF mRectf;
    
    private Bitmap mBitmap;
    
    private Bitmap mFgBitmap;
    
    public QqHeadView(Context context) {
        this(context, null);
    }

    public QqHeadView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initData(context);
    }

    private void initData(Context context) {

        mRingWidth = 4;
        
        mCenterPoint = new Point();
        
        mFrontPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mFrontPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.XOR));
        mFrontPaint.setARGB(190, 0, 0, 0);

        mRoundPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mRoundPaint.setStrokeWidth(mCircleRadius);
        mRoundPaint.setARGB(255, 0, 0, 0);

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setARGB(255, 255, 225, 255);
        mPaint.setStrokeWidth(mRingWidth);

        
        int[] screenWH = MeasureTool.getScreenWH((Activity) context);
        mScreenW = screenWH[0];
        mScreenH = screenWH[1];
        mCenterPoint.x = mScreenW / 2;
        mCenterPoint.y = mScreenH / 2;

        mFgBitmap = Bitmap.createBitmap(mScreenW, mScreenH, Bitmap.Config.ARGB_8888);
        
        mFrontCanvas = new Canvas(mFgBitmap);
        mCircleRadius = mScreenW * 1 / 4;

        mRectf = new RectF(0, 0, mScreenW, mScreenH);

        mBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.demo);
        
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.BLACK);
        canvas.drawBitmap(mBitmap, mScreenW / 2 - mBitmap.getWidth() / 2, mScreenH / 2 - mBitmap.getHeight() / 2, mPaint);

        canvas.drawCircle(mCenterPoint.x, mCenterPoint.y, mCircleRadius, mPaint);
        mFrontCanvas.drawCircle(mCenterPoint.x, mCenterPoint.y, mCircleRadius, mRoundPaint);
        mFrontCanvas.drawRect(mRectf, mFrontPaint);
        canvas.drawBitmap(mFgBitmap, null, mRectf, mPaint);
    }
}
