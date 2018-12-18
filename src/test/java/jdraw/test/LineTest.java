package jdraw.test;

import jdraw.figures.Line;
import jdraw.framework.Figure;

public class LineTest extends AbstractFigureTest {


	@Override
	protected Figure createFigure(int x, int y, int w, int h) {
		return new Line(x,y,w,h);
	}
}
