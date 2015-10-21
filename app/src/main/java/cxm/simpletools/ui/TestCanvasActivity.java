package cxm.simpletools.ui;

import android.app.Activity;
import android.os.Bundle;

import cxm.simpletools.view.CanvasTestView;

/**
 * Created by Terry.Chen on 2015/10/20 11:07.
 * Description:
 * Email:cxm_lmz@163.com
 */
public class TestCanvasActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new CanvasTestView(this));
    }
}
