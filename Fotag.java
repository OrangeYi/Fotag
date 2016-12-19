// HelloMVC: a simple MVC example
// the model is just a counter 
// inspired by code by Joseph Mack, http://www.austintek.com/mvc/

/**
 *  Two views with integrated controllers.  Uses java.util.Observ{er, able} instead
 *  of custom IView.
 */

import javax.swing.*;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.*;
import java.io.FileWriter;
import java.io.IOException;	

public class Fotag{
	public JFrame frame;
	public JPanel panel;
	public JScrollPane grid;
	public JScrollPane list;
	public ToolBar toolbar;
	Fotag(){	
		this.frame = new JFrame("Fotag");
		this.panel = new JPanel(new BorderLayout());
		
		//model
		ViewModel viewmodel = new ViewModel(this);
		//System.out.println(viewmodel.filter);
		
		//grid
		ViewGrid viewgrid = new ViewGrid(viewmodel);
		viewmodel.addObserver(viewgrid);
		grid = new JScrollPane(viewgrid);
		grid.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		grid.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		
		//list
		ViewList viewlist = new ViewList(viewmodel);
		viewmodel.addObserver(viewlist);
		list = new JScrollPane(viewlist);
		list.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		list.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		
		//toolbar
		toolbar = new ToolBar(viewmodel);
		viewmodel.addObserver(toolbar);
		
		// let all the views know that they're connected to the model
		viewmodel.notifyObservers();

		//set panel (default grid)
		panel.add(toolbar, BorderLayout.NORTH);
		panel.add(grid,BorderLayout.CENTER);
		
		
		//set the frame
		frame.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e){
				String fileName="load.txt";
				 try
				 {
					 FileWriter writer=new FileWriter(fileName);
					 for(int i = 0; i < viewmodel.imageM.size();++i){
				        String p = viewmodel.imageM.get(i).Path;
				        int r = viewmodel.imageM.get(i).Rating;
				        writer.write(p + "\n");
				        writer.write(r + "\n");
					 }
				         writer.close();
				 } catch (IOException e1){
					 e1.printStackTrace();
				 }			
				 }
		});
		frame.setPreferredSize(new Dimension(1200,800));
		frame.setMinimumSize(new Dimension(350,300));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.setResizable(true);
		frame.pack();
	} 
	public static void main(String[] args) {
		Fotag fotag = new Fotag();
		fotag.frame.add(fotag.panel);
	}
}
