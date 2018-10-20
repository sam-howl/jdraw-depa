package jdraw.figures.handle;

import jdraw.framework.DrawView;
import jdraw.framework.Figure;

import java.awt.*;
import java.awt.event.MouseEvent;

public class NorthEastHandle extends AbstractFigureHandle {

    public NorthEastHandle(Figure figure) {
        super(figure);
    }

    @Override
    public Point getLocation() {
        Rectangle r = getOwner().getBounds();
        Point point = new Point(r.x+r.width, r.y);
        return point;
    }

    @Override
    public Cursor getCursor() {
        return Cursor.getPredefinedCursor(Cursor.NE_RESIZE_CURSOR);
    }

    @Override
    public void dragInteraction(int x, int y, MouseEvent e, DrawView v) {
        Rectangle r = getOwner().getBounds();
        getOwner().setBounds(new Point(x, y), new Point(r.x, r.y + r.height));
        if (y > r.y + r.height){
            getOwner().swapHorizontal();
        }
        if (x < (r.x) ){
            getOwner().swapVertical();
        }
    }
}
