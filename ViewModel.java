import java.awt.BorderLayout;
import java.awt.Point;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Scanner;



public class ViewModel extends Observable {	
	public Fotag fotag;
	public ArrayList<ImageModel> imageM;
	public int filter;
	
	ViewModel(Fotag fo) {
		this.fotag = fo;
		this.imageM = new ArrayList<ImageModel>();
		this.filter = 0;
		load();
		setChanged();
	}
	public void setfilter(int f){
		filter = f;
	}
	public void load(){
		try {
            Scanner in = new Scanner(new File("load.txt"));
            while (in.hasNextLine()) {
                String path = in.nextLine();
                int rate = Integer.valueOf(in.nextLine()).intValue();
                this.imageM.add(new ImageModel(path,rate,this));
            }
        } catch (FileNotFoundException e1) {}
	}
	
	
//	public void save(){
//		String fileName="load.txt";
//		 try
//		 {
//		         FileWriter writer=new FileWriter(fileName);
//		         for(int i = 0; i < this.imageM.size();++i){
//		        	 String p = this.imageM.get(i).Path;
//		        	 int r = this.imageM.get(i).Rating;
//		        	 writer.write(p + "\n");
//		        	 writer.write(r + "\n");
//		         }
//		         writer.close();
//		 } catch (IOException e)
//		 {
//		         e.printStackTrace();
//		 }
//		 
//	}
	
	public void enablegridview(){
		this.fotag.panel.remove(this.fotag.list);
		this.fotag.panel.add(this.fotag.grid,BorderLayout.CENTER);
		this.fotag.panel.revalidate();
		this.fotag.panel.repaint();
	}
	
	public void enablelistview(){
		this.fotag.panel.remove(this.fotag.grid);
		this.fotag.panel.add(this.fotag.list,BorderLayout.CENTER);
		this.fotag.panel.revalidate();
		this.fotag.panel.repaint();
	}
	
	public void change() {
		setChanged();
		notifyObservers();
	}
}
