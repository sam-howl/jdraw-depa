package jdraw.figures.handle;

import jdraw.framework.DrawView;
import jdraw.framework.Figure;
import jdraw.framework.FigureHandle;

import java.awt.*;
import java.awt.event.MouseEvent;

public abstract class AbstractFigureHandle implements FigureHandle {
    private final Figure figure;
    private int x, y;

    public AbstractFigureHandle(Figure figure) {
        this.figure = figure;
    }

    @Override
    public Figure getOwner() {
        return figure;
    }

    @Override
    public void draw(Graphics g) {

    }

    @Override
    public boolean contains(int x, int y) {

        return false;
    }

    @Override
    public void startInteraction(int x, int y, MouseEvent e, DrawView v) {
        this.x = x;
        this.y = y;
    }

    @Override
    public void stopInteraction(int x, int y, MouseEvent e, DrawView v) {
        this.x = 0;
        this.y = 0;
    }
}
