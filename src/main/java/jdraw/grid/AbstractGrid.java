package jdraw.grid;

import jdraw.framework.DrawGrid;

import java.awt.*;

public abstract class AbstractGrid implements DrawGrid {
    private final int step;

    public AbstractGrid(int step) {
        this.step = step;
    }

    @Override
    public Point constrainPoint(Point p) {
        int x = p.x / step;
        int y = p.y / step;
        return new Point(x * step, y * step);
    }

    @Override
    public int getStepX(boolean right) {
        return step;
    }

    @Override
    public int getStepY(boolean down) {
        return step;
    }

    @Override
    public void activate() {

    }

    @Override
    public void deactivate() {

    }

    @Override
    public void mouseDown() {

    }

    @Override
    public void mouseUp() {

    }
}
