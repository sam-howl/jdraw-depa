package jdraw.figures.handle;

import jdraw.framework.DrawView;
import jdraw.framework.Figure;

import java.awt.*;
import java.awt.event.MouseEvent;

public class SouthHandle extends AbstractFigureHandle {

    public SouthHandle(Figure figure) {
        super(figure);
    }

    @Override
    public Point getLocation() {
        Rectangle r = getOwner().getBounds();
        Point point = new Point(r.x+(r.width/2), r.y+r.height);
        return point;
    }

    @Override
    public Cursor getCursor() {
        return Cursor.getPredefinedCursor(Cursor.S_RESIZE_CURSOR);
    }


    @Override
    public void dragInteraction(int x, int y, MouseEvent e, DrawView v) {
        Rectangle r = getOwner().getBounds();
        getOwner().setBounds(new Point(getOwner().getBounds().x, y), new Point(r.x + r.width, r.y));
        if (y < r.y){
            getOwner().swapHorizontal();
        }
    }
}
