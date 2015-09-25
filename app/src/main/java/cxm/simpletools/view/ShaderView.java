package cxm.simpletools.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;

import cxm.simpletools.R;
import cxm.simpletools.tool.MeasureTool;

/**
 * Created by Terry.Chen on 2015/9/24 15:09.
 * Description：着色器
 * Email:cxm_lmz@163.com
 */
public class ShaderView extends View {

    private final static int RECT_SIZE = 400;
    private Paint mPaint;
    
    private int scW,scH;
    
    private int left, top, bottom, right;
    
    public ShaderView(Context context) {
        this(context, null);
    }

    public ShaderView(Context context, AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public ShaderView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initData(context);
    }

    private void initData(Context context) {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);

        int[] screenWH = MeasureTool.getScreenWH((Activity) context);
        scW = screenWH[0];
        scH = screenWH[1];

        left = scW / 2 - RECT_SIZE;
        right = scW /2 +RECT_SIZE;
        top = scH /2 - RECT_SIZE;
        bottom = scH / 2 + RECT_SIZE;

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.lakesi_1);
        
//        mPaint.setShader(new BitmapShader(bitmap, Shader.TileMode.MIRROR, Shader.TileMode.CLAMP));
//        mPaint.setShader(new SweepGradient(scW/2, scH/2, Color.RED, Color.YELLOW));
        mPaint.setShader(new RadialGradient(scW/2, scH/2, RECT_SIZE, Color.RED, Color.YELLOW, Shader.TileMode.CLAMP));
    }
    

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawRect(left, top, right, bottom, mPaint);
    }
}
