package cxm.simpletools.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

import com.lidroid.xutils.util.LogUtils;

/**
 * Created by lmz_cxm on 2015/9/22.
 * 创建TextView的画笔
 */
public class TextCusView extends View {

    private static final String TEXT = "ap爱哥ξτβбпшㄎㄊěǔぬも┰┠№＠↓бпшㄎㄊěǔぬбпшㄎㄊěǔぬ";

    //-----
    private Paint mFirPaint;
    private Paint.FontMetrics mFrontMetric;

    private TextPaint mTextPaint;

    private StaticLayout mStaticLayout;

    public TextCusView(Context context) {
        this(context, null);
    }

    public TextCusView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TextCusView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initData(context);
    }

    private void initData(Context context) {
        mFirPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mFirPaint.setStyle(Paint.Style.STROKE);
        mFirPaint.setTextSize(80);
        mFirPaint.setColor(Color.BLACK);

        mFrontMetric = mFirPaint.getFontMetrics();

        LogUtils.i("top--"+mFrontMetric.top+",bottom--"+mFrontMetric.bottom);

        //----------------
        mTextPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setTextSize(80);
        mTextPaint.setColor(Color.BLACK);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//        canvas.drawText(TEXT,0 ,Math.abs(mFrontMetric.top) ,mFirPaint);
        mStaticLayout = new StaticLayout(TEXT, mTextPaint, canvas.getWidth(), Layout.Alignment.ALIGN_CENTER, 1.0f, 0.0f, false);
        mStaticLayout.draw(canvas);
        canvas.restore();
    }
}
