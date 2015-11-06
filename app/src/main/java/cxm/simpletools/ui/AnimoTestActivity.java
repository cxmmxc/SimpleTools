package cxm.simpletools.ui;

import android.app.Activity;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.BounceInterpolator;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.lidroid.xutils.util.LogUtils;
import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.AnimatorSet;
import com.nineoldandroids.animation.ObjectAnimator;

import cxm.simpletools.R;
import cxm.simpletools.tool.MeasureTool;
import cxm.simpletools.view.AnimDialog;

/**
 * Created by Terry.Chen on 2015/10/21 17:03.
 * Description:
 * Email:cxm_lmz@163.com
 */
public class AnimoTestActivity extends Activity {

    private ListView mListView;
    private AnimDialog dialog;

    private int mScreenW, mScreenH;

    private int mBtnW, mBtnH;

    private int tempX;
    private int tempY;

    private ImageView imageView;
    
    private LinearLayout lineLayout;
    
    private final static int ANIMOTION_DURATION = 500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.anim_layout);
        mListView = (ListView) findViewById(R.id.listview);
        imageView = (ImageView) findViewById(R.id.imageview);
        lineLayout = (LinearLayout) findViewById(R.id.lineLayout);
        initData();
    }

    private void initData() {
        mListView.setAdapter(new AnimAdapter());

        dialog = new AnimDialog(this);
        int[] screenWH = MeasureTool.getScreenWH(this);
        mScreenW = screenWH[0];
        mScreenH = screenWH[1];

        imageView.setImageBitmap(BitmapFactory.decodeResource(this.getResources(), R.mipmap.lakesi_1));

        imageView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                LogUtils.v(imageView.getWidth() + "," + imageView.getHeight());
            }
        });

        lineLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (imageView.getVisibility() == View.INVISIBLE) {
                    return;
                }
                AnimatorSet animatorSet = new AnimatorSet();
                animatorSet.playTogether(
                        ObjectAnimator.ofFloat(imageView, "translationX", mScreenW / 2 - imageView.getWidth() / 2, tempX - imageView.getWidth() / 2 + mBtnW / 2),
                        ObjectAnimator.ofFloat(imageView, "translationY", mScreenH / 2 - imageView.getHeight() / 2, tempY - imageView.getHeight() / 2 + mBtnH / 2),
                        ObjectAnimator.ofFloat(imageView, "scaleX", 1f, 0.0f),
                        ObjectAnimator.ofFloat(imageView, "scaleY", 1f, 0.0f),
                        ObjectAnimator.ofFloat(imageView, "alpha", 1f, 0.2f)
                );
                animatorSet.setInterpolator(new AccelerateInterpolator());
                animatorSet.setDuration(ANIMOTION_DURATION).start();
                animatorSet.addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animator) {
                        LogUtils.e("onAnimationStart---"+",imgW=" + imageView.getWidth() + ",imgH=" + imageView.getHeight());
                    }

                    @Override
                    public void onAnimationEnd(Animator animator) {
                        LogUtils.e("onAnimationEnd---");
                        lineLayout.setVisibility(View.INVISIBLE);
                        imageView.setVisibility(View.INVISIBLE);
                    }

                    @Override
                    public void onAnimationCancel(Animator animator) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animator) {

                    }
                });
            }
        });
    }

    class AnimAdapter extends BaseAdapter {
        private LayoutInflater inflater;

        public AnimAdapter() {
            inflater = LayoutInflater.from(AnimoTestActivity.this);
        }


        @Override
        public int getCount() {
            return 10;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            convertView = inflater.inflate(R.layout.anim_item, null);
            final Button click_btn = (Button) convertView.findViewById(R.id.click_btn);

            click_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int location[] = new int[2];
                    click_btn.getLocationInWindow(location);
                    tempX = location[0];
                    tempY = location[1];
                    mBtnW = click_btn.getWidth();
                    mBtnH = click_btn.getHeight();
                    lineLayout.setVisibility(View.VISIBLE);
                    imageView.setVisibility(View.VISIBLE);
                    LogUtils.e("x=" + (tempX) + ",y=" + (tempY) + ",pos=" + position + ",imgW=" + imageView.getWidth() + ",imgH=" + imageView.getHeight());
                    AnimatorSet animatorSet = new AnimatorSet();
                    animatorSet.playTogether(
                            ObjectAnimator.ofFloat(imageView, "translationX", tempX - imageView.getWidth() / 2 + mBtnW / 2, mScreenW / 2 - imageView.getWidth() / 2),
                            ObjectAnimator.ofFloat(imageView, "translationY", tempY - imageView.getHeight() / 2 + mBtnH / 2, mScreenH / 2 - imageView.getHeight() / 2),
                            ObjectAnimator.ofFloat(imageView, "scaleX", 0.2f, 1.0f),
                            ObjectAnimator.ofFloat(imageView, "scaleY", 0.2f, 1.0f),
                            ObjectAnimator.ofFloat(imageView, "alpha", 0.5f, 1.0f)
                    );
                    animatorSet.setInterpolator(new BounceInterpolator());
                    animatorSet.setDuration(ANIMOTION_DURATION).start();
                    animatorSet.addListener(new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animator) {
                            LogUtils.e("onAnimationStart"+",imgW=" + imageView.getWidth() + ",imgH=" + imageView.getHeight());
                        }

                        @Override
                        public void onAnimationEnd(Animator animator) {
                            LogUtils.e("onAnimationEnd");
                        }

                        @Override
                        public void onAnimationCancel(Animator animator) {

                        }

                        @Override
                        public void onAnimationRepeat(Animator animator) {

                        }
                    });
                }
            });

            return convertView;
        }
    }
}
