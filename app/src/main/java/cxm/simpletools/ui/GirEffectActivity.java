package cxm.simpletools.ui;

import android.app.Activity;
import android.os.Bundle;

import cxm.simpletools.view.GirlEffectView;

/**
 * Created by Terry.Chen on 2015/9/25 15:05.
 * Description:
 * Email:cxm_lmz@163.com
 */
public class GirEffectActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new GirlEffectView(this));
    }
}
