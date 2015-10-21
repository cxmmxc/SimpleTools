package cxm.simpletools.tool;

import android.app.Activity;
import android.util.DisplayMetrics;

/**
 * Created by Terry.Chen on 2015/9/18 14:50.
 * Description:
 * Email:cxm_lmz@163.com
 */
public class MeasureTool {
    public static int[] getScreenWH(Activity activity) {
        DisplayMetrics metrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
        return new int[] { metrics.widthPixels, metrics.heightPixels };
    }

}
