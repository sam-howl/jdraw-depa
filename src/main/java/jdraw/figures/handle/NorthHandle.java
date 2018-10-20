package jdraw.figures.handle;

import jdraw.framework.DrawView;
import jdraw.framework.Figure;

import java.awt.*;
import java.awt.event.MouseEvent;

public class NorthHandle extends AbstractFigureHandle {

    public NorthHandle(Figure figure) {
        super(figure);
    }

    @Override
    public Point getLocation() {
        Rectangle r = getOwner().getBounds();
        //Point point = new Point(getOwner().getBounds().x+(getOwner().getBounds().width/2), getOwner().getBounds().y);
        Point point = new Point(r.x+(r.width/2), r.y);
        return point;
    }

    @Override
    public Cursor getCursor() {
        return Cursor.getPredefinedCursor(Cursor.N_RESIZE_CURSOR);
    }

    @Override
    public void dragInteraction(int x, int y, MouseEvent e, DrawView v) {
        Rectangle r = getOwner().getBounds();
        getOwner().setBounds(new Point(getOwner().getBounds().x, y), new Point(r.x + r.width, r.y + r.height));
        if (y > r.y + r.height){
            getOwner().swapHorizontal();
        }
    }
}
