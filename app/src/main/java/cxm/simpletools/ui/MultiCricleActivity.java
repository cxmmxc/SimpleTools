package cxm.simpletools.ui;

import android.app.Activity;
import android.os.Bundle;

import cxm.simpletools.view.MultiCricleView;

/**
 * Created by Terry.Chen on 2015/10/8 11:24.
 * Description:
 * Email:cxm_lmz@163.com
 */
public class MultiCricleActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new MultiCricleView(this));
    }
}
