/*
 * Copyright (c) 2018 Fachhochschule Nordwestschweiz (FHNW)
 * All Rights Reserved. 
 */

package jdraw.std;

import java.util.LinkedList;
import java.util.concurrent.CopyOnWriteArrayList;

import jdraw.framework.*;

/**
 * Provide a standard behavior for the drawing model. This class initially does not implement the methods
 * in a proper way.
 * It is part of the course assignments to do so.
 * @author Samantha Howlett
 *
 */
public class StdDrawModel implements DrawModel {
	private final LinkedList<Figure> figureList = new LinkedList<>();
	private final CopyOnWriteArrayList<DrawModelListener> drawModelListenerList = new CopyOnWriteArrayList<>();
	private FigureListener fl = e -> {
		notifyDrawModelListeners(this, e.getFigure(), DrawModelEvent.Type.DRAWING_CHANGED);
	};

	@Override
	public void addFigure(Figure f) {
		if (!figureList.contains(f)){
			figureList.add(f);
			notifyDrawModelListeners(this, f, DrawModelEvent.Type.FIGURE_ADDED);
			f.addFigureListener(fl);
		}
	}

	@Override
	public Iterable<Figure> getFigures() {
		return figureList;
	}

	@Override
	public void removeFigure(Figure f) {
		if (figureList.remove(f)){
			notifyDrawModelListeners(this, f, DrawModelEvent.Type.FIGURE_REMOVED);
			f.removeFigureListener(fl);
		}
	}

	@Override
	public void addModelChangeListener(DrawModelListener listener) {
		drawModelListenerList.add(listener);
	}

	@Override
	public void removeModelChangeListener(DrawModelListener listener) {
		drawModelListenerList.remove(listener);
	}

	/** The draw command handler. Initialized here with a dummy implementation. */
	// TODO initialize with your implementation of the undo/redo-assignment.
	private DrawCommandHandler handler = new EmptyDrawCommandHandler();

	/**
	 * Retrieve the draw command handler in use.
	 * @return the draw command handler.
	 */
	@Override
	public DrawCommandHandler getDrawCommandHandler() {
		return handler;
	}

	@Override
	public void setFigureIndex(Figure f, int index) {
		if (!figureList.contains(f)){
			throw new IllegalArgumentException();
		}
		if (index < 0 || index >= figureList.size()){
			throw new IndexOutOfBoundsException();
		}
		figureList.remove(f);
		figureList.add(index, f);
		notifyDrawModelListeners(this, f, DrawModelEvent.Type.DRAWING_CHANGED);
	}

	@Override
	public void removeAllFigures() {
		if (!figureList.isEmpty()){
			for (Figure f : figureList){
				f.removeFigureListener(fl);
			}
			figureList.clear();
			notifyDrawModelListeners(this, null, DrawModelEvent.Type.DRAWING_CLEARED);
		}
	}

	private void notifyDrawModelListeners(DrawModel source, Figure figure, DrawModelEvent.Type type) {
		DrawModelEvent drawModelEvent = new DrawModelEvent(source, figure, type);
		drawModelListenerList.forEach(l -> l.modelChanged(drawModelEvent));
	}
}
