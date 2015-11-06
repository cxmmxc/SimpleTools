package cxm.simpletools.ui;

import android.app.Activity;
import android.os.Bundle;

import cxm.simpletools.view.QqHeadView;

/**
 * Created by Terry.Chen on 2015/11/2 10:10.
 * Description:
 * Email:cxm_lmz@163.com
 */
public class QQHeadSelectActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new QqHeadView(this));
    }
}
