package cxm.simpletools.ui;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

import cxm.simpletools.R;

/**
 * Created by Terry.Chen on 2015/10/21 17:03.
 * Description:
 * Email:cxm_lmz@163.com
 */
public class AnimoTestActivity extends Activity {

    private ListView mListView;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.anim_layout);
        mListView = (ListView) findViewById(R.id.listview);
        initData();
    }

    private void initData() {
        
    }
}
