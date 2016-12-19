import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Date;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ImageView extends JPanel {
	
	private static final long serialVersionUID = 1L;
	public ImageModel Model;
	public JPanel bottom;
	public Ratebar ratebar;
	public String NAME;
	public String DATE;
	
	ImageView(ImageModel model){
		//this.setMaximumSize(new Dimension(200,230));
		//this.setPreferredSize((new Dimension(200,300)));
		Model = model;
		ratebar = new Ratebar(Model.Rating);
		ratebar.update(Model.Rating);
		this.setLayout(new BorderLayout());
		this.setBackground(Color.WHITE);
		this.setBorder(BorderFactory.createLineBorder(Color.black,5));
		
		//show image
		Image org = null;
		Image img = null;
        try {
			org = ImageIO.read(new FileInputStream(Model.Path));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        img = org.getScaledInstance(200, 230, Image.SCALE_SMOOTH);
        
        JLabel label = new JLabel(new ImageIcon(img));
        this.add(label,BorderLayout.CENTER);
        
        JDialog dialog = new JDialog();
        dialog.setPreferredSize(new Dimension(500,500));
        dialog.setResizable(false);
        dialog.add(new JLabel(new ImageIcon(org.getScaledInstance(400, 400, Image.SCALE_SMOOTH))));
        dialog.pack();
        
        label.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mousePressed(MouseEvent e){
        		dialog.setVisible(true);
        	}
        });
        
        //bottom
        this.bottom = new JPanel();
        bottom.setBackground(Color.WHITE);
        bottom.setLayout(new BoxLayout(bottom,BoxLayout.Y_AXIS));
        
        Path file = Paths.get(model.Path);
        BasicFileAttributeView basicview = Files.getFileAttributeView(file, BasicFileAttributeView.class);
        BasicFileAttributes basicfile = null;
		try {
			basicfile = basicview.readAttributes();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}  
		File e = new File(model.Path);
		String names = e.getName();
        
        //date
        JPanel date = new JPanel();
        //date.setBackground(Color.WHITE);
        JLabel datal = new JLabel();
        datal.setBackground(Color.white);
        DATE = new Date(basicfile.creationTime().toMillis()).toString();
        datal.setText(DATE);
        date.add(datal);
        
        //name
        JPanel name = new JPanel();
        //name.setBackground(Color.WHITE);
        JLabel namel = new JLabel();
        namel.setBackground(Color.white);
        namel.setText(names);
        name.add(namel);
        NAME = names;
        
        //reset
        JButton reset = new JButton();
        reset.setText("\u21BA");
        reset.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Model.Rating = 0;
                ratebar.update(Model.Rating);
                model.Model.change();
			}
		});
        
        
        //add staff
        bottom.add(name);
        bottom.add(date);
        
        JPanel ratepanel = new JPanel();
        ratepanel.setLayout(new BoxLayout(ratepanel,BoxLayout.X_AXIS));
        ratepanel.add(this.ratebar);
        ratepanel.add(reset);
        
        bottom.add(ratepanel);
        this.add(bottom,BorderLayout.SOUTH);
        
        
        
	}
	
	//ratebar
	private class Ratebar extends JPanel{
		
		private static final long serialVersionUID = 1L;
		//public int thisrate;
		Ratebar(int rate){
			//thisrate = rate;
			JPanel panel = new JPanel();
			for(int i = 1; i <= 5; ++i){
				if(i<= rate){
					panel.add(new Star(i,"f"));
				}else{
					panel.add(new Star(i,"n"));
				}
			}
		}
		public void update(int rate){
			//thisrate = rate;
			this.removeAll();
			JPanel panel = new JPanel();
			for(int i = 1; i <= 5; ++i){
				if(i<= rate){
					panel.add(new Star(i,"f"));
				}else{
					panel.add(new Star(i,"n"));
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
			this.setOpaque(true);
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
					Model.Rating = index;
					ratebar.update(Model.Rating);
					Model.Model.change();
                }
            });
		}
	}
	
	public void notifyV(){
		this.ratebar.update(this.Model.Rating);
	}
}
