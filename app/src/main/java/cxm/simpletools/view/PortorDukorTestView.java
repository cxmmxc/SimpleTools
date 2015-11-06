package cxm.simpletools.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import com.lidroid.xutils.util.LogUtils;

import cxm.simpletools.R;
import cxm.simpletools.tool.MeasureTool;

/**
 * Created by Terry.Chen on 2015/11/3 15:39.
 * Description:测试PortorDuffer功能
 * Email:cxm_lmz@163.com
 */
public class PortorDukorTestView extends View {
    
    private Bitmap mBigBitmap, mSmallBitmap;
    
    private int mScW, mScH;
    
    private Context mContext;
    
    private Paint mPaint,mTwoPaint;
    
    private Rect mFirRectf, mSecRectf, mThirdRect,mFourRect;
    
    private Bitmap mTestBitmap;
    
    private Canvas mTestCanvas;

    private int mRadius;
    private Paint mRoundPaint;//绘画和前景蒙版层相交圆的paint
    private Paint mFgroundPaint;//绘画前景蒙版层的paint
    public PortorDukorTestView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        initData();
    }

    private void initData() {
        int[] screenWH = MeasureTool.getScreenWH((Activity) mContext);
        mScW = screenWH[0];
        mScH = screenWH[1];
        mBigBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.lakesi_1);
        mSmallBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.t1);

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

        mTwoPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTwoPaint.setColor(Color.YELLOW);
        
        
        mFirRectf = new Rect(0, 0,mBigBitmap.getWidth(), mBigBitmap.getHeight());
        mSecRectf = new Rect(0, 0, mBigBitmap.getWidth()+300, mBigBitmap.getHeight()+300);

        mThirdRect = new Rect(200, 200, mSmallBitmap.getWidth() + 200, mSmallBitmap.getHeight() + 200);
        mFourRect = new Rect(200, 200, mSmallBitmap.getWidth() + 400, mSmallBitmap.getHeight() + 400);
        
        mTestBitmap = Bitmap.createBitmap(mScW, mScH, Bitmap.Config.ARGB_8888);
        mTestCanvas = new Canvas(mTestBitmap);
        LogUtils.v("initData");

        mRadius = mScW*1/3;

        mRoundPaint = new Paint();
        mFgroundPaint = new Paint();
        
        mRoundPaint.setStrokeWidth(mRadius);
        mRoundPaint.setAntiAlias(true);
        mRoundPaint.setARGB(255, 0, 0, 0);

        mFgroundPaint.setAntiAlias(true);
        mFgroundPaint.setARGB(198, 0, 0, 0);
        mFgroundPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.XOR));

    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawColor(Color.GRAY);
//        canvas.drawBitmap(mSmallBitmap, 0, 0, null);
        int count = canvas.saveLayer(0, 0, mScW, mScH, mPaint, Canvas.ALL_SAVE_FLAG);
        canvas.drawBitmap(mBigBitmap, mFirRectf, mSecRectf, mPaint);
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SCREEN));
        canvas.drawBitmap(mSmallBitmap, mThirdRect, mFourRect, mPaint);
        mPaint.setXfermode(null);
//        canvas.drawCircle(mScW / 2, mScH / 2, mRadius, mRoundPaint);
//        canvas.drawRect(mSecRectf, mFgroundPaint);
        canvas.restoreToCount(count);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        LogUtils.v("onSizeChanged");
    }
}
