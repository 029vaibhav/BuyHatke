package com.vaibhav.buyhatke.customviews;

import android.view.MotionEvent;
import android.view.View;

/**
 * Created by vaibhav on 22/4/17.
 */

public class DragableFloatingButton implements View.OnTouchListener {

    float dX;
    float dY;
    int lastAction;

    ClickEvent clickEvent;

    public DragableFloatingButton(ClickEvent clickEvent) {
        this.clickEvent = clickEvent;
    }

    @Override
    public boolean onTouch(View view, MotionEvent event) {
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                dX = view.getX() - event.getRawX();
                dY = view.getY() - event.getRawY();
                lastAction = MotionEvent.ACTION_DOWN;
                break;

            case MotionEvent.ACTION_MOVE:
                view.setY(event.getRawY() + dY);
                view.setX(event.getRawX() + dX);
                lastAction = MotionEvent.ACTION_MOVE;
                break;

            case MotionEvent.ACTION_UP:
                if (lastAction == MotionEvent.ACTION_DOWN)
                    clickEvent.onClick();
                break;

            default:
                return false;
        }
        return true;
    }
}