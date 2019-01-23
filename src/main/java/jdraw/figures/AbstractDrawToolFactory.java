package jdraw.figures;

import jdraw.framework.DrawContext;
import jdraw.framework.DrawTool;
import jdraw.framework.DrawToolFactory;

public class AbstractDrawToolFactory implements DrawToolFactory {
    private String name, icon;

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getIconName() {
        return icon;
    }

    @Override
    public void setIconName(String name) {
        this.icon = name;
    }

    @Override
    public DrawTool createTool(DrawContext context) {
        return null;
    }
}
