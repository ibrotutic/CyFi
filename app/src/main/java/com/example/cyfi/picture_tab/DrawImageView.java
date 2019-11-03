package com.example.cyfi.picture_tab;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.ImageView;

public class DrawImageView extends ImageView {
    private Paint currentPaint;
    public boolean drawRect = false;
    public float left;
    public float top;
    public float right;
    public float bottom;

    public DrawImageView(Context context, AttributeSet attrs) {
        super(context, attrs);

        currentPaint = new Paint();
        currentPaint.setDither(true);
        currentPaint.setColor(0xFF00CC00);
        currentPaint.setStyle(Paint.Style.STROKE);
        currentPaint.setStrokeJoin(Paint.Join.ROUND);
        currentPaint.setStrokeCap(Paint.Cap.ROUND);
        currentPaint.setStrokeWidth(2);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (drawRect)
        {
            canvas.drawRect(left, top, right, bottom, currentPaint);
        }
    }
}
