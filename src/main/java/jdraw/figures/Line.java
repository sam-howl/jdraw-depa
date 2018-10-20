/*
 * Copyright (c) 2018 Fachhochschule Nordwestschweiz (FHNW)
 * All Rights Reserved. 
 */

package jdraw.figures;

import jdraw.figures.handle.*;
import jdraw.framework.FigureHandle;

import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.LinkedList;
import java.util.List;

/**
 * Represents rectangles in JDraw.
 * 
 * @author Christoph Denzler
 *
 */
public class Line extends AbstractFigure {
	/**
	 * Use the java.awt.geom.Line2D in order to save/reuse code.
	 */
	private final Line2D line2D;

	/**
	 * Create a new line of the given dimension.
	 * @param x the x-coordinate of the upper left corner of the line
	 * @param y the y-coordinate of the upper left corner of the line
	 * @param w the line's width
	 * @param h the line's height
	 */
	public Line(int x, int y, int w, int h) {
		line2D = new Line2D.Double(x, y, w, h);
	}

	/**
	 * Draw the line to the given graphics context.
	 * @param g the graphics context to use for drawing.
	 */
	@Override
	public void draw(Graphics g) {
		g.setColor(Color.BLACK);
		g.drawLine((int)line2D.getX1(), (int)line2D.getY1(), (int)line2D.getX2(), (int)line2D.getY2());
	}

	@Override
	public void setBounds(Point origin, Point corner) {
		Line2D original = new Line2D.Double(line2D.getX1(), line2D.getY1(), line2D.getX2(), line2D.getY2());
		line2D.setLine(origin, corner);
		if(!original.equals(line2D))
			propagateFigureEvent();
	}

	@Override
	public void move(int dx, int dy) {
		if (dy == 0 && dx == 0) {
			return;
		}
		line2D.setLine(line2D.getX1() + dx, line2D.getY1() + dy, line2D.getX2() + dx, line2D.getY2() + dy);
		propagateFigureEvent();
	}

	@Override
	public boolean contains(int x, int y) {
		int hitBoxSize = 5;
		int boxX = x - hitBoxSize / 2;
		int boxY = y - hitBoxSize / 2;

		int width = hitBoxSize;
		int height = hitBoxSize;

		return line2D.intersects(boxX, boxY, width, height);
	}

	@Override
	public Rectangle getBounds() {
		return line2D.getBounds();
	}

	public java.util.List<FigureHandle> getHandles() {
		List<FigureHandle> handles = new LinkedList<>();
		handles.add(new Handle(new LineStartHandle(this)));
		handles.add(new Handle(new LineEndHandle(this)));
		return handles;
	}

	@Override
	public void swapHorizontal() {

	}

	@Override
	public void swapVertical() {

	}

	public Point getP1() {
		return new Point((int) line2D.getX1(), (int) line2D.getY1());
	}

	public Point getP2() {
		return new Point((int) line2D.getX2(), (int) line2D.getY2());
	}

	public void setP1(Point point) {
		if (point != getP1()) {
			line2D.setLine(point, getP2());
			propagateFigureEvent();
		}
	}

	public void setP2(Point point) {
		if (point != getP2()) {
			line2D.setLine(getP1(), point);
			propagateFigureEvent();
		}

	}
}
