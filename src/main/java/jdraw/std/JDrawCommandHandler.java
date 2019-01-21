package jdraw.std;

import jdraw.framework.DrawCommand;
import jdraw.framework.DrawCommandHandler;

import java.util.Stack;

public class JDrawCommandHandler implements DrawCommandHandler {
    private Stack<DrawCommand> undoStack, redoStack;
    private DrawCommandScript script;

    public JDrawCommandHandler (){
        undoStack = new Stack<>();
        redoStack = new Stack<>();
        script = null;
    }

    @Override
    public void addCommand(DrawCommand cmd) {
        redoStack.clear();
        if (script == null){
            undoStack.push(cmd);
        } else {
            script.addItem(cmd);
        }
    }

    @Override
    public void undo() {
        if (undoPossible()){
            DrawCommand command = undoStack.pop();
            redoStack.push(command);
            command.undo();
        }

    }

    @Override
    public void redo() {
        if (redoPossible()) {
            DrawCommand command = redoStack.pop();
            undoStack.push(command);
            command.redo();
        }
    }

    @Override
    public boolean undoPossible() {
        return !undoStack.empty();
    }

    @Override
    public boolean redoPossible() {
        return !redoStack.empty();
    }

    @Override
    public void beginScript() {
        if(script != null)
            throw new IllegalArgumentException();
        script = new DrawCommandScript();
    }

    @Override
    public void endScript() {
        if (script == null)
            throw new IllegalArgumentException();
        DrawCommandScript s = script;
        script = null;
        if (s.getSize() > 0){
            if (s.getSize() == 1) {
                addCommand(s.getItem(0));
            } else {
                addCommand(s);
            }
        }
    }

    @Override
    public void clearHistory() {
        undoStack.clear();
        redoStack.clear();
    }
}
