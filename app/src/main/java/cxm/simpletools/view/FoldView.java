package cxm.simpletools.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Region;
import android.os.Message;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Terry.Chen on 2015/10/15 10:23.
 * Description:折线翻页效果
 * Email:cxm_lmz@163.com
 */
public class FoldView extends View {
    private static final float VALUE_ADDED = 1 / 500F;// 精度附加值占比  
    private static final float BUFF_AREA = 1 / 50F;// 底部缓冲区域占比
    private final static float TEXTSIZE_NORMAL = 1 / 40F, TEXTSIZE_LARGE = 1 / 20F;
    private Context context;
    private float textSize_normal, textSize_large;
    private static final float AUTO_SLIDE_BL_V = 1 / 25F, AUTO_SLIDE_BR_V = 1 / 100F;// 滑动速度占比  

    private float pointX, pointY;

    private int width, height;

    private Path mPath, mFoldAndNextPath;
    private Paint mPaint;

    private Region mShortRegion, mCurrentRegion;
    private float mValueAdded;
    private float mBuffArea;

    private float mAutoBottom;

    private float mStart_BR_X, mStart_BR_Y;
    private boolean isSlide;

    private SlideHandler mSlideHander;

    private Slide mSlide;
    private float mAutoSlideV_BL, mAutoSlideV_BR;// 滑动速度

    private TextPaint textPaint;
    private List<Bitmap> mBitmaps;
    private float mDegrees;// 当前Y边长与Y轴的夹角  

    private Ratio mRatio;// 定义当前折叠边长


    private boolean isLastPage, isNextPage;

    private int pageIndex;

    private float mValidDis;

    private float mAutoLeft, mAutoRight;
    private float mClipX;
    public FoldView(Context context) {
        this(context, null);
    }

