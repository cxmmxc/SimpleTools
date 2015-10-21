package cxm.simpletools.ui;

import android.app.Activity;
import android.os.Bundle;

import cxm.simpletools.R;
import cxm.simpletools.view.DragScaleView;

/**
 * Created by lmz_cxm on 2015/10/5.
 */
public class DragActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drag_layout);
    }
}
