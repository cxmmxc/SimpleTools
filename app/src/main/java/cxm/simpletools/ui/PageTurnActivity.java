package cxm.simpletools.ui;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import java.util.ArrayList;

import cxm.simpletools.R;
import cxm.simpletools.view.PageTurnView;

/**
 * Created by Terry.Chen on 2015/10/13 10:57.
 * Description:
 * Email:cxm_lmz@163.com
 */
public class PageTurnActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PageTurnView view = new PageTurnView(this);
        Bitmap bitmap1 = BitmapFactory.decodeResource(getResources(), R.mipmap.t1);
        Bitmap bitmap2 = BitmapFactory.decodeResource(getResources(), R.mipmap.t2);
        Bitmap bitmap3 = BitmapFactory.decodeResource(getResources(), R.mipmap.t3);
        Bitmap bitmap4 = BitmapFactory.decodeResource(getResources(), R.mipmap.t4);
        Bitmap bitmap5 = BitmapFactory.decodeResource(getResources(), R.mipmap.lakesi);
        Bitmap bitmap6 = BitmapFactory.decodeResource(getResources(), R.mipmap.t6);
        ArrayList<Bitmap> bitmaps = new ArrayList<Bitmap>();
        bitmaps.add(bitmap1);
        bitmaps.add(bitmap2);
        bitmaps.add(bitmap3);
        bitmaps.add(bitmap4);
        bitmaps.add(bitmap5);
        bitmaps.add(bitmap6);
        setContentView(view);
        view.setBitmaps(bitmaps);
    }
}
