package cxm.simpletools.ui;

import android.app.Activity;
import android.os.Bundle;

import cxm.simpletools.view.HeartView;

/**
 * Created by Terry.Chen on 2015/9/24 14:22.
 * Description:
 * Email:cxm_lmz@163.com
 */
public class HearActivity extends Activity {
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new HeartView(this));
    }
}
