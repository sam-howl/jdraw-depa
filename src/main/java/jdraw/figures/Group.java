package jdraw.figures;

import jdraw.figures.handle.*;
import jdraw.framework.*;

import java.awt.*;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class Group implements Figure, FigureGroup {
    private List<Figure> parts;
    private final List<FigureHandle> handles = new LinkedList<>();
    private final List<FigureListener> figureListenerList = new CopyOnWriteArrayList<>();

    public Group(List<Figure> parts) {
        this.parts = new CopyOnWriteArrayList<>(parts);
    }

    public Group(Group group){
        parts = new CopyOnWriteArrayList<>();
        group.parts.forEach(f -> this.parts.add(f.clone()));
    }

    @Override
    public void draw(Graphics g) {
        parts.forEach(f -> f.draw(g));
    }

    @Override
    public void move(int dx, int dy) {
        parts.forEach(f -> f.move(dx, dy));
        propagateFigureEvent();
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
    public Group clone(){
        return new Group(this);
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
        figureListenerList.add(listener);
    }

    @Override
    public void removeFigureListener(FigureListener listener) {
        figureListenerList.remove(listener);
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

    private void propagateFigureEvent(){
        FigureEvent figureEvent = new FigureEvent(this);
        figureListenerList.forEach(l -> l.figureChanged(figureEvent));
    }
}
