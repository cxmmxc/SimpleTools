package cxm.simpletools.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;

import cxm.simpletools.R;
import cxm.simpletools.tool.MeasureTool;

/**
 * Created by Terry.Chen on 2015/9/29 10:34.
 * Description: matrix练习
 * Email:cxm_lmz@163.com
 */
public class MatrixView extends View {
    
    private Paint mPaint;
    
    private int sx,sy;
    
    public MatrixView(Context context) {
        this(context, null);
    }

    public MatrixView(Context context, AttributeSet attrs) {
        super(context, attrs);
        
        initData(context);
    }

    private void initData(Context context) {
        int[] screenWH = MeasureTool.getScreenWH((Activity) context);
        sx = screenWH[0];
        sy = screenWH[1];
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        Matrix matrix = new Matrix();
        Bitmap meizi = BitmapFactory.decodeResource(getResources(), R.mipmap.shader);
        matrix.setTranslate( meizi.getHeight()/2, 0);
        matrix.preRotate(80f);
        BitmapShader bitmapShader = new BitmapShader(meizi, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        bitmapShader.setLocalMatrix(matrix);
        mPaint.setShader(bitmapShader);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawRect(0, 0, sx, sy, mPaint);
    }
}
