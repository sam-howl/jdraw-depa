package jdraw.figures.handle;

import jdraw.framework.DrawView;
import jdraw.framework.Figure;
import jdraw.framework.FigureHandle;

import java.awt.*;
import java.awt.event.MouseEvent;

public class Handle implements FigureHandle {
    private AbstractFigureHandle state;

    public Handle(AbstractFigureHandle state) {
        this.state = state;
    }

    public AbstractFigureHandle getState() {
        return state;
    }

    public void setState(AbstractFigureHandle state) {
        this.state = state;
    }

    @Override
    public Figure getOwner() {
        return state.getOwner();
    }

    @Override
    public Point getLocation() {
        return state.getLocation();
    }

    @Override
    public void draw(Graphics g) {
        Point loc = getLocation();
        g.setColor(Color.WHITE);
        g.fillRect(loc.x - 3, loc.y - 3, 6, 6);
        g.setColor(Color.BLACK);
        g.drawRect(loc.x - 3, loc.y - 3, 6, 6);
    }

    @Override
    public Cursor getCursor() {
        return state.getCursor();
    }

    @Override
    public boolean contains(int x, int y) {
        Rectangle handle = new Rectangle(getLocation().x - 3, getLocation().y - 3, 6, 6);
        return handle.contains(x, y);
    }

    @Override
    public void startInteraction(int x, int y, MouseEvent e, DrawView v) {
        state.startInteraction(x, y, e, v);
    }

    @Override
    public void dragInteraction(int x, int y, MouseEvent e, DrawView v) {
        state.dragInteraction(x, y, e, v);
    }

    @Override
    public void stopInteraction(int x, int y, MouseEvent e, DrawView v) {
        state.stopInteraction(x, y, e, v);
    }
}
