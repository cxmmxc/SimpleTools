package cxm.simpletools.view;

import android.app.Dialog;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.view.ViewGroup;
import android.widget.ImageView;

import cxm.simpletools.R;

/**
 * Created by lmz_cxm on 2015/10/21.
 */
public class CusDialog extends Dialog {
    public CusDialog(Context context) {
        this(context, android.R.style.Theme_Dialog);
    }

    public CusDialog(Context context, int themeResId) {
        super(context, themeResId);
        initData(context);

    }

    private void initData(Context context) {
        ImageView imageView = new ImageView(context);
        imageView.setImageBitmap(BitmapFactory.decodeResource(context.getResources(), R.mipmap.lakesi_1));
        this.addContentView(imageView,new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
    }
}
