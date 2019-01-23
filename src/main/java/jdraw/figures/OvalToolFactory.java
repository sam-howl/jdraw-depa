package jdraw.figures;

import jdraw.framework.DrawContext;
import jdraw.framework.DrawTool;

public class OvalToolFactory extends AbstractDrawToolFactory {
    @Override
    public DrawTool createTool(DrawContext context) {
        return new OvalTool(context, getName());
    }
}
