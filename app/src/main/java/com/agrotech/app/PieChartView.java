package com.agrotech.app;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

/** Gráfico de pizza simples com legenda, desenhado com Canvas. */
public class PieChartView extends View {

    private String[] labels = new String[0];
    private float[] values = new float[0];
    private final Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final RectF oval = new RectF();
    private final int[] colors = {0xFF2E7D32, 0xFF66BB6A, 0xFFA5D6A7, 0xFFF59E0B, 0xFF8D6E63};

    public PieChartView(Context c) {
        super(c);
    }

    public PieChartView(Context c, AttributeSet a) {
        super(c, a);
    }

    public void setData(String[] labels, float[] values) {
        this.labels = labels;
        this.values = values;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (values.length == 0) return;
        float d = getResources().getDisplayMetrics().density;
        float total = 0;
        for (float v : values) total += v;
        if (total <= 0) return;

        float size = Math.min(getHeight(), getWidth() / 2f) - 8 * d;
        float cy = getHeight() / 2f;
        oval.set(4 * d, cy - size / 2f, 4 * d + size, cy + size / 2f);

        float start = -90;
        for (int i = 0; i < values.length; i++) {
            float sweep = values[i] / total * 360f;
            paint.setColor(colors[i % colors.length]);
            canvas.drawArc(oval, start, sweep, true, paint);
            start += sweep;
        }

        textPaint.setTextSize(14 * d);
        textPaint.setColor(0xFF1F2A37);
        float x = oval.right + 20 * d;
        float lineH = 24 * d;
        float y = cy - (values.length * lineH) / 2f + lineH * 0.75f;
        for (int i = 0; i < values.length; i++) {
            paint.setColor(colors[i % colors.length]);
            canvas.drawRect(x, y - 12 * d, x + 12 * d, y, paint);
            canvas.drawText(labels[i], x + 20 * d, y, textPaint);
            y += lineH;
        }
    }
}
