package jdraw.figures;

import jdraw.framework.Figure;

import java.awt.*;

public class DecoratorBorder extends AbstractDecoratorFigure {
    private static final int BORDER_OFFSET = 5;

    public DecoratorBorder (Figure f){
        super(f);
    }

    @Override
    public void draw(Graphics g) {
        super.draw(g);
        Rectangle r = getBounds();
        g.setColor(Color.white);
        g.drawLine(r.x, r.y, r.x, r.y + r.height);
        g.drawLine(r.x, r.y, r.x + r.width, r.y);
        g.setColor(Color.gray);
        g.drawLine(r.x + r.width, r.y, r.x + r.width, r.y + r.height);
        g.drawLine(r.x, r.y + r.height, r.x + r.width, r.y+ r.height);
    }

    @Override
    public Rectangle getBounds() {
        Rectangle r = getInner().getBounds();
        r.grow(BORDER_OFFSET, BORDER_OFFSET);
        return r;
    }

    @Override
    public boolean contains(int x, int y){
        return getBounds().contains(x, y);
    }
}
