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
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.animation.DecelerateInterpolator;

import java.util.Random;


/**
 * 仿百度的语音助手--波浪动画控件
 */
public class VolumeWaveView extends View {
    private static final String TAG = "VolumeWaveView";
    private static final int HEIGHT = 400;//整个控件的高度

    private static final int HEIGHT1 = 60;//第一层曲线的高度
    private static final int HEIGHT2 = 40;//第二层曲线的高度
    private static final int HEIGHT3 = 50;//第三层曲线的高度

    private int h1 = 0,h2 = 0, h3 = 0,h4 = 0,h5 = 0;

    private int range = 0;//波动的幅度,你可以动态改变这个值，比如麦克风录入的音量的高低

    private Path path;
    private Paint paint1,paint2,paint3,paint4;

    private LinearGradient linearGradient1,linearGradient2,linearGradient3,linearGradient4;//四种渐变色

    private ValueAnimator animator1,animator2,animator3,animator4,animator5;//五种动画


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
        paint1.setAntiAlias(true);//抗锯齿
        //渐变色1
        linearGradient1 = new LinearGradient(0, 0, 0, HEIGHT1,
                Color.parseColor("#e652a6d2"), Color.parseColor("#e652d5a1"), Shader.TileMode.MIRROR);
        paint1.setShader(linearGradient1);

        paint2 = new Paint();
        paint2.setAntiAlias(true);//抗锯齿
        paint2.setStyle(Paint.Style.FILL);
        //渐变色2
        linearGradient2 = new LinearGradient(0, 0, 0, HEIGHT2,
                Color.parseColor("#e68952d5"), Color.parseColor("#e6525dd5"), Shader.TileMode.MIRROR);
        paint2.setShader(linearGradient2);


        paint3 = new Paint();
        paint3.setAntiAlias(true);//抗锯齿
        paint3.setStyle(Paint.Style.FILL);
        //渐变色3
        linearGradient3 = new LinearGradient(0, 0, 0, HEIGHT3,
                Color.parseColor("#e66852d5"), Color.parseColor("#e651b9d2"), Shader.TileMode.MIRROR);
        paint3.setShader(linearGradient3);


        paint4 = new Paint();
        paint4.setAntiAlias(true);//抗锯齿
        paint4.setStyle(Paint.Style.FILL);
        //渐变色4
        linearGradient4 = new LinearGradient(0, 0, 0, HEIGHT2,
                Color.parseColor("#e6d5527e"), Color.parseColor("#e6bf52d5"), Shader.TileMode.MIRROR);
        paint4.setShader(linearGradient4);

    }


    /**
     * draw方法中不要创建大量对象，尽量复用对象
     * @param canvas
     */
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
        drawCurve(path,canvas,paint1,getWidth()/5,getWidth()/3,h1);
        drawCurve(path,canvas,paint1,getWidth()/3+getWidth()/5,getWidth()/3,h2);
    }

    /**
     * 绘制第二层
     * @param canvas
     */
    private void drawLayer2(Canvas canvas){
        drawCurve(path,canvas,paint2,0,getWidth()/2,h3);
        drawCurve(path,canvas,paint4,getWidth()/2-10,getWidth()/2,h4);

    }

    /**
     * 绘制第三层
     * @param canvas
     */
    private void drawLayer3(Canvas canvas){
        drawCurve(path,canvas,paint3,getWidth()/4,getWidth()/2,h5);
    }


    /**
     * 画贝塞尔曲线
     * @param path
     * @param canvas
     * @param x 横向起点的位置
     * @param width 曲线的整个宽度
     * @param height 曲线的高度
     */
    private void drawCurve(Path path,Canvas canvas,Paint paint,int x,int width,int height){
        path.reset();
        int divideWith = width/6;//因为一个弧形是由六部分组成的，所以这里就平均分一下
        path.moveTo(x,HEIGHT);
        path.quadTo(x+divideWith,HEIGHT-height,x+divideWith*2,HEIGHT-height*2);//B - C

        path.lineTo(x+divideWith*2,HEIGHT-height*2);
        path.quadTo(x+divideWith*3,HEIGHT-height*3,x+divideWith*4,HEIGHT-height*2);

        path.lineTo(x+divideWith*4,HEIGHT-height*2);
        path.quadTo(x+divideWith*5,HEIGHT-height,x+divideWith*6,HEIGHT);

        canvas.drawPath(path,paint);
    }

    /**
     * 添加属性动画,每一个动画的变化范围和周期都不一样，这样错开的效果才好看点
     */
    public void startAnimation() {
        Random random = new Random();
        range = random.nextInt(100)%(100-10+1) + 10;//波动的幅度，模拟动态音量输入，你可以自己设置

        animator1 = ValueAnimator.ofInt(0,HEIGHT1,0);
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

        animator2 = ValueAnimator.ofInt(0,HEIGHT1,0);
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



        animator3 = ValueAnimator.ofInt(0,HEIGHT2,0);
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


        animator4 = ValueAnimator.ofInt(0,HEIGHT2,0);
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


        animator5 = ValueAnimator.ofInt(0,HEIGHT3,0);
        animator5.setDuration(2000);
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

    /**
     * 关闭动画
     */
    public void removeAnimation(){
        if (animator1 != null){
            animator1.cancel();
            animator1 = null;
        }
        if (animator2 != null){
            animator2.cancel();
            animator2 = null;
        }
        if (animator3 != null){
            animator3.cancel();
            animator3 = null;
        }
        if (animator4 != null){
            animator4.cancel();
            animator4 = null;
        }
        if (animator5 != null){
            animator5.cancel();
            animator5 = null;
        }
    }

}
