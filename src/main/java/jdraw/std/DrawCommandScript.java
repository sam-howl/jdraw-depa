package jdraw.std;

import jdraw.framework.DrawCommand;

import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

public class DrawCommandScript implements DrawCommand {
    private List<DrawCommand> commands = new LinkedList<>();

    @Override
    public void redo() {
        ListIterator<DrawCommand> it = commands.listIterator();
        while (it.hasNext()){
            it.next().redo();
        }
    }

    @Override
    public void undo() {
        ListIterator<DrawCommand> it = commands.listIterator(commands.size());
        while (it.hasPrevious()){
            it.previous().undo();
        }
    }

    public int getSize(){
        return commands.size();
    }

    public DrawCommand getItem (int index){
        return commands.get(index);
    }

    public void addItem (DrawCommand c){
        commands.add(c);
    }
}
