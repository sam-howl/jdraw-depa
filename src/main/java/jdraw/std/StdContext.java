/*
 * Copyright (c) 2018 Fachhochschule Nordwestschweiz (FHNW)
 * All Rights Reserved.
 */
package jdraw.std;

import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import jdraw.figures.*;
import jdraw.framework.DrawCommandHandler;
import jdraw.framework.DrawModel;
import jdraw.framework.DrawTool;
import jdraw.framework.DrawToolFactory;
import jdraw.framework.DrawView;
import jdraw.framework.Figure;
import jdraw.grid.Grid;

/**
 * Standard implementation of interface DrawContext.
 * 
 * @see DrawView
 * @author Dominik Gruntz & Christoph Denzler
 * @version 2.6, 24.09.09
 */
@SuppressWarnings("serial")
public class StdContext extends AbstractContext {
	private List<Figure> clipboard = new ArrayList<>();
	private List<DrawToolFactory> toolFactories = new LinkedList<>();
	/**
	 * Constructs a standard context with a default set of drawing tools.
	 * @param view the view that is displaying the actual drawing.
	 */
	public StdContext(DrawView view) {
		super(view, null);
	}

	/**
	 * Constructs a standard context. The drawing tools available can be
	 * parameterized using <code>toolFactories</code>.
	 * 
	 * @param view  the view that is displaying the actual drawing.
	 * @param toolFactories  a list of DrawToolFactories that are available to the user
	 */
	public StdContext(DrawView view, List<DrawToolFactory> toolFactories) {
		super(view, toolFactories);
		this.toolFactories = toolFactories;
	}

