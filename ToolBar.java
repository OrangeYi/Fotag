import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.*;

class ToolBar extends JPanel implements Observer {
	
	private static final long serialVersionUID = 1L;
	// the model that this view is showing
	public ViewModel model;
	public Ratebar ratebar;
	
	ToolBar(ViewModel model_) {
		this.setBackground(Color.WHITE);
		model = model_;
		this.setLayout(new GridLayout(0,6));
		this.setBorder(BorderFactory.createLineBorder(Color.black,5));
		
		
		
		Image org = null;
		Image img = null;
		JButton grid = new JButton();
		//button to switch to list
		JButton list = new JButton();
        try {
			org = ImageIO.read(new File("list.png"));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        img = org.getScaledInstance(40, 40, Image.SCALE_SMOOTH);
		list.setIcon(new ImageIcon(img));
		list.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				list.setEnabled(false);
				model.enablelistview();
				grid.setEnabled(true);
			}
		});
		this.add(list);
	
		
		
		//button to switch to grid
		
		try {
			org = ImageIO.read(new File("grid.png"));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        img = org.getScaledInstance(40, 40, Image.SCALE_SMOOTH);
		grid.setIcon(new ImageIcon(img));
		grid.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				grid.setEnabled(false);
				model.enablegridview();;
				list.setEnabled(true);
			}
		});
		grid.setEnabled(false);
		this.add(grid);
		
		
		
		//label
		JLabel label = new JLabel();
		try {
			org = ImageIO.read(new File("fotag.png"));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        img = org.getScaledInstance(100, 40, Image.SCALE_SMOOTH);
		label.setIcon(new ImageIcon(img));
		this.add(label);
		
		//open
		JButton file = new JButton();
		try {
			org = ImageIO.read(new File("file.png"));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        img = org.getScaledInstance(40, 40, Image.SCALE_SMOOTH);
		file.setIcon(new ImageIcon(img));
		
		JFileChooser choose = new JFileChooser();
		
		
		file.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				choose.setMultiSelectionEnabled(true);
				choose.showOpenDialog(model.fotag.frame);
				File[] images = choose.getSelectedFiles();
				
				Image img = null;
				for(int i = 0 ; i < images.length; ++i){
					try {
						img = ImageIO.read(images[i]);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					if(img == null){
						System.out.println("Please select photos");
						return;
					}
					model.imageM.add(new ImageModel(images[i].getAbsolutePath(),0,model));
					model.change();
				}
				
			}
			
		});
		
		
		this.add(file);
		
		//rate
		this.ratebar = new Ratebar(0);
		this.add(ratebar);
		
		//reset
		JButton reset = new JButton("\u21BA");
		Font font =new Font("Times new Roman",Font.BOLD,33);
		reset.setFont(font);
        reset.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				model.filter = 0;
                ratebar.update(model.filter);
                model.change();
			}
		});
		this.add(reset);
	}

	// Observer interface 
	@Override
	public void update(Observable o, Object arg) {
		
		
	}
	
	
	//ratebar
		private class Ratebar extends JPanel{
			
			private static final long serialVersionUID = 1L;
			//public int thisrate;
			Ratebar(int rate){
				this.setBackground(Color.WHITE);
				//thisrate = rate;
				//JPanel panel = new JPanel();
				for(int i = 1; i <= 5; ++i){
					if(i<= rate){
						this.add(new Star(i,"f"));
					}else{
						this.add(new Star(i,"n"));
					}
				}
			}
			public void update(int rate){
				//thisrate = rate;
				this.removeAll();
				JPanel panel = new JPanel();
				for(int i = 1; i <= 5; ++i){
					if(i<= rate){
						this.add(new Star(i,"f"));
					}else{
						this.add(new Star(i,"n"));
					}
				}
				this.add(panel);
				this.revalidate();
				this.repaint();
			}
		}
		
		//star
		private class Star extends JLabel{
			
			private static final long serialVersionUID = 1L;
			public int index;
			Star(int i,String s){
				Font font =new Font("Times new Roman",Font.BOLD,33);
				this.setFont(font);
				this.setBackground(Color.WHITE);
				//this.setOpaque(true);
				this.index = i;
				if(s == "f"){
					this.setText("\u2605");
				}
				else{
					this.setText("\u2606");
				}
				this.addMouseListener(new MouseAdapter() {
	                @Override
	                public void mousePressed(MouseEvent e) {
	                	System.out.println(index);
						model.setfilter(index);
						ratebar.update(model.filter);
						model.change();
	                }
	            });
			}
		}
}
