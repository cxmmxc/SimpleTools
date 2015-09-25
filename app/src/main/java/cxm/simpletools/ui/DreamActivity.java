package cxm.simpletools.ui;

import android.app.Activity;
import android.os.Bundle;

import cxm.simpletools.view.DreamEffectView;

/**
 * Created by Terry.Chen on 2015/9/25 16:06.
 * Description:
 * Email:cxm_lmz@163.com
 */
public class DreamActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new DreamEffectView(this));
    }
}
