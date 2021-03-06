package com.lmj.customcamera.vertical;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import com.lmj.customcamera.R;

/**
 * author: lmj
 * date  : 2018/3/19.
 */

public class VerticalRectView extends AppCompatImageView {
    //    角标画笔
    private Paint mHornPaint;
    //    背景画笔
    private Paint mBgPaint;
    //    文本画笔
    private Paint mTextPaint;
    //     提示
    private String mTip;
    //    矩形宽度
    public int rectWidth;
    //    矩形高度
    public int rectHeight;
    //    顶部偏移量
    public int topOffset;
    //    左边偏移量
    public int leftOffset;
    //    右边偏移量
    public int rightOffset;
    //    底部偏移量
    public int bottomOffset;
    public float leftRatio = 0.05f;
    public float topRatio = 0.3f;
    public int widthScreen;
    public int heightScreen;
    private final DisplayMetrics mMetrics;


    public VerticalRectView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mMetrics = context.getResources().getDisplayMetrics();
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        if (wm!=null){
            wm.getDefaultDisplay().getMetrics(dm);
            widthScreen = dm.widthPixels;
            heightScreen = dm.heightPixels;
        }else {
            widthScreen = mMetrics.widthPixels;
            heightScreen = mMetrics.heightPixels;
        }
        initPaint();
        initSize();
    }

    private void initPaint() {
        mTip = "对准后拍摄";
        mBgPaint = new Paint();
        mBgPaint.setStyle(Paint.Style.FILL);
        mBgPaint.setColor(getResources().getColor(R.color.supply_scan_bg));
        mBgPaint.setAntiAlias(true);

        mHornPaint = new Paint();
        mHornPaint.setStyle(Paint.Style.FILL);
        mHornPaint.setColor(getResources().getColor(R.color.common_blue));
        mHornPaint.setStrokeWidth(3);
        mHornPaint.setAntiAlias(true);

        mTextPaint = new Paint();
        mTextPaint.setColor(getResources().getColor(R.color.common_white));
        mTextPaint.setTextSize(dip2px(15));
        mTextPaint.setTextAlign(Paint.Align.CENTER);

    }

    private void initSize(){
        topOffset = (int) (heightScreen * topRatio);
        leftOffset = (int) (widthScreen * leftRatio);
        rectHeight = dip2px(60);
        rightOffset = (int) (widthScreen * (1 - leftRatio));
        bottomOffset = topOffset + rectHeight;
        rectWidth = widthScreen - 2 * leftOffset;
    }


    @Override
    protected void onDraw(Canvas canvas) {


        //画矩形
        canvas.drawRect(leftOffset, 0, rightOffset, topOffset, mBgPaint);//上矩形
        canvas.drawRect(leftOffset, bottomOffset, rightOffset, heightScreen, mBgPaint);//下矩形
        canvas.drawRect(0, 0, leftOffset, heightScreen, mBgPaint);//左矩形
        canvas.drawRect(rightOffset, 0, widthScreen, heightScreen, mBgPaint);//右矩形


        //画8个小角标

        canvas.drawLine(leftOffset, topOffset + 1, 2 * leftOffset, topOffset + 1, mHornPaint);//左上横角标
        canvas.drawLine(leftOffset - 1, topOffset, leftOffset - 1, topOffset + leftOffset, mHornPaint);//左上竖角标
        canvas.drawLine(rectWidth, topOffset + 1, rightOffset, topOffset + 1, mHornPaint);//右上横角标
        canvas.drawLine(rightOffset, topOffset, rightOffset, leftOffset + topOffset, mHornPaint);//右上竖角标
        canvas.drawLine(leftOffset, bottomOffset - 1, 2 * leftOffset, bottomOffset - 1, mHornPaint);//左下横角标
        canvas.drawLine(leftOffset - 1, bottomOffset, leftOffset - 1, bottomOffset - leftOffset, mHornPaint);//左下竖角标
        canvas.drawLine(rightOffset, bottomOffset - 1, rectWidth, bottomOffset - 1, mHornPaint);//右下横角标
        canvas.drawLine(rightOffset, bottomOffset, rightOffset, bottomOffset - leftOffset, mHornPaint);//右下竖角标

        canvas.drawText(mTip, widthScreen / 2, topOffset - dip2px(30), mTextPaint);

    }

    public void setTip(String tip) {
        mTip = tip;
        invalidate();
    }

    int dip2px(float dipValue) {
        final float scale = mMetrics.density;
        return (int) (dipValue * scale + 0.5f);
    }

    public void resetSize(int width, int height) {
        heightScreen = height;
        widthScreen = width;
        initSize();
        invalidate();
    }
}