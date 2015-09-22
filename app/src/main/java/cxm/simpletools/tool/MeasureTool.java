package cxm.simpletools.tool;

import android.app.Activity;
import android.util.DisplayMetrics;

import com.lidroid.xutils.util.LogUtils;

/**
 * Created by Terry.Chen on 2015/9/18 14:50.
 * Description:
 * Email:cxm_lmz@163.com
 */
public class MeasureTool {
    public static int[] getScreenWH(Activity context) {
        DisplayMetrics metrics = new DisplayMetrics();

        context.getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int [] widtAndHei = new int[2];
        widtAndHei[0] = metrics.widthPixels;
        widtAndHei[1] = metrics.heightPixels;
        LogUtils.i("---"+widtAndHei[0]+"---"+widtAndHei[1]);
        return widtAndHei;
    }
}
