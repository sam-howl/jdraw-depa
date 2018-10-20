package jdraw.figures.handle;

import jdraw.framework.DrawView;
import jdraw.framework.Figure;

import java.awt.*;
import java.awt.event.MouseEvent;

public class EastHandle extends AbstractFigureHandle {

    public EastHandle(Figure figure) {
        super(figure);
    }

    @Override
    public Point getLocation() {
        Rectangle r = getOwner().getBounds();
        Point point = new Point(r.x+r.width, r.y+(r.height/2));
        return point;
    }

    @Override
    public Cursor getCursor() {
        return Cursor.getPredefinedCursor(Cursor.E_RESIZE_CURSOR);
    }

    @Override
    public void dragInteraction(int x, int y, MouseEvent e, DrawView v) {
        Rectangle r = getOwner().getBounds();
        getOwner().setBounds(new Point(x, getOwner().getBounds().y), new Point(r.x, r.y + r.height));
        if (x < (r.x) ){
            getOwner().swapVertical();
        }
    }
}
