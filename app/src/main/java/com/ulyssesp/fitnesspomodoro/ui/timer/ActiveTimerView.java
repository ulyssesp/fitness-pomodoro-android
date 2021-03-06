package com.ulyssesp.fitnesspomodoro.ui.timer;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import com.google.auto.value.AutoValue;

public class ActiveTimerView extends View {
    private static final int COMPLETED_COLOR = 0xff00E676;
    private static final float CIRCLE_STROKE_MODIFIER = 1f / 80f;

    private Paint mCompletedPaint = new Paint();
    private Paint mCompletedCirclePaint = new Paint();
    private Paint mIncompletePaint = new Paint();
    private RectF mBounds;
    private Model mModel;

    @AutoValue
    public abstract static class Model {
        abstract public Float percentDone();

        public static Model create(Float percentDone) {
            return new AutoValue_ActiveTimerView_Model(percentDone);
        }
    }

    public ActiveTimerView(Context context, AttributeSet attrs) {
        super(context, attrs);

        setWillNotDraw(false);

        mIncompletePaint.setColor(0xffE57373);
        mIncompletePaint.setStyle(Paint.Style.STROKE);

        mCompletedPaint.setColor(COMPLETED_COLOR);
        mCompletedPaint.setStyle(Paint.Style.STROKE);

        mCompletedCirclePaint.setColor(COMPLETED_COLOR);
        mCompletedCirclePaint.setStyle(Paint.Style.FILL);
    }

    @Override
    public void onMeasure(int widthSpec, int heightSpec) {
      super.onMeasure(widthSpec, heightSpec);
      int size = Math.min(getMeasuredWidth(), getMeasuredHeight());
      setMeasuredDimension(size, size);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        float strokeWidth = w * CIRCLE_STROKE_MODIFIER;
        float completedCircleStrokeWidth = strokeWidth * 2f;
        float margin = completedCircleStrokeWidth ;
        float circleWidth = w - 2 * margin;

        mBounds = new RectF(margin, margin, circleWidth + margin, circleWidth + margin);

        mIncompletePaint.setStrokeWidth(strokeWidth);
        mCompletedPaint.setStrokeWidth(strokeWidth);

        mCompletedCirclePaint.setStrokeWidth(completedCircleStrokeWidth);
    }

    public void update(Model model) {
        mModel = model;
        invalidate();
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if(mModel == null) {
            return;
        }

        float r = mBounds.width() / 2;

        canvas.drawCircle(r + mBounds.left, r + mBounds.top, r, mIncompletePaint);

        double a = 360 * Math.min(mModel.percentDone(), 1);
        float start = (float) -a - 90;
        float sweep = (float) a;
        canvas.drawArc(mBounds, start, sweep, false, mCompletedPaint);

        float xPos = r * (float) Math.cos(Math.toRadians(start));
        float yPos = r * (float) Math.sin(Math.toRadians(start));
        canvas.drawCircle(xPos + r + mBounds.left, yPos + r + mBounds.top,
                mCompletedCirclePaint.getStrokeWidth(), mCompletedCirclePaint);
    }
}
