package cxm.simpletools.ui;

import android.app.Activity;
import android.os.Bundle;

import cxm.simpletools.view.FoldView;

/**
 * Created by Terry.Chen on 2015/10/15 10:46.
 * Description:
 * Email:cxm_lmz@163.com
 */
public class FolderActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new FoldView(this));
    }
}