    public FoldView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initData();
    }
    /**
     * 枚举类定义长边短边 
     */
    private enum Ratio {
        LONG, SHORT
    }
    
    private void initData() {
        mPath = new Path();
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(5);
        mPaint.setColor(Color.BLACK);

        mFoldAndNextPath = new Path();

        mShortRegion = new Region();
        mCurrentRegion = new Region();
        mSlideHander = new SlideHandler();
        textPaint = new TextPaint(TextPaint.ANTI_ALIAS_FLAG | TextPaint.DITHER_FLAG | TextPaint.LINEAR_TEXT_FLAG);
        textPaint.setTextAlign(Paint.Align.CENTER);
//        setLayerType(LAYER_TYPE_SOFTWARE, null);

    }


    private void defaultDisplay(Canvas canvas) {
        canvas.drawColor(Color.WHITE);
        textPaint.setTextSize(textSize_large);
        textPaint.setColor(Color.RED);
        canvas.drawText("FBI waring", width / 2, height / 4, textPaint);
        textPaint.setTextSize(textSize_normal);
        textPaint.setColor(Color.BLACK);
        canvas.drawText("no much data display", width / 2, height / 3, textPaint);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        /* 
         * 如果数据为空则显示默认提示文本 
         */
        if (null == mBitmaps || mBitmaps.size() == 0) {
            defaultDisplay(canvas);
            return;
        }
        mPath.reset();
        mFoldAndNextPath.reset();
        
        //画底色
        canvas.drawColor(Color.WHITE);
        if (pointX == 0 && pointY == 0) {
            canvas.drawBitmap(mBitmaps.get(mBitmaps.size() - 1), 0, 0, null);
            return;
        }


        if (!mShortRegion.contains((int) pointX, (int) pointY)) {
            pointY = (float) (Math.sqrt(Math.pow(width, 2) - Math.pow(pointX, 2)) - height);
            pointY = Math.abs(pointY) + mValueAdded;
        }
        //缓冲区域判断 
        float area = height - mBuffArea;
        if (!isSlide && pointY >= area) {
            pointY = area;
        }
        
        //计算
        float mK = width - pointX;
        float mL = height - pointY;
        //存temp，
        float temp = (float) (Math.pow(mK, 2) + Math.pow(mL, 2));

        float size_long = temp / (2F * mL);
        float size_short = temp / (2F * mK);

        /* 
         * 根据长短边边长计算旋转角度并确定mRatio的值 
         */
        if (size_short < size_long) {
            mRatio = Ratio.SHORT;
            float sin = (mK - size_short) / size_short;
            mDegrees = (float) (Math.asin(sin) / Math.PI * 180);
        } else {
            mRatio = Ratio.LONG;
            float cos = mK / size_long;
            mDegrees = (float) (Math.acos(cos) / Math.PI * 180);
        }

        //生成路径
        mPath.moveTo(pointX, pointY);
        mFoldAndNextPath.moveTo(pointX, pointY);
        
        if (size_long > height) {
            // 计算……额……按图来AN边~  
            float an = size_long - height;

            // 三角形AMN的MN边  
            float largerTrianShortSize = an / (size_long - (height - pointY)) * (width - pointX);

            // 三角形AQN的QN边  
            float smallTrianShortSize = an / size_long * size_short;

            float topX1 = width - largerTrianShortSize;
            float topX2 = width - smallTrianShortSize;
            float btmX2 = width - size_short;
            /* 
             * 生成四边形路径 
             */
            mPath.lineTo(topX1, 0);
            mPath.lineTo(topX2, 0);
            mPath.lineTo(btmX2, height);
            mPath.close();
            
            //包含折叠和下一页的路径
            mFoldAndNextPath.lineTo(topX1, 0);
            mFoldAndNextPath.lineTo(width, 0);
            mFoldAndNextPath.lineTo(width, height);
            mFoldAndNextPath.lineTo(btmX2, height);
            mFoldAndNextPath.close();
            
        }else {

            float leftY = height - size_long;
            float btmX = width - size_short;
            mPath.lineTo(width, leftY);
            mPath.lineTo(btmX, height);
            mPath.close();

            mFoldAndNextPath.lineTo(width, leftY);
            mFoldAndNextPath.lineTo(width, height);
            mFoldAndNextPath.lineTo(btmX, height);
            mFoldAndNextPath.close();
            
        }

        drawBitmaps(canvas);
    }

    private void drawBitmaps(Canvas canvas) {
        isLastPage = false;
        //限制pageindex范围
        pageIndex = pageIndex < 0 ? 0 : pageIndex;
        pageIndex = pageIndex > mBitmaps.size() ? mBitmaps.size() : pageIndex;

        int start = mBitmaps.size() - 2 -pageIndex;
        int end = mBitmaps.size() - pageIndex;

        if (start < 0) {
            isLastPage = true;
            //重置位置
            // 并显示提示信息  
            Toast.makeText(context,"This is fucking lastest page",Toast.LENGTH_SHORT).show();
            start = 0;
            end = 1;
        }
        for (int i = start; i < end; i++) {
            canvas.save();
            if (!isLastPage && i == end - 1) {
                canvas.clipRect(0, 0, mClipX, height);
            }
            canvas.drawBitmap(mBitmaps.get(i), 0, 0, null);
            canvas.restore();
        }

        //定义区域
        Region foldRegion = null;
        Region nextRegion = null;
        //路径区域
        foldRegion = computeRegion(mPath);
        nextRegion = computeRegion(mFoldAndNextPath);
        //compute current region
        canvas.save();
        canvas.clipRegion(mCurrentRegion);
        canvas.clipRegion(nextRegion, Region.Op.DIFFERENCE);
        canvas.drawBitmap(mBitmaps.get(end - 1), 0, 0, null);
        canvas.restore();
        
        //计算折叠也的区域
        canvas.save();
        canvas.clipRegion(foldRegion);
        canvas.translate(pointX, pointY);
        /* 
         * 根据长短边标识计算折叠区域图像 
         */
        if (mRatio == Ratio.SHORT) {
            canvas.rotate(90 - mDegrees);
            canvas.translate(0, -height);
            canvas.scale(-1, 1);
            canvas.translate(-height, 0);
        } else {
            canvas.rotate(-(90 - mDegrees));
            canvas.translate(-height, 0);
            canvas.scale(1, -1);
            canvas.translate(0, -height);
        }
        canvas.drawBitmap(mBitmaps.get(end - 1), 0, 0, null);
        canvas.restore();
        
        //计算下一页区域
        canvas.save();
        canvas.clipRegion(nextRegion);
        canvas.clipRegion(foldRegion, Region.Op.DIFFERENCE);
        canvas.drawBitmap(mBitmaps.get(start), 0, 0, null);
        canvas.restore();
        
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        width = w;
        height = h;

        if (null != mBitmaps) {
            initBitmaps();
        }

        textSize_normal = height * TEXTSIZE_NORMAL;
        textSize_large = height * TEXTSIZE_LARGE;
        mValueAdded = VALUE_ADDED * height;
        mBuffArea = BUFF_AREA * height;
        
        mAutoBottom = 4 / 5F * height;
        mAutoRight = 4 / 5F * width;
        mAutoLeft = 1 / 5F * width;


        mAutoLeft = 1 / 5F * width;
        mAutoRight = 4 / 5F * width;
        mValidDis = 1 / 100F * width;
        
        mCurrentRegion = new Region(0, 0, width, height);
        computeShotSizeRegion();
        
        /* 
         * 计算滑动速度 
         */
        mAutoSlideV_BL = width * AUTO_SLIDE_BL_V;
        mAutoSlideV_BR = width * AUTO_SLIDE_BR_V;
    }

    private void initBitmaps() {
        List<Bitmap> tempBs = new ArrayList<Bitmap>();
        for (int i = mBitmaps.size() - 1; i >= 0; i--) {
            Bitmap bitmap = Bitmap.createScaledBitmap(mBitmaps.get(i), width, height, true);
            tempBs.add(bitmap);
        }
        mBitmaps = tempBs;
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

    /**
     * 计算path Region
     *
     * @param path
     * @return
     */
    public Region computeRegion(Path path) {
        Region region = new Region();
        RectF rectf = new RectF();
        path.computeBounds(rectf, true);
        region.setPath(path, new Region((int) rectf.left, (int) rectf.top, (int) rectf.right, (int) rectf.bottom));
        return region;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                isSlide = false;
                /* 
             * 如果事件点位于回滚区域 
             */
                if (x < mAutoLeft) {
                    // 那就不翻下一页了而是上一页  
                    isNextPage = false;
                    pageIndex--;
                    pointX = x;
                    pointY = y;
                    invalidate();
                }
                break;
            case MotionEvent.ACTION_MOVE:
                invalidate();
                break;

            case MotionEvent.ACTION_UP:

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

        if (!isLastPage && isNextPage && (pointX - mAutoSlideV_BL <= -width)) {
            pointX = -width;
            pointY = height;
            pageIndex++;
            invalidate();
        } else if (mSlide == Slide.SLIDE_RIGHT_BOTTOM && pointX < width) {
            pointX += 20;
            pointY = mStart_BR_Y + ((pointX - mStart_BR_X) * (height - mStart_BR_Y)) / (width - mStart_BR_X);
            mSlideHander.sleep(25);
        } else if (mSlide == Slide.SLIDE_LEFT_BOTTOM && pointX > -width) {
            pointX -= 30;
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
