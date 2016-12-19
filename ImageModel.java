
public class ImageModel {
	public ViewModel Model;
	public String Path;
	public int Rating;
	
	ImageModel(String path, int rate, ViewModel Vmodel){
		Path = path;
		Rating = rate;
		Model = Vmodel;
	}
	public void setRating(int r){
		Rating = r;
	}
	public void notifyView(){
		
	}
}
