/*
 * Copyright (c) 2018 Fachhochschule Nordwestschweiz (FHNW)
 * All Rights Reserved. 
 */

package jdraw.figures;

import jdraw.framework.DrawContext;
import jdraw.framework.Figure;


/**
 * This tool defines a mode for drawing rectangles.
 *
 * @see jdraw.framework.Figure
 *
 * @author  Christoph Denzler
 */
public class LineTool extends AbstractDrawTool {

	/**
	 * Create a new rectangle tool for the given context.
	 *
	 * @param context a context to use this tool in.
	 * @param name    a name of the figure type
	 */
	public LineTool(DrawContext context, String name) {
		super(context, name);
	}

	@Override
	public Figure createFigure(int x, int y) {
		return new Line(x, y, x, y);
	}
}
