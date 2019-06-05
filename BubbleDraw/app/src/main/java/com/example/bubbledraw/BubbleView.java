package com.example.bubbledraw;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ImageView;
import android.view.View;
import java.util.ArrayList;
import java.util.Random;

public class BubbleView extends android.support.v7.widget.AppCompatImageView implements View.OnTouchListener {
    private Random random = new Random();
    private ArrayList<Bubble> bubbleList;
    int size = 50;
    int delay = 33;
    private Paint myPaint = new Paint();
    private Handler h = new Handler();

    public BubbleView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        bubbleList = new ArrayList<Bubble>();
        setOnTouchListener(this);
    }
    private Runnable r = new Runnable() {
        @Override
            public void run() {
            for(Bubble b : bubbleList)
                b.update();
                invalidate();
            }
    };
    protected void onDraw(Canvas canvas) {
        for(Bubble b:bubbleList){
            b.draw(canvas);
        }
        h.postDelayed(r, delay);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        for (int n = 0; n < event.getPointerCount(); n++) {
            int x = (int) event.getX(n);
            int y = (int) event.getY(n);
            int s = random.nextInt(size) + size;
            bubbleList.add(new Bubble(x, y, s));
        }
        return true;
    }

    public class Bubble {
        private int x;
        private int y;
        private int size;
        private int color;
        private int xspeed, yspeed;
        private final int MAX_SPEED = 15;

        public Bubble(int newX, int newY, int newSize) {
            x = newX;
            y = newY;
            size = newSize;
            color = Color.argb(random.nextInt(256),
                    random.nextInt(256),
                    random.nextInt(256),
                    random.nextInt(256));
            xspeed = random.nextInt(MAX_SPEED * 2) - MAX_SPEED;
            yspeed = random.nextInt(MAX_SPEED * 2) - MAX_SPEED;
        }

        public void draw(Canvas canvas) {
            myPaint.setColor(color);
            canvas.drawOval(x - size / 2, y - size / 2, x + size / 2, y + size / 2, myPaint);
        }

        public void update () {
            x += xspeed;
            y += yspeed;
            if (x <= 0 || x >= getWidth())
                xspeed = -xspeed;
            if (y <= 0 || y >= getHeight())
                yspeed = -yspeed;
        }
    }
}