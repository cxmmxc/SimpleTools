package cxm.simpletools.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;

import cxm.simpletools.R;

/**
 * Created by Terry.Chen on 2015/10/12 10:09.
 * Description:图层实验
 * Email:cxm_lmz@163.com
 */
public class LayerView extends View {

//    private Paint mPaint, mTwoPaint;

    private Bitmap mBitmap;
    
    private int width, height;

    public LayerView(Context context) {
        this(context, null);
    }

    public LayerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initData(context);
    }

    private void initData(Context context) {
//        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
//        mPaint.setColor(Color.GREEN);
//        mTwoPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
//        mTwoPaint.setColor(Color.RED);

        //---------------------<b>
        mBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.lakesi_1);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
//        super.onSizeChanged(w, h, oldw, oldh);
        width = w;
        height = h;
//        mBitmap = Bitmap.createScaledBitmap(mBitmap, width, height, true);
    }

    @Override
    protected void onDraw(Canvas canvas) {
//        canvas.drawRect(10, 10, 150, 150, mPaint);
//        canvas.saveLayerAlpha(15, 15, 140, 140, 128, Canvas.ALL_SAVE_FLAG);
//        canvas.rotate(30);
//
//        canvas.drawRect(15, 15, 140, 140, mTwoPaint);
//        canvas.restore();

        canvas.save(Canvas.MATRIX_SAVE_FLAG);
//        canvas.scale(0.7f, 0.7f, width/2, height/2);
        canvas.skew(0.3f, 1.0f);
        canvas.drawBitmap(mBitmap, 0, 0, null);
        canvas.restore();
    }
}
