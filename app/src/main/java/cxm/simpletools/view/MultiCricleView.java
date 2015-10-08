package cxm.simpletools.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Terry.Chen on 2015/10/8 10:57.
 * Description: 自定义画图
 * Email:cxm_lmz@163.com
 */
public class MultiCricleView extends View {

    private static final float STROKE_WIDTH = 1F / 256F, // 描边宽度占比  
            SPACE = 1F / 64F,// 大圆小圆线段两端间隔占比  
            LINE_LENGTH = 3F / 32F, // 线段长度占比  
            CRICLE_LARGER_RADIU = 3F / 32F,// 大圆半径  
            CRICLE_SMALL_RADIU = 5F / 64F,// 小圆半径  
            ARC_RADIU = 1F / 8F,// 弧半径  
            ARC_TEXT_RADIU = 5F / 32F;// 弧围绕文字半径 

    private float strokeWidth, circleLargeRadius, lineLength,
            spaceDis, circleSmallRadius, arcRadius, arcTextRadius;

    private int size;

    private Paint strokePaint, textPaint;

    private float textOffSet;

    private float ccx, ccy;

    public MultiCricleView(Context context) {
        this(context, null);
    }

    public MultiCricleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initPaint(context);
    }

    /**
     * 初始化画笔
     *
     * @param context
     */
    private void initPaint(Context context) {
        strokePaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        strokePaint.setStyle(Paint.Style.STROKE);
        strokePaint.setStrokeCap(Paint.Cap.ROUND);
        strokePaint.setColor(Color.WHITE);

        textPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG | Paint.SUBPIXEL_TEXT_FLAG);
        textPaint.setColor(Color.WHITE);
        textPaint.setTextSize(30);
        textPaint.setTextAlign(Paint.Align.CENTER);
        textOffSet = (textPaint.descent() + textPaint.ascent()) / 2;

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //强制长宽一致
        super.onMeasure(widthMeasureSpec, widthMeasureSpec);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        size = w;
        calParams();
//        super.onSizeChanged(w, h, oldw, oldh);
    }

    //参数计算
    private void calParams() {
        strokeWidth = size * STROKE_WIDTH;
        circleLargeRadius = size * CRICLE_LARGER_RADIU;
        lineLength = size * LINE_LENGTH;
        spaceDis = size * SPACE;
        circleSmallRadius = size * CRICLE_SMALL_RADIU;
        arcRadius = size * ARC_RADIU;
        arcTextRadius = size * ARC_TEXT_RADIU;

        ccx = size / 2;
        ccy = size / 2 + circleLargeRadius;
        setParm();
    }

    /**
     * 设置参数
     */
    private void setParm() {
        strokePaint.setStrokeWidth(strokeWidth);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(0xFFF29B76);
        canvas.drawCircle(ccx, ccy, circleLargeRadius, strokePaint);
        canvas.drawText("CircleCenter", ccx, ccy - textOffSet, textPaint);
        drawTopLeft(canvas);

    }

    private void drawTopLeft(Canvas canvas) {
        canvas.save();
        canvas.translate(ccx, ccy);
        canvas.rotate(-30);

        canvas.drawLine(0, -circleLargeRadius, 0, -circleLargeRadius * 2, strokePaint);
        canvas.drawCircle(0, -circleLargeRadius * 3, circleLargeRadius, strokePaint);
        canvas.drawLine(0, -circleLargeRadius * 4, 0, -circleLargeRadius * 5, strokePaint);
        canvas.drawCircle(0, -circleLargeRadius * 6, circleLargeRadius, strokePaint);

        canvas.restore();
        canvas.drawText("TopTwo", ccx - (float) (3.0f * circleLargeRadius * Math.sin(Math.toRadians(30))), ccy - (float) (3.0f * circleLargeRadius * Math.cos(Math.toRadians(30))) - textOffSet, textPaint);
    }
}
