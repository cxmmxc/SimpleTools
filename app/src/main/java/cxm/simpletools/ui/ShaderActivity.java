package cxm.simpletools.ui;

import android.app.Activity;
import android.os.Bundle;

import cxm.simpletools.view.ShaderView;

/**
 * Created by Terry.Chen on 2015/9/24 16:35.
 * Description:
 * Email:cxm_lmz@163.com
 */
public class ShaderActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new ShaderView(this));
    }
}
