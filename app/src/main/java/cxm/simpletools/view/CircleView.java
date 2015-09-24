package cxm.simpletools.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.lidroid.xutils.util.LogUtils;

import cxm.simpletools.R;
import cxm.simpletools.tool.MeasureTool;


/**
 * Created by Terry.Chen on 2015/9/10 15:58.
 * Description:
 * Email:cxm_lmz@163.com
 */
public class CircleView extends View implements Runnable {

    private Canvas mCanvas;

    private Paint mPaint;

    private Context mContext;

    private Bitmap bitmap; //位图

    private int x, y; //位图绘制时候左上角的坐标 

    private PorterDuffXfermode duffXfermode;


    private int progress;

    private int radius;

    //进行橡皮擦效果画图 -------
    private Canvas mEraserCanvas;

    private int mScreenWidth, mScreenHeight;

    private Bitmap mBgBitmap, mFrBitmap;

    private Paint mEraserPaint;

    private Path mEraserPath;

    private PorterDuffXfermode mEraserMode;
    
    private final static int MIN_MOVE_DIS = 5;
    
    private float preX, preY;

    //橡皮擦画图结束 --------

    public CircleView(Context context) {
        this(context, null);
    }

    public CircleView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
//        initView(context);
        cal(context);
        initData(context);
    }

    //初始化各种信息
    private void initData(Context context) {

        mEraserPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        mEraserPaint.setARGB(0, 255, 0, 0);
        mEraserPaint.setStyle(Paint.Style.STROKE);
        mEraserPaint.setStrokeWidth(80);
        mEraserPaint.setStrokeCap(Paint.Cap.ROUND);
        mEraserPaint.setStrokeJoin(Paint.Join.ROUND);
        
        mFrBitmap = Bitmap.createBitmap(mScreenWidth, mScreenHeight, Bitmap.Config.ARGB_8888);
        mBgBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.lakesi);
        mBgBitmap = Bitmap.createScaledBitmap(mBgBitmap, mScreenWidth, mScreenHeight, true);
        mEraserPath = new Path();

        mEraserMode = new PorterDuffXfermode(PorterDuff.Mode.DST_IN);

        mEraserPaint.setXfermode(mEraserMode);

        mEraserCanvas = new Canvas(mFrBitmap);

        mEraserCanvas.drawColor(0xFF808080);

    }

    //进行屏幕计算
    private void cal(Context context) {
        int[] screenWH = MeasureTool.getScreenWH((Activity) context);
        mScreenWidth = screenWH[0];
        mScreenHeight = screenWH[1];
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//        canvas.drawCircle(MeasureTool.getScreenWH((Activity)mContext)[0]/2, MeasureTool.getScreenWH((Activity)mContext)[1]/2, 300, mPaint);
//        canvas.drawArc(10,10, 40, 40, 0, 90, true, mPaint);
//        RectF rectF = new RectF(10, 10, 440, 440);
//        canvas.drawArc(rectF, 0, progress, true, mPaint );
//        canvas.drawCircle(600, 600 , radius, mPaint);
//        canvas.drawBitmap(bitmap, x, y, mPaint);
        //----橡皮擦的效果
        canvas.drawBitmap(mBgBitmap, 0, 0, null);
        canvas.drawBitmap(mFrBitmap, 0, 0, null);
        mEraserCanvas.drawPath(mEraserPath, mEraserPaint);
        //----橡皮擦的效果
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
//        return super.onTouchEvent(event);
        float x = event.getX();
        float y = event.getY();
        
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mEraserPath.reset();
                mEraserPath.moveTo(x, y);
                preX = x;
                preY = y;
                break;
            case MotionEvent.ACTION_MOVE:
                float abs_x = Math.abs(x - preX);
                float abs_y = Math.abs(y - preY);
                if(abs_x >= MIN_MOVE_DIS || abs_y >= MIN_MOVE_DIS) {
                    mEraserPath.quadTo(preX, preY, x, y); 
                    preX = x;
                    preY = y;
                }

                break;
            case MotionEvent.ACTION_UP:

                break;
        }

        invalidate();
        return true;
    }


    public void setCurrentProgress(int progress) {
        this.progress = progress;
        invalidate();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int mode = MeasureSpec.getMode(widthMeasureSpec);
        int size = MeasureSpec.getSize(widthMeasureSpec);
    }

    /**
     * Starts executing the active part of the class' code. This method is
     * called when a thread is started that has been created with a class which
     * implements {@code Runnable}.
     */
    @Override
    public void run() {
//        while (true) {
//            if(radius <500) {
//                radius ++;
//            }else {
//                radius = 0;
//            }
//            postInvalidate();
//            try {
//                Thread.sleep(50);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
    }


    private void initView(Context context) {
        mContext = context;
        mCanvas = new Canvas();
        mPaint = new Paint();

        ColorMatrix colorMatrix = new ColorMatrix(new float[]{
                1.438F, -0.122F, -0.016F, 0, -0.03F,
                -0.062F, 1.378F, -0.016F, 0, 0.05F,
                -0.062F, -0.122F, 1.483F, 0, -0.02F,
                0, 0, 0, 1, 0,
        });
        bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.lakesi);
        mPaint.setColor(Color.argb(255, 255, 128, 103));
//        mPaint.setColorFilter(new ColorMatrixColorFilter(colorMatrix));
//        mPaint.setColorFilter(new PorterDuffColorFilter(Color.RED, PorterDuff.Mode.LIGHTEN));
//        mPaint.setColorFilter(new LightingColorFilter(0xFFFF00FF, 0x00BBBB00));
        mPaint.setColorFilter(null);
        mPaint.setAntiAlias(true);
//        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStrokeWidth(1);
        mPaint.setStyle(Paint.Style.FILL);

        x = MeasureTool.getScreenWH((Activity) mContext)[0] / 2 - bitmap.getWidth() / 2;
        y = MeasureTool.getScreenWH((Activity) mContext)[1] / 2 - bitmap.getHeight() / 2;
//        x = 10;
//        y = 10;
        LogUtils.v(x + "---" + y);
    }
}
