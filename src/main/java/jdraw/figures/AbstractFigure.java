package jdraw.figures;

import jdraw.framework.Figure;
import jdraw.framework.FigureEvent;
import jdraw.framework.FigureHandle;
import jdraw.framework.FigureListener;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public abstract class AbstractFigure implements Figure {
    private final CopyOnWriteArrayList<FigureListener> figureListenerList = new CopyOnWriteArrayList<>();

    @Override
    public final void addFigureListener(FigureListener listener) {
        figureListenerList.add(listener);
    }

    @Override
    public final void removeFigureListener(FigureListener listener) {
        figureListenerList.remove(listener);
    }

    protected final void propagateFigureEvent(){
        FigureEvent figureEvent = new FigureEvent(this);
        figureListenerList.forEach(fl -> fl.figureChanged(figureEvent));
    }

    @Override
    public Figure clone(){
        return null;
    }
}
