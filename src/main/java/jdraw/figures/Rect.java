/*
 * Copyright (c) 2018 Fachhochschule Nordwestschweiz (FHNW)
 * All Rights Reserved. 
 */

package jdraw.figures;

import jdraw.figures.handle.*;
import jdraw.framework.FigureHandle;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.LinkedList;
import java.util.List;

/**
 * Represents rectangles in JDraw.
 * 
 * @author Christoph Denzler
 *
 */
public class Rect extends AbstractFigure {
	/**
	 * Use the java.awt.Rectangle in order to save/reuse code.
	 */
	private final Rectangle rectangle;
	private final List<FigureHandle> handles = new LinkedList<>();
	
	/**
	 * Create a new rectangle of the given dimension.
	 * @param x the x-coordinate of the upper left corner of the rectangle
	 * @param y the y-coordinate of the upper left corner of the rectangle
	 * @param w the rectangle's width
	 * @param h the rectangle's height
	 */
	public Rect(int x, int y, int w, int h) {
		rectangle = new Rectangle(x, y, w, h);
	}

	public Rect (Rect rect){
		rectangle = (Rectangle) rect.rectangle.clone();
	}

	/**
	 * Draw the rectangle to the given graphics context.
	 * @param g the graphics context to use for drawing.
	 */
	@Override
	public void draw(Graphics g) {
		g.setColor(Color.WHITE);
		g.fillRect(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
		g.setColor(Color.BLACK);
		g.drawRect(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
	}
	
	@Override
	public void setBounds(Point origin, Point corner) {
		if (null == origin || null == corner) {
			throw new NullPointerException();
		}
		Rectangle original = new Rectangle(rectangle);
		rectangle.setFrameFromDiagonal(origin, corner);
		if(!original.equals(rectangle))
			propagateFigureEvent();
	}

	@Override
	public void move(int dx, int dy) {
		if (dy == 0 && dx == 0) {
			return;
		}
		rectangle.setLocation(rectangle.x + dx, rectangle.y + dy);
		propagateFigureEvent();
	}

	@Override
	public boolean contains(int x, int y) {
		return rectangle.contains(x, y);
	}

	@Override
	public Rectangle getBounds() {
		return rectangle.getBounds();
	}

	@Override
	public Rect clone(){
		return new Rect(this);
	}

	public List<FigureHandle> getHandles() {

		handles.add(new Handle(new WestHandle(this)));
		handles.add(new Handle (new NorthWestHandle(this)));
		handles.add(new Handle (new NorthHandle(this)));
		handles.add(new Handle (new NorthEastHandle(this)));
		handles.add(new Handle (new EastHandle(this)));
		handles.add(new Handle (new SouthWestHandle(this)));
		handles.add(new Handle (new SouthHandle(this)));
		handles.add(new Handle (new SouthEastHandle(this)));
		return handles;
	}

	@Override
	public void swapHorizontal() {
		Handle NW = (Handle) handles.get(1);
		Handle N = (Handle) handles.get(2);
		Handle NE = (Handle) handles.get(3);
		Handle SW = (Handle) handles.get(5);
		Handle S = (Handle) handles.get(6);
		Handle SE = (Handle) handles.get(7);

		AbstractFigureHandle NWState = NW.getState();
		AbstractFigureHandle NEState = NE.getState();
		AbstractFigureHandle NState = N.getState();

		NW.setState(SW.getState());
		NE.setState(SE.getState());
		N.setState(S.getState());
		SW.setState(NWState);
		SE.setState(NEState);
		S.setState(NState);
	}

	@Override
	public void swapVertical() {
		Handle W = (Handle) handles.get(0);
		Handle NW = (Handle) handles.get(1);
		Handle N = (Handle) handles.get(2);
		Handle NE = (Handle) handles.get(3);
		Handle E = (Handle) handles.get(4);
		Handle SW = (Handle) handles.get(5);
		Handle S = (Handle) handles.get(6);
		Handle SE = (Handle) handles.get(7);

		AbstractFigureHandle NWState = NW.getState();
		AbstractFigureHandle SWState = SW.getState();
		AbstractFigureHandle WState = W.getState();

		NW.setState(NE.getState());
		SW.setState(SE.getState());
		W.setState(E.getState());
		NE.setState(NWState);
		SE.setState(SWState);
		E.setState(WState);
	}
}
