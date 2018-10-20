package jdraw.figures.handle;

import jdraw.figures.Line;
import jdraw.framework.DrawView;
import jdraw.framework.Figure;

import java.awt.*;
import java.awt.event.MouseEvent;

public class LineEndHandle extends AbstractFigureHandle {
    public LineEndHandle(Figure figure) {
        super(figure);
    }

    @Override
    public Point getLocation() {
        Line line = (Line) getOwner();
        return line.getP2();
    }

    @Override
    public Cursor getCursor() {
        return Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR);
    }

    @Override
    public void dragInteraction(int x, int y, MouseEvent e, DrawView v) {
        Line line = (Line) getOwner();
        line.setP2(new Point(x, y));
    }
}
