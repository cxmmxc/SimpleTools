package cxm.simpletools.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Terry.Chen on 2015/10/21 15:10.
 * Description:ViewGroup Measure
 * Email:cxm_lmz@163.com
 */
public class ViewGroupMeasureView extends ViewGroup {
    
    public ViewGroupMeasureView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int parentDisreWidth = 0;
        int parentDisreHeight = 0;
        if (getChildCount() > 0) {
            for (int i = 0; i < getChildCount(); i++) {
                View child = getChildAt(i);
                CustomParams clp = (CustomParams) child.getLayoutParams();
                measureChildWithMargins(child, widthMeasureSpec, 0, heightMeasureSpec, 0);
                parentDisreWidth += child.getMeasuredWidth() + clp.leftMargin + clp.rightMargin;
                parentDisreHeight += child.getMeasuredHeight() + clp.topMargin + clp.bottomMargin;
            }
            parentDisreWidth += getPaddingLeft() + getPaddingRight();
            parentDisreHeight += getPaddingTop() + getPaddingBottom();
            parentDisreWidth = Math.max(parentDisreWidth, getSuggestedMinimumWidth());
            parentDisreHeight = Math.max(parentDisreHeight, getSuggestedMinimumHeight());
        }
        setMeasuredDimension(resolveSize(parentDisreWidth, widthMeasureSpec), resolveSize(parentDisreHeight, heightMeasureSpec));
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int paddingTop = getPaddingTop();
        int paddingLeft = getPaddingLeft();
        if (getChildCount() > 0) {
            int tempHeight = 0;
            for (int i = 0; i < getChildCount(); i++) {
                View view = getChildAt(i);
                CustomParams lp = (CustomParams) view.getLayoutParams();
                view.layout(paddingLeft+lp.leftMargin, tempHeight+paddingTop+lp.topMargin, view.getMeasuredWidth()+paddingLeft+lp.leftMargin, view.getMeasuredHeight() + tempHeight+paddingTop+lp.topMargin);
                tempHeight += view.getMeasuredHeight()+lp.topMargin+lp.bottomMargin;
            }
        }
    }

    public class CustomParams extends MarginLayoutParams {

        public CustomParams(Context c, AttributeSet attrs) {
            super(c, attrs);
        }

        public CustomParams(int width, int height) {
            super(width, height);
        }

        public CustomParams(MarginLayoutParams source) {
            super(source);
        }
        public CustomParams(LayoutParams source) {
            super(source);
        }
    }

    @Override
    protected LayoutParams generateDefaultLayoutParams() {
        return new CustomParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
    }

    @Override
    protected LayoutParams generateLayoutParams(LayoutParams p) {
        return new CustomParams(p);
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new CustomParams(getContext(), attrs);
    }

    @Override
    protected boolean checkLayoutParams(LayoutParams p) {
        return p instanceof CustomParams;
    }
    
}