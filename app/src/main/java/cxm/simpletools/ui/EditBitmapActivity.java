package cxm.simpletools.ui;

import android.app.Activity;
import android.os.Bundle;

import cxm.simpletools.view.EditView;

/**
 * Created by lmz_cxm on 2015/11/10.
 */
public class EditBitmapActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EditView view = new EditView(this, null);
        setContentView(view);
//        view.setBitmap(BitmapFactory.decodeResource(getResources(), R.mipmap.t6));
    }
}
