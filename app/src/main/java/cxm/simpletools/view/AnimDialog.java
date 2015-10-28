package cxm.simpletools.view;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;

import cxm.simpletools.R;

/**
 * Created by Terry.Chen on 2015/10/22 10:22.
 * Description:
 * Email:cxm_lmz@163.com
 */
public class AnimDialog extends Dialog {

    private Bitmap mBitmap;
    
    public AnimDialog(Context context) {
        super(context);
        initData(context);
    }

    private void initData(Context context) {
        mBitmap = BitmapFactory.decodeResource(context.getResources(), R.mipmap.lakesi_1);
        ImageView imageView = new ImageView(context);
        imageView.setImageBitmap(mBitmap);
        this.setContentView(imageView);
    }
    
    public int[] getViewSize() {
        if (null == mBitmap) {
            return null;
        }
        return new int[]{mBitmap.getWidth(), mBitmap.getHeight()};
    }
    
}
