package cxm.simpletools;

import android.app.Application;

import com.lidroid.xutils.util.LogUtils;

/**
 * Created by Terry.Chen on 2015/9/22 15:29.
 * Description:
 * Email:cxm_lmz@163.com
 */
public class APP extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        initData();
    }

    private void initData() {
        LogUtils.customTagPrefix = "cxm";
    }
}
