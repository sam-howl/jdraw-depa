package jdraw.figures.handle;

import jdraw.figures.Line;
import jdraw.framework.DrawView;
import jdraw.framework.Figure;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.geom.Line2D;

public class LineStartHandle extends AbstractFigureHandle {
    public LineStartHandle(Figure figure) {
        super(figure);
    }

    @Override
    public Point getLocation() {
        Line line = (Line) getOwner();
        return line.getP1();
    }

    @Override
    public Cursor getCursor() {
        return Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR);
    }

    @Override
    public void dragInteraction(int x, int y, MouseEvent e, DrawView v) {
        Line line = (Line) getOwner();
        line.setP1(new Point(x, y));
    }
}
