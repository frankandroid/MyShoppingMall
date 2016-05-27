package com.example.frank.myshoppingmall.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.widget.ImageView;

import com.example.frank.myshoppingmall.R;

/**
 * 创建者     Frank
 * 创建时间   2016/5/20 10:59
 * 描述	      ${自定义的圆角图片}
 * 更新者     $Author$
 * 更新时间   $Date$
 * 更新描述   ${TODO}
 */
public class FrankCircleImageView extends ImageView {

    public static final String TAG ="FrankCircleImageView";

    //类型
    private int type;
    public static final int TYPE_CIRCLE = 0;
    public static final int TYPE_ROUND  = 1;
    public static final int BORDER_RADIUS_DEFAULT = 10;
    //圆角大小
    private int mBorderRadius;
    private Paint mPaint;
    private Matrix mMatrix;
    private BitmapShader mBitmapShader;
    //view的宽度
    private int mWidth;
    private RectF mRoundRectF;
    //圆形的半径
    private int mRadius;


    public FrankCircleImageView(Context context) {
        this(context, null);
    }

    public FrankCircleImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FrankCircleImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mMatrix = new Matrix();
        mPaint = new Paint();
        mPaint.setAntiAlias(true);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.FrankCircleImageView);

        type = typedArray.getInt(R.styleable.FrankCircleImageView_type, TYPE_CIRCLE);

        mBorderRadius = typedArray.getDimensionPixelSize(R.styleable.FrankCircleImageView_borderRadius, (int) TypedValue.applyDimension
                (TypedValue.COMPLEX_UNIT_DIP, BORDER_RADIUS_DEFAULT, getResources().getDisplayMetrics()));

        typedArray.recycle();


    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        Log.d(TAG, w + "++++++" + getMeasuredWidth());

        mRoundRectF = new RectF(0,0,w,h);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        Log.d(TAG,"onmeasure");

        if (type==TYPE_CIRCLE){
            mWidth = Math.min(getMeasuredWidth(),getMeasuredHeight());

            mRadius=mWidth/2;

            setMeasuredDimension(mWidth,mWidth);
        }




    }

    private void setUpShader(){

        float scale = 1.0f;

        Drawable drawable = getDrawable();

        if (drawable==null){
            return;
        }
        Bitmap bitmap = drawableToBitmap(drawable);

        mBitmapShader = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);


        if (type==TYPE_CIRCLE){

            int bitmapsize = Math.min(bitmap.getWidth(),bitmap.getHeight());
            scale = mWidth*1.0f/bitmapsize;

        }else if(type==TYPE_ROUND){

            scale = Math.max(getWidth()*1.0f/bitmap.getWidth(),getHeight()*1.0f/bitmap.getHeight());

        }

            mMatrix.setScale(scale,scale);

            mBitmapShader.setLocalMatrix(mMatrix);

            mPaint.setShader(mBitmapShader);

    }


    private Bitmap drawableToBitmap(Drawable drawable){

        if (drawable instanceof BitmapDrawable)
        {
            BitmapDrawable bd = (BitmapDrawable) drawable;
            return bd.getBitmap();
        }
        int w = drawable.getIntrinsicWidth();
        int h = drawable.getIntrinsicHeight();
        Bitmap bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, w, h);
        drawable.draw(canvas);
        return bitmap;
    }



    @Override
    protected void onDraw(Canvas canvas) {


        if (getDrawable()==null){
            return;
        }

        setUpShader();

        if (type==TYPE_CIRCLE){
            canvas.drawCircle(mRadius,mRadius,mRadius,mPaint);
            
        }else {
            canvas.drawRoundRect(mRoundRectF,mBorderRadius,mBorderRadius,mPaint);
        }

    }
}
