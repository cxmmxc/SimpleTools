package cxm.simpletools;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import cxm.simpletools.ui.AnimoTestActivity;
import cxm.simpletools.ui.DreamActivity;
import cxm.simpletools.ui.FolderActivity;
import cxm.simpletools.ui.GirEffectActivity;
import cxm.simpletools.ui.HearActivity;
import cxm.simpletools.ui.IconMeasureActivity;
import cxm.simpletools.ui.LayerViewActivity;
import cxm.simpletools.ui.MainActivity;
import cxm.simpletools.ui.MatrixActivity;
import cxm.simpletools.ui.MultiCricleActivity;
import cxm.simpletools.ui.PageTurnActivity;
import cxm.simpletools.ui.PotorActivity;
import cxm.simpletools.ui.QQHeadSelectActivity;
import cxm.simpletools.ui.ShaderActivity;
import cxm.simpletools.ui.TestCanvasActivity;
import cxm.simpletools.ui.TextCusActivity;
import cxm.simpletools.ui.WaterActivity;

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

    public void GirlClick(View view) {
        intent.setClass(this, GirEffectActivity.class);
        startActivity(intent);
    }

    public void DreamClick(View view) {
        intent.setClass(this, DreamActivity.class);
        startActivity(intent);
    }

    public void MatrixClick(View view) {
        intent.setClass(this, MatrixActivity.class);
        startActivity(intent);
    }
    public void MultiCircleClick(View view) {
        intent.setClass(this, MultiCricleActivity.class);
        startActivity(intent);
    }

    public void WaterClick(View view) {
        intent.setClass(this, WaterActivity.class);
        startActivity(intent);
    }

    public void LayerClick(View view) {
        intent.setClass(this, LayerViewActivity.class);
        startActivity(intent);
    }
    public void PageTurnClick(View view) {
        intent.setClass(this, PageTurnActivity.class);
        startActivity(intent);
    }
    public void FoldClick(View view) {
        intent.setClass(this, FolderActivity.class);
        startActivity(intent);
    }
    public void TestClick(View view) {
        intent.setClass(this, TestCanvasActivity.class);
        startActivity(intent);
    }
    public void IconMeasureClick(View view) {
        intent.setClass(this, IconMeasureActivity.class);
        startActivity(intent);
    }

    public void AnimoClick(View view) {
        intent.setClass(this, AnimoTestActivity.class);
        startActivity(intent);
    }
    public void QqHeadPickClick(View view) {
        intent.setClass(this, QQHeadSelectActivity.class);
        startActivity(intent);
    }
    public void PotorClick(View view) {
        intent.setClass(this, PotorActivity.class);
        startActivity(intent);
    }
}