	/**
	 * Creates and initializes the "Edit" menu.
	 * 
	 * @return the new "Edit" menu.
	 */
	@Override
	protected JMenu createEditMenu() {
		JMenu editMenu = new JMenu("Edit");
		final JMenuItem undo = new JMenuItem("Undo");
		undo.setAccelerator(KeyStroke.getKeyStroke("control Z"));
		editMenu.add(undo);
		undo.addActionListener(e -> {
				final DrawCommandHandler h = getModel().getDrawCommandHandler();
				if (h.undoPossible()) {
					h.undo();
				}
			}
		);

		final JMenuItem redo = new JMenuItem("Redo");
		redo.setAccelerator(KeyStroke.getKeyStroke("control Y"));
		editMenu.add(redo);
		redo.addActionListener(e -> {
				final DrawCommandHandler h = getModel().getDrawCommandHandler();
				if (h.redoPossible()) {
					h.redo();
				}
			}
		);
		editMenu.addSeparator();

		JMenuItem sa = new JMenuItem("SelectAll");
		sa.setAccelerator(KeyStroke.getKeyStroke("control A"));
		editMenu.add(sa);
		sa.addActionListener( e -> {
				for (Figure f : getModel().getFigures()) {
					getView().addToSelection(f);
				}
				getView().repaint();
			}
		);

		editMenu.addSeparator();
		JMenuItem cut = new JMenuItem("Cut");
		editMenu.add(cut);
		cut.addActionListener(e -> {
			if(!clipboard.isEmpty())
				clipboard.clear();
			getView().getSelection().forEach(f -> {
				//clone so the original properties are fetched when pasting and prevent changes before pasting
				clipboard.add(f.clone());
				//remove all the selected figures
				getView().getModel().removeFigure(f);
			});
		});
		JMenuItem copy = new JMenuItem("Copy");
		editMenu.add(copy);
		copy.addActionListener(e -> {
			if(!clipboard.isEmpty())
				clipboard.clear();
			//clone so the original properties are fetched when pasting and prevent changes before pasting
			getView().getSelection().forEach(f -> clipboard.add(f.clone()));
		});
		JMenuItem paste = new JMenuItem("Paste");
		editMenu.add(paste);
		paste.addActionListener(e -> {
			//clone again to do multiple pastes
			clipboard.forEach(f -> getView().getModel().addFigure(f.clone()));
		});

		editMenu.addSeparator();
		JMenuItem clear = new JMenuItem("Clear");
		editMenu.add(clear);
		clear.addActionListener(e -> {
			getModel().removeAllFigures();
		});

		editMenu.addSeparator();
		JMenuItem group = new JMenuItem("Group");
		group.addActionListener(e -> {
			List<Figure> selectedFigures = getView().getSelection();
			List<Figure> figures = new ArrayList<>();
			for (Figure f : getModel().getFigures()){
				if(selectedFigures.contains(f)){
					figures.add(f);
				}
			}
			getModel().addFigure(new Group(figures));
			figures.forEach(f -> getModel().removeFigure(f));
		});
		editMenu.add(group);

		JMenuItem ungroup = new JMenuItem("Ungroup");
		ungroup.addActionListener(e -> {
			List<Figure> figures = getView().getSelection();
			for (Figure f : figures) {
				if (f instanceof Group){
					for (Figure g : ((Group) f).getFigureParts())
						getModel().addFigure(g);
					getModel().removeFigure(f);
				}
			}
		});
		editMenu.add(ungroup);

		editMenu.addSeparator();

		JMenu orderMenu = new JMenu("Order...");
		JMenuItem frontItem = new JMenuItem("Bring To Front");
		frontItem.addActionListener(e -> {
			bringToFront(getView().getModel(), getView().getSelection());
		});
		orderMenu.add(frontItem);
		JMenuItem backItem = new JMenuItem("Send To Back");
		backItem.addActionListener(e -> {
			sendToBack(getView().getModel(), getView().getSelection());
		});
		orderMenu.add(backItem);
		editMenu.add(orderMenu);

		JMenu grid = new JMenu("Grid...");
		JMenuItem simpleGrid = new JMenuItem("Simple Grid");
		simpleGrid.addActionListener(e -> getView().setGrid(new Grid(1)));
		JMenuItem twentyGrid = new JMenuItem("20 Grid");
		twentyGrid.addActionListener(e -> getView().setGrid(new Grid(20)));
		JMenuItem fiftyGrid = new JMenuItem("50 Grid");
		fiftyGrid.addActionListener(e -> getView().setGrid(new Grid(50)));
		JMenuItem noGrid = new JMenuItem("No Grid");
		noGrid.addActionListener(e -> getView().setGrid(null));
		grid.add(simpleGrid);
		grid.add(twentyGrid);
		grid.add(fiftyGrid);
		grid.add(noGrid);
		editMenu.add(grid);

		JMenu decorator = new JMenu("Decorators...");
		JMenuItem borderDecorator = new JMenuItem("Border Decorator");
		borderDecorator.addActionListener(e -> {
			List<Figure> selected = getView().getSelection();
			getView().clearSelection();
			for (Figure f : selected){
				DecoratorBorder border = new DecoratorBorder(f);
				getModel().removeFigure(f);
				getModel().addFigure(border);
				getView().addToSelection(border);
				getModel().removeFigure(f);
			}
		});
		JMenuItem borderDecoratorRemove = new JMenuItem("Remove Border Decorator");
		borderDecoratorRemove.addActionListener(e -> {
			for (Figure f : getView().getSelection()){
				if(f instanceof DecoratorBorder){
					AbstractDecoratorFigure d = (AbstractDecoratorFigure) f;
					Figure inner = d.getInner();
					getModel().removeFigure(f);
					getModel().addFigure(inner);
				}
			}
		});
		decorator.add(borderDecorator);
		decorator.add(borderDecoratorRemove);
		editMenu.add(decorator);
		
		return editMenu;
	}

	/**
	 * Creates and initializes items in the file menu.
	 * 
	 * @return the new "File" menu.
	 */
	@Override
	protected JMenu createFileMenu() {
		JMenu fileMenu = new JMenu("File");
		JMenuItem open = new JMenuItem("Open");
		fileMenu.add(open);
		open.setAccelerator(KeyStroke.getKeyStroke("control O"));
		open.addActionListener(e -> doOpen());

		JMenuItem save = new JMenuItem("Save");
		save.setAccelerator(KeyStroke.getKeyStroke("control S"));
		fileMenu.add(save);
		save.addActionListener(e ->	doSave());

		JMenuItem exit = new JMenuItem("Exit");
		fileMenu.add(exit);
		exit.addActionListener(e -> System.exit(0));
		
		return fileMenu;
	}

