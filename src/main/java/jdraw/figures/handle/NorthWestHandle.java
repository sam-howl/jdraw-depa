package jdraw.figures.handle;

import jdraw.framework.DrawView;
import jdraw.framework.Figure;

import java.awt.*;
import java.awt.event.MouseEvent;

public class NorthWestHandle extends AbstractFigureHandle {

    public NorthWestHandle(Figure figure) {
        super(figure);
    }

    @Override
    public Point getLocation() {
        return getOwner().getBounds().getLocation();
    }

    @Override
    public Cursor getCursor() {
        return Cursor.getPredefinedCursor(Cursor.NW_RESIZE_CURSOR);
    }

    @Override
    public void dragInteraction(int x, int y, MouseEvent e, DrawView v) {
        Rectangle r = getOwner().getBounds();
        getOwner().setBounds(new Point(x, y), new Point (r.x + r.width, r.y + r.height));
        if (y > r.y + r.height){
            getOwner().swapHorizontal();
        }
        if (x > r.x + r.width ){
            getOwner().swapVertical();
        }
    }
}
