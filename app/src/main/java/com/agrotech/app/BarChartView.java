package com.agrotech.app;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/** Gráfico de barras simples desenhado com Canvas (sem bibliotecas externas). */
public class BarChartView extends View {

    private String[] labels = new String[0];
    private float[] values = new float[0];
    private final Paint barPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final int[] colors = {0xFFA5D6A7, 0xFF81C784, 0xFF66BB6A, 0xFF4CAF50, 0xFF2E7D32};

    public BarChartView(Context c) {
        super(c);
        init();
    }

    public BarChartView(Context c, AttributeSet a) {
        super(c, a);
        init();
    }

    private void init() {
        textPaint.setTextAlign(Paint.Align.CENTER);
        textPaint.setColor(0xFF1F2A37);
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
        textPaint.setTextSize(12 * d);

        float w = getWidth(), h = getHeight();
        float top = 28 * d, bottom = h - 24 * d;
        float max = 0;
        for (float v : values) max = Math.max(max, v);
        if (max <= 0) return;

        float slot = w / values.length;
        float bw = slot * 0.6f;
        for (int i = 0; i < values.length; i++) {
            float left = i * slot + (slot - bw) / 2f;
            float barH = (bottom - top) * values[i] / max;
            barPaint.setColor(colors[Math.min(i, colors.length - 1)]);
            canvas.drawRect(left, bottom - barH, left + bw, bottom, barPaint);
            canvas.drawText(String.valueOf(Math.round(values[i])), left + bw / 2f, bottom - barH - 6 * d, textPaint);
            canvas.drawText(labels[i], left + bw / 2f, h - 6 * d, textPaint);
        }
    }
}
