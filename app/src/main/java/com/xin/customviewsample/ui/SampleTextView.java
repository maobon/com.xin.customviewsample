package com.xin.customviewsample.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.widget.Toast;

import com.xin.customviewsample.R;

/**
 * Created by gmrz on 10/22/17.
 * Sample TextView custom view
 */

public class SampleTextView extends View implements View.OnClickListener {

    private String mText;
    private int mTextColor;
    private int mTextSize;

    private Context context;
    private Paint mPaint;
    private Rect mRect;

    // Constructor
    public SampleTextView(Context context) {
        this(context, null);
    }

    public SampleTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SampleTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;

        // get style values from xml
        TypedArray typedArray = context.getTheme()
                .obtainStyledAttributes(attrs, R.styleable.SampleTextView, defStyleAttr, 0);

        int indexCount = typedArray.getIndexCount();
        for (int i = 0; i < indexCount; i++) {
            int attr = typedArray.getIndex(i);
            switch (attr) {
                case R.styleable.SampleTextView_text:
                    mText = typedArray.getString(attr);
                    break;
                case R.styleable.SampleTextView_textColor:
                    mTextColor = typedArray.getColor(attr, Color.WHITE);
                    break;
                case R.styleable.SampleTextView_textSize:
                    // default text size 16sp
                    int defTextSize = (int) TypedValue
                            .applyDimension(TypedValue.COMPLEX_UNIT_SP, 16, getResources().getDisplayMetrics());
                    mTextSize = typedArray.getDimensionPixelSize(attr, defTextSize);
                    break;
            }
        }

        typedArray.recycle();

        // create paint
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mRect = new Rect();

        // use Rect to measure with content area
        mPaint.setTextSize(mTextSize);
        mPaint.getTextBounds(mText, 0, mText.length(), mRect);

        // set on click listener
        this.setOnClickListener(this);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int width = 0;
        int height = 0;

        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSpec = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSpec = MeasureSpec.getSize(heightMeasureSpec);

        if (widthMode == MeasureSpec.EXACTLY) {
            width = widthSpec;
        } else {
            mPaint.setTextSize(mTextSize);
            mPaint.getTextBounds(mText, 0, mText.length(), mRect);
            width = getPaddingLeft() + mRect.width() + getPaddingRight();
        }

        if (heightMode == MeasureSpec.EXACTLY) {
            height = heightSpec;
        } else {
            mPaint.setTextSize(mTextSize);
            mPaint.getTextBounds(mText, 0, mText.length(), mRect);
            height = getPaddingTop() + mRect.height() + getPaddingBottom();
        }

        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // draw background
        mPaint.setColor(Color.YELLOW);
        canvas.drawRect(0, 0, getMeasuredWidth(), getMeasuredHeight(), mPaint);

        // draw content text
        mPaint.setColor(mTextColor);
        canvas.drawText(mText,
                getMeasuredWidth() / 2 - mRect.width() / 2,
                getMeasuredHeight() / 2 + mRect.height() / 2,
                mPaint
        );

    }

    @Override
    public void onClick(View v) {
        Toast.makeText(context, "Clicked", Toast.LENGTH_SHORT).show();
    }
}