	@Override
	protected void doRegisterDrawTools() {
		for (DrawToolFactory dt : getToolFactories()){
			addTool(dt == null ? null : dt.createTool(this));
		}
//		DrawTool rectangleTool = new RectTool(this, "Rectangle");
//		addTool(rectangleTool);
//		DrawTool ovalTool = new OvalTool(this, "Oval");
//		addTool(ovalTool);
//		DrawTool lineTool = new LineTool(this, "Line");
//		addTool(lineTool);
	}

	/**
	 * Changes the order of figures and moves the figures in the selection
	 * to the front, i.e. moves them to the end of the list of figures.
	 * @param model model in which the order has to be changed
	 * @param selection selection which is moved to front
	 */
	public void bringToFront(DrawModel model, List<Figure> selection) {
		// the figures in the selection are ordered according to the order in
		// the model
		List<Figure> orderedSelection = new LinkedList<Figure>();
		int pos = 0;
		for (Figure f : model.getFigures()) {
			pos++;
			if (selection.contains(f)) {
				orderedSelection.add(0, f);
			}
		}
		for (Figure f : orderedSelection) {
			model.setFigureIndex(f, --pos);
		}
	}

	/**
	 * Changes the order of figures and moves the figures in the selection
	 * to the back, i.e. moves them to the front of the list of figures.
	 * @param model model in which the order has to be changed
	 * @param selection selection which is moved to the back
	 */
	public void sendToBack(DrawModel model, List<Figure> selection) {
		// the figures in the selection are ordered according to the order in
		// the model
		List<Figure> orderedSelection = new LinkedList<Figure>();
		for (Figure f : model.getFigures()) {
			if (selection.contains(f)) {
				orderedSelection.add(f);
			}
		}
		int pos = 0;
		for (Figure f : orderedSelection) {
			model.setFigureIndex(f, pos++);
		}
	}

	/**
	 * Handles the saving of a drawing to a file.
	 */
	private void doSave() {
		JFileChooser chooser = new JFileChooser(getClass().getResource("").getFile());
		chooser.setDialogTitle("Save Graphic");
		chooser.setDialogType(JFileChooser.SAVE_DIALOG);
		
		chooser.setFileFilter(new FileNameExtensionFilter("JDraw Graphics (*.draw)", "draw"));
		chooser.addChoosableFileFilter(new FileNameExtensionFilter("JDraw Graphics (*.xml)", "xml"));
		chooser.addChoosableFileFilter(new FileNameExtensionFilter("JDraw Graphics (*.json)", "json"));
		
		int res = chooser.showSaveDialog(this);

		if (res == JFileChooser.APPROVE_OPTION) {
			// save graphic
			File file = chooser.getSelectedFile();
			FileFilter filter = chooser.getFileFilter();
			if(filter instanceof FileNameExtensionFilter && !filter.accept(file)) {
				file = new File(chooser.getCurrentDirectory(), file.getName() + "." + ((FileNameExtensionFilter)filter).getExtensions()[0]);
			}
			try {
				ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file));
				for (Figure f : getModel().getFigures()){
					oos.writeObject(f.clone());
				}
				oos.close();
			} catch (IOException ex){
				ex.printStackTrace();
			}
		}
	}

	/**
	 * Handles the opening of a new drawing from a file.
	 */
	private void doOpen() {
		JFileChooser chooser = new JFileChooser(getClass().getResource("")
				.getFile());
		chooser.setDialogTitle("Open Graphic");
		chooser.setDialogType(JFileChooser.OPEN_DIALOG);
		chooser.setFileFilter(new javax.swing.filechooser.FileFilter() {
			@Override
			public String getDescription() {
				return "JDraw Graphic (*.draw)";
			}

			@Override
			public boolean accept(File f) {
				return f.isDirectory() || f.getName().endsWith(".draw");
			}
		});
		int res = chooser.showOpenDialog(this);

		if (res == JFileChooser.APPROVE_OPTION) {
			// read jdraw graphic
			File file = chooser.getSelectedFile();
			try{
				ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));
				Figure figure;
				while (true) {
					try {
						figure = (Figure) ois.readObject();
						getModel().addFigure(figure);
					} catch (EOFException eof){
						break;
					}
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}

}
