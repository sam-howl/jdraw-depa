package jdraw.figures;

import jdraw.figures.handle.*;
import jdraw.framework.Figure;
import jdraw.framework.FigureGroup;
import jdraw.framework.FigureHandle;
import jdraw.framework.FigureListener;

import java.awt.*;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class Group implements Figure, FigureGroup {
    private List<Figure> parts;
    private final List<FigureHandle> handles = new LinkedList<>();

    public Group(List<Figure> parts) {
        this.parts = new CopyOnWriteArrayList<>(parts);
    }

    @Override
    public void draw(Graphics g) {
        parts.forEach(f -> f.draw(g));
    }

    @Override
    public void move(int dx, int dy) {
        parts.forEach(f -> f.move(dx, dy));
    }

    @Override
    public boolean contains(int x, int y) {
        Rectangle rectangle = getBounds();
        return rectangle.contains(x, y);
    }

    @Override
    public void setBounds(Point origin, Point corner) {

    }

    @Override
    public Rectangle getBounds() {
        Rectangle rectangle = new Rectangle(parts.get(0).getBounds());
        parts.forEach(f -> rectangle.add(f.getBounds()));
        return rectangle;
    }

    @Override
    public List<FigureHandle> getHandles() {
        handles.add(new Handle(new NorthWestHandle(this)));
        handles.add(new Handle (new NorthEastHandle(this)));
        handles.add(new Handle (new SouthWestHandle(this)));
        handles.add(new Handle (new SouthEastHandle(this)));
        return handles;
    }

    @Override
    public void addFigureListener(FigureListener listener) {

    }

    @Override
    public void removeFigureListener(FigureListener listener) {

    }

    @Override
    public Figure clone() {
        return null;
    }

    @Override
    public void swapHorizontal() {

    }

    @Override
    public void swapVertical() {

    }

    @Override
    public Iterable<Figure> getFigureParts() {
        return parts;
    }
}