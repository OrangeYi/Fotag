// HelloMVC: a simple MVC example
// the model is just a counter 
// inspired by code by Joseph Mack, http://www.austintek.com/mvc/

import javax.swing.*;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.RenderingHints;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

class ViewGrid extends JPanel implements Observer {
	private static final long serialVersionUID = 1L;
	// the model that this view is showing
	public ViewModel model;
	public ArrayList<ImageView> imageView;
	private int width;
	ViewGrid(ViewModel model_) {
		model = model_;
		width = getWidth();
		imageView = new ArrayList<ImageView>();
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.addComponentListener(new ComponentAdapter() {
			@Override
    		public void componentResized(ComponentEvent e) {
				width = getWidth();
				resize();
				model.change();
				//System.out.println(width);
			}
		});
	} 
	// Observer interface 
	@Override
	public void update(Observable o, Object arg) {
		removeAll();
		
		for(int i = 0; i < this.imageView.size(); ++i){
			this.imageView.get(i).notifyV();
		}
		
		if(this.imageView.size() != this.model.imageM.size()){
			this.imageView = new ArrayList<ImageView>();
			for(int i = 0; i < model.imageM.size(); ++i){
				this.imageView.add(new ImageView(this.model.imageM.get(i)));
			}
		}
		resize();
	}
	public void resize(){
		removeAll();
		if(width == 0){return;}
		int cols = width/220;
		JPanel mainP = new JPanel();
		mainP.setLayout(new GridLayout(0,cols));
		int count = 0;
		for(int i = 0; i < this.imageView.size();++i){
			if(this.model.imageM.get(i).Rating >= this.model.filter){
				if(count >= cols){
					count = 0;
					this.add(mainP);
					mainP = new JPanel();
					mainP.setLayout(new GridLayout(0,cols));
				}
				JPanel toshow = new JPanel();
				toshow.add(this.imageView.get(i));
				mainP.add(toshow);
				count += 1;
			}else{continue;}
			add(mainP);
		}
		revalidate();
		repaint();
	}
	@Override
	public Dimension getPreferredSize() {
	return new Dimension(getParent().getWidth(), super.getParent().getHeight());
	}
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g; // cast to get 2D drawing methods
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,  // antialiasing look nicer
        					RenderingHints.VALUE_ANTIALIAS_ON);
        System.out.println(getWidth());
    }
} 
