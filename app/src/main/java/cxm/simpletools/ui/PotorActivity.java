package cxm.simpletools.ui;

import android.app.Activity;
import android.os.Bundle;

import cxm.simpletools.view.PortorDukorTestView;

/**
 * Created by Terry.Chen on 2015/11/3 15:41.
 * Description: 测试Potor
 * Email:cxm_lmz@163.com
 */
public class PotorActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(new LoadingTextView(this, null));
        setContentView(new PortorDukorTestView(this, null));
    }
}
