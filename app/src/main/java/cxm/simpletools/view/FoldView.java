package cxm.simpletools.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Region;
import android.os.Message;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by Terry.Chen on 2015/10/15 10:23.
 * Description:折线翻页效果
 * Email:cxm_lmz@163.com
 */
public class FoldView extends View {
    private static final float VALUE_ADDED = 1 / 500F;// 精度附加值占比  
    private static final float BUFF_AREA = 1 / 50F;// 底部缓冲区域占比
    private Context context;

    private float pointX, pointY;

    private int width, height;

    private Path mPath;
    private Paint mPaint;

    private Region mShortRegion;
    private float mValueAdded;
    private float mBuffArea;

    private float mAutoBottom, mAutoRight, mAutoLeft;

    private float mStart_BR_X, mStart_BR_Y;
    private boolean isSlide;

    private SlideHandler mSlideHander;

    private Slide mSlide;

    public FoldView(Context context) {
        this(context, null);
    }

    public FoldView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initData();
    }

    private void initData() {
        mPath = new Path();
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(5);
        mPaint.setColor(Color.BLACK);

        mShortRegion = new Region();
        mSlideHander = new SlideHandler();
//        setLayerType(LAYER_TYPE_SOFTWARE, null);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        mPath.reset();
        //画底色
        canvas.drawColor(Color.WHITE);
        if (pointX == 0 && pointY == 0) {
            return;
        }
        //计算
        float mK = width - pointX;
        float mL = height - pointY;
        //存temp，
        float temp = (float) (Math.pow(mK, 2) + Math.pow(mL, 2));

        float size_long = temp / (2F * mL);
        float size_short = temp / (2F * mK);

        if (!mShortRegion.contains((int) pointX, (int) pointY)) {
            pointY = (float) (Math.sqrt(Math.pow(width, 2) - Math.pow(pointX, 2)) - height);
            pointY = Math.abs(pointY) + mValueAdded;
        }
        //缓冲区域判断 
        float area = height - mBuffArea;
        if (!isSlide && pointY >= area) {
            pointY = area;
        }


        //生成路径
        mPath.moveTo(pointX, pointY);

        if (size_long > height) {
            // 计算……额……按图来AN边~  
            float an = size_long - height;

            // 三角形AMN的MN边  
            float largerTrianShortSize = an / (size_long - (height - pointY)) * (width - pointX);

            // 三角形AQN的QN边  
            float smallTrianShortSize = an / size_long * size_short;
            /* 
             * 生成四边形路径 
             */
            mPath.lineTo(width - largerTrianShortSize, 0);
            mPath.lineTo(width - smallTrianShortSize, 0);
            mPath.lineTo(width - size_short, height);
            mPath.close();
        }else {
            mPath.lineTo(width, height - size_long);
            mPath.lineTo(width - size_short, height);
            mPath.close();
        }

        canvas.drawPath(mPath, mPaint);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        width = w;
        height = h;
        mValueAdded = VALUE_ADDED * height;
        mBuffArea = BUFF_AREA * height;
        mAutoBottom = 4 / 5F * height;
        mAutoRight = 4 / 5F * width;
        mAutoLeft = 1 / 5F * width;
        computeShotSizeRegion();
    }

    /**
     * 计算有效区域
     */
    private void computeShotSizeRegion() {
        Path path = new Path();
        RectF rectF = new RectF();
        path.addCircle(0, height, width, Path.Direction.CCW);
        path.computeBounds(rectF, true);
        mShortRegion.setPath(path, new Region((int) rectF.left, (int) rectF.top, (int) rectF.right, (int) rectF.bottom));
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        pointX = event.getX();
        pointY = event.getY();
        switch (event.getAction() & MotionEvent.ACTION_MASK) {

            case MotionEvent.ACTION_MOVE:
                invalidate();
                break;

            case MotionEvent.ACTION_UP:
                float x = event.getX();
                float y = event.getY();
                if (x > mAutoRight && y > mAutoBottom) {
                    mSlide = Slide.SLIDE_RIGHT_BOTTOM;
                    justSlide(x, y);
                }
                if (x < mAutoLeft) {
                    mSlide = Slide.SLIDE_LEFT_BOTTOM;
                    justSlide(x, y);
                }
                break;
        }
        return true;
    }

    private void justSlide(float x, float y) {
        mStart_BR_X = x;
        mStart_BR_Y = y;
        isSlide = true;
        slide();
    }

    private void slide() {
        if (!isSlide) {
            return;
        }
        if (mSlide == Slide.SLIDE_RIGHT_BOTTOM && pointX < width) {
            pointX += 20;
            pointY = mStart_BR_Y + ((pointX - mStart_BR_X) * (height - mStart_BR_Y)) / (width - mStart_BR_X);
            mSlideHander.sleep(25);
        }
        if (mSlide == Slide.SLIDE_LEFT_BOTTOM && pointX > -width) {
            pointX -= 20;
            // 并根据x坐标的值重新计算y坐标的值  
            pointY = mStart_BR_Y + ((pointX - mStart_BR_X) * (height - mStart_BR_Y)) / (-width - mStart_BR_X);
            mSlideHander.sleep(25);
        }
    }

    private class SlideHandler extends android.os.Handler {
        @Override
        public void handleMessage(Message msg) {
            FoldView.this.slide();
            FoldView.this.invalidate();
        }

        public void sleep(long delayTime) {
            this.removeMessages(0);
            sendMessageDelayed(obtainMessage(0), delayTime);
        }
    }

    public void stopSlide() {
        isSlide = false;
    }

    private enum Slide {
        SLIDE_LEFT_BOTTOM, SLIDE_RIGHT_BOTTOM;
    }
}
