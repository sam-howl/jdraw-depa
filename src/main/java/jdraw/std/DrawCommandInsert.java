package jdraw.std;

import jdraw.framework.DrawCommand;
import jdraw.framework.DrawModel;
import jdraw.framework.Figure;

public class DrawCommandInsert implements DrawCommand {
    private final Figure figure;
    private final DrawModel model;

    public DrawCommandInsert(Figure f, DrawModel m){
        this.figure = f;
        this.model = m;
    }

    @Override
    public void redo() {
        model.addFigure(figure);
    }

    @Override
    public void undo() {
        model.removeFigure(figure);
    }
}
