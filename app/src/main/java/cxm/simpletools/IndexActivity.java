package cxm.simpletools;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import cxm.simpletools.ui.HearActivity;
import cxm.simpletools.ui.MainActivity;
import cxm.simpletools.ui.ShaderActivity;
import cxm.simpletools.ui.TextCusActivity;

/**
 * Created by Terry.Chen on 2015/9/24 14:38.
 * Description:
 * Email:cxm_lmz@163.com
 */
public class IndexActivity extends Activity {
    
    Intent intent;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.index_layout);
        initData();
    }

    private void initData() {
        intent = new Intent();
    }

    public void CircleClick(View view) {
        intent.setClass(this, MainActivity.class);
        startActivity(intent);
    }

    public void TextCusClick(View view) {
        intent.setClass(this, TextCusActivity.class);
        startActivity(intent);
    }

    public void HeartClick(View view) {
        intent.setClass(this, HearActivity.class);
        startActivity(intent);
    }

    public void ShaderClick(View view) {
        intent.setClass(this, ShaderActivity.class);
        startActivity(intent);
    }
}
