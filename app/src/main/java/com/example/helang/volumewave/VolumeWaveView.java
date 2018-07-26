package com.example.helang.volumewave;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Shader;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.DecelerateInterpolator;


/**
 * 波浪...
 */
public class VolumeWaveView extends View {
    private static final String TAG = "VolumeWaveView";

    private static final int HEIGHT = 300;//整个曲线的高度
    private static final int HEIGHT1 = 100;//第一层曲线的高度
    private static final int HEIGHT2 = 300;//第二层曲线的高度
    private static final int HEIGHT3 = 250;//第三层曲线的高度

    private int leftRightMargin = 50;
    
    private int parentWith = 0;


    private float h1 = 200,h2 = 200, h3 = 300,h4 = 300,h5 = 200;

    private static final int splitWidth = -200;//扇形的交错距离

    private Path path;
    private Paint paint1,paint2,paint3,paint4;

    private LinearGradient linearGradient1,linearGradient2,linearGradient3,linearGradient4;//四种渐变色


    public VolumeWaveView(Context context) {
        this(context,null);
    }

    public VolumeWaveView(Context context, @Nullable AttributeSet attrs) {
        this(context,attrs,0);
    }

    public VolumeWaveView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs,defStyleAttr);
        initPaint();

        
    }

    private void initPaint(){
        path = new Path();

        paint1 = new Paint();
        paint1.setStyle(Paint.Style.FILL);
        //渐变色1  9052a6d2  9052d5a1
        linearGradient1 = new LinearGradient(0, 0, 0, HEIGHT1,
                Color.parseColor("#e652a6d2"), Color.parseColor("#e652d5a1"), Shader.TileMode.MIRROR);
        paint1.setShader(linearGradient1);

        paint2 = new Paint();
        paint2.setStyle(Paint.Style.FILL);
        //渐变色2
        linearGradient2 = new LinearGradient(0, 0, 0, HEIGHT2,
                Color.parseColor("#e68952d5"), Color.parseColor("#e6525dd5"), Shader.TileMode.MIRROR);
        paint2.setShader(linearGradient2);


        paint3 = new Paint();
        paint3.setStyle(Paint.Style.FILL);
        //渐变色3
        linearGradient3 = new LinearGradient(0, 0, 0, HEIGHT3,
                Color.parseColor("#e66852d5"), Color.parseColor("#e651b9d2"), Shader.TileMode.MIRROR);
        paint3.setShader(linearGradient3);


        paint4 = new Paint();
        paint4.setStyle(Paint.Style.FILL);
        //渐变色4
        linearGradient4 = new LinearGradient(0, 0, 0, HEIGHT2,
                Color.parseColor("#e6d5527e"), Color.parseColor("#e6bf52d5"), Shader.TileMode.MIRROR);
        paint4.setShader(linearGradient4);



    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawLayer3(canvas);
        drawLayer2(canvas);
        drawLayer1(canvas);
    }

    /**
     * 绘制第一层
     * @param canvas
     */
    private void drawLayer1(Canvas canvas){
        path.reset();//重置path
        path.moveTo(0, HEIGHT);//起点
        path.quadTo(getWidth()/4, HEIGHT-h1, getWidth()/2, HEIGHT);//第一条二阶贝塞尔曲线

        path.moveTo(getWidth()/2+splitWidth,HEIGHT);
        path.quadTo(getWidth()/2+splitWidth+getWidth()/4, HEIGHT-h2, getWidth(), HEIGHT);//第二条二阶贝塞尔曲线
        canvas.drawPath(path,paint1);
    }

    /**
     * 绘制第二层
     * @param canvas
     */
    private void drawLayer2(Canvas canvas){
        path.reset();//重置path
        path.moveTo(0, HEIGHT);//起点
        path.quadTo(getWidth()/4, HEIGHT-h3, getWidth()/2, HEIGHT);//第一条二阶贝塞尔曲线
        canvas.drawPath(path,paint2);

        path.reset();
        path.moveTo(getWidth()/2+splitWidth,HEIGHT);
        path.quadTo(getWidth()/2+getWidth()/4, HEIGHT-h4, getWidth(), HEIGHT);//第二条二阶贝塞尔曲线
        canvas.drawPath(path,paint4);

    }

    /**
     * 绘制第三层
     * @param canvas
     */
    private void drawLayer3(Canvas canvas){
        path.reset();//重置path
        path.moveTo(200,HEIGHT);
        path.quadTo(200+getWidth()/3, HEIGHT-h5, getWidth(), HEIGHT);//二阶贝塞尔曲线
        canvas.drawPath(path,paint3);
    }


    /**
     * 添加属性动画
     */
    public void startAnimation() {
        ValueAnimator animator1 = ValueAnimator.ofInt(0,HEIGHT1,0);
        animator1.setDuration(1400);
        animator1.setInterpolator(new DecelerateInterpolator());
        //无限循环
        animator1.setRepeatCount(ValueAnimator.INFINITE);
        animator1.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                h1 = (int) animation.getAnimatedValue();
                invalidate();

            }
        });
        animator1.start();

        ValueAnimator animator2 = ValueAnimator.ofInt(0,HEIGHT1,0);
        animator2.setDuration(1700);
        animator2.setInterpolator(new DecelerateInterpolator());
        //无限循环
        animator2.setRepeatCount(ValueAnimator.INFINITE);
        animator2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                h2 = (int) animation.getAnimatedValue();
                invalidate();

            }
        });
        animator2.start();




        ValueAnimator animator3 = ValueAnimator.ofInt(0,HEIGHT2,0);
        animator3.setDuration(1600);
        animator3.setInterpolator(new DecelerateInterpolator());
        //无限循环
        animator3.setRepeatCount(ValueAnimator.INFINITE);
        animator3.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                h3 = (int) animation.getAnimatedValue();
                invalidate();

            }
        });
        animator3.start();


        ValueAnimator animator4 = ValueAnimator.ofInt(0,HEIGHT2,0);
        animator4.setDuration(1300);
        animator4.setInterpolator(new DecelerateInterpolator());
        //无限循环
        animator4.setRepeatCount(ValueAnimator.INFINITE);
        animator4.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                h4 = (int) animation.getAnimatedValue();
                invalidate();

            }
        });
        animator4.start();


        ValueAnimator animator5 = ValueAnimator.ofInt(0,HEIGHT2,0);
        animator5.setDuration(1250);
        animator5.setInterpolator(new DecelerateInterpolator());
        //无限循环
        animator5.setRepeatCount(ValueAnimator.INFINITE);
        animator5.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                h5 = (int) animation.getAnimatedValue();
                invalidate();

            }
        });
        animator5.start();
    }



}
