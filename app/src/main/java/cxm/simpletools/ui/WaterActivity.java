package cxm.simpletools.ui;

import android.app.Activity;
import android.os.Bundle;

import cxm.simpletools.view.CupWaterView;

/**
 * Created by Terry.Chen on 2015/10/10 10:22.
 * Description:
 * Email:cxm_lmz@163.com
 */
public class WaterActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new CupWaterView(this));
    }
}
