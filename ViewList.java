import javax.swing.*;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.*;

class ViewList extends JPanel implements Observer {

	private static final long serialVersionUID = 1L;
	// the model that this view is showing
	public ViewModel model;
	public ArrayList<ImageView> imageView;
	ViewList(ViewModel model_) {
		model = model_;
		this.setBackground(Color.WHITE);
		imageView = new ArrayList<ImageView>();
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
	}

	// Observer interface 
	@Override
	public void update(Observable o, Object arg) {
		this.removeAll();
		
		for(int i = 0; i < this.imageView.size(); ++i){
			this.imageView.get(i).notifyV();
		}
		
		if(this.imageView.size() != this.model.imageM.size()){
			this.imageView = new ArrayList<ImageView>();
			for(int i = 0; i < model.imageM.size(); ++i){
				this.imageView.add(new ImageView(this.model.imageM.get(i)));
			}
		}
		for(int i = 0; i < this.imageView.size(); ++i){
			if(this.model.imageM.get(i).Rating >= this.model.filter){
				JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
				panel.add(this.imageView.get(i));
				this.add(panel);
				
				JPanel right = new JPanel();
				right.setLayout(new GridLayout(2,0));
				JLabel name = new JLabel();
				name.setText(this.imageView.get(i).NAME);
				JLabel date = new JLabel();
				date.setText(this.imageView.get(i).DATE);
				Font font =new Font("Times new Roman",Font.BOLD,33);
				name.setFont(font);
				date.setFont(font);
				right.add(name);
				right.add(date);
				panel.add(right);
			}
		}
		this.revalidate();
		this.repaint();
	}
}
