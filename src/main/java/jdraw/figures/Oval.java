/*
 * Copyright (c) 2018 Fachhochschule Nordwestschweiz (FHNW)
 * All Rights Reserved. 
 */

package jdraw.figures;

import jdraw.figures.handle.*;
import jdraw.framework.FigureHandle;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.util.LinkedList;
import java.util.List;

/**
 * Represents rectangles in JDraw.
 * 
 * @author Christoph Denzler
 *
 */
public class Oval extends AbstractFigure {
	/**
	 * Use the java.awt.geom.Ellipse2D in order to save/reuse code.
	 */
	private final Ellipse2D oval;
	List<FigureHandle> handles = new LinkedList<>();

	/**
	 * Create a new rectangle of the given dimension.
	 * @param x the x-coordinate of the upper left corner of the oval
	 * @param y the y-coordinate of the upper left corner of the oval
	 * @param w the oval's width
	 * @param h the oval's height
	 */
	public Oval(int x, int y, int w, int h) {
		oval = new Ellipse2D.Double(x, y, w, h);
	}

	public Oval (Oval o){
		oval = (Ellipse2D.Double) o.oval.clone();
	}

	/**
	 * Draw the oval to the given graphics context.
	 * @param g the graphics context to use for drawing.
	 */
	@Override
	public void draw(Graphics g) {
		g.setColor(Color.WHITE);
		g.fillOval((int)oval.getX(), (int)oval.getY(), (int)oval.getWidth(), (int)oval.getHeight());
		g.setColor(Color.BLACK);
		g.drawOval((int)oval.getX(), (int)oval.getY(), (int)oval.getWidth(), (int)oval.getHeight());
	}

	@Override
	public void setBounds(Point origin, Point corner) {
		Ellipse2D original = new Ellipse2D.Double(oval.getX(), oval.getY(), oval.getWidth(), oval.getHeight());
		oval.setFrameFromDiagonal(origin, corner);
		if(!original.equals(oval))
			propagateFigureEvent();
	}

	@Override
	public void move(int dx, int dy) {
		if (dy == 0 && dx == 0) {
			return;
		}
		oval.setFrame(oval.getX() + dx, oval.getY() + dy, oval.getWidth(), oval.getHeight());
		propagateFigureEvent();
	}

	@Override
	public boolean contains(int x, int y) {
		return oval.contains(x, y);
	}

	@Override
	public Rectangle getBounds() {
		return oval.getBounds();
	}

	@Override
	public Oval clone(){
		return new Oval(this);
	}

	public java.util.List<FigureHandle> getHandles() {

		handles.add(new Handle(new WestHandle(this)));
		handles.add(new Handle (new NorthHandle(this)));
		handles.add(new Handle (new EastHandle(this)));
		handles.add(new Handle (new SouthHandle(this)));
		return handles;
	}

	@Override
	public void swapHorizontal() {
		Handle N = (Handle) handles.get(1);
		Handle S = (Handle) handles.get(3);

		AbstractFigureHandle NState = N.getState();

		N.setState(S.getState());
		S.setState(NState);
	}

	@Override
	public void swapVertical() {
		Handle W = (Handle) handles.get(0);
		Handle E = (Handle) handles.get(2);

		AbstractFigureHandle WState = W.getState();

		W.setState(E.getState());
		E.setState(WState);
	}
}
