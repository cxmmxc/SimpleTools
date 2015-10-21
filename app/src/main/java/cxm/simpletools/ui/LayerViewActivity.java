package cxm.simpletools.ui;

import android.app.Activity;
import android.os.Bundle;

import cxm.simpletools.view.LayerView;

/**
 * Created by Terry.Chen on 2015/10/12 10:15.
 * Description:
 * Email:cxm_lmz@163.com
 */
public class LayerViewActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new LayerView(this));
    }
}
