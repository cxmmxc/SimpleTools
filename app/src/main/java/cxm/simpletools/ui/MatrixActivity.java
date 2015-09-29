package cxm.simpletools.ui;

import android.app.Activity;
import android.os.Bundle;

import cxm.simpletools.view.MatrixView;

/**
 * Created by Terry.Chen on 2015/9/29 10:47.
 * Description:
 * Email:cxm_lmz@163.com
 */
public class MatrixActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new MatrixView(this));
    }
}
