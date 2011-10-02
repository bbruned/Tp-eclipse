
import java.awt.Color;
import ij.ImagePlus;
import ij.gui.NewImage;
import ij.io.FileSaver;
import ij.process.ImageProcessor;
import java.util.Vector;



/**
 * wrapper pour une image au sens de ImageJ
 * 
 * @author bruned
 *
 */
public class ImageTraitement {
	/**
	 * la veritable image
	 */
	private ImagePlus _image;
	
	
	/**
	 * creation d'une image vierge ˆ partir de ses dimensions
	 * @param w largeur
	 * @param h hauteur
	 */
	public ImageTraitement(int w,int h) {
		_image = NewImage.createByteImage("test",w,h,1,NewImage.FILL_BLACK);
		_image.getProcessor().setColor(Color.WHITE);
	}

	/**
	 * creation d'une image ˆ partir d'une image sur le disque en donnant son chemin d'acces.
	 */
	public ImageTraitement(String path){
		_image = new ImagePlus(path);
	}
	
	/**
	 * accesseur
	 * @return la largeur de l'image
	 */
	public int getWidth() {
		return _image.getWidth();
	}
	
	/**
	 * accesseur
	 * @return la hauteur de l'image
	 */
	public int getHeight() {
		return _image.getHeight();
	}

	
	/**
	 * accesseur sur l'image interne (au sens d'ImageJ)
	 * @return
	 */
	private ImageProcessor getImageProcessor() {
		return _image.getProcessor();
	}
	
	/**
	 * "allume" le pixel de coordonnees (x,y) avec la valeur value.
	 * de points traces
	 * @param x
	 * @param y
	 */
	public void setPixel(int x,int y, int value) {
		byte[] pixels = (byte[]) _image.getProcessor().getPixels();
		pixels[y*_image.getWidth()+x] =  (byte)value;
	}
	
	/**
	 * permet d'obtenir les valeurs de chaque plan de couleur dans un tableau de dim 4 
	 * -les trois premieres valeurs R-G-B
	 * - pour une image en niveaux de gris, seul le premier element a une valeur
	 * @param x
	 * @param y
	 * @return
	 */
	public int[] getPixel(int x, int y){
		return _image.getPixel(x, y);
	}
	
	/**
	 * enregistre l'image construite au format PNG
	 * @param fileName le nom du fichier (doit contenir l'extension .png)
	 */
	public void saveAsPng(String fileName) {
		FileSaver fs = new FileSaver(_image);
		fs.saveAsPng(fileName);
	}
	
	/** 
	 * Affiche l'image
	 * 
	 */	
	public void show(){
		_image.show();
	}
	/**
	 * mise au niveau de gris 
	 * @return
	 */
	public ImageTraitement gris(){
		ImageTraitement gris=new ImageTraitement(getWidth(),getHeight());
		int i,j,a;
		for(i=0;i<getWidth();i++){
			for(j=0;j<getHeight();j++){
				a =(int)Math.round(0.299*getPixel(i,j)[0]+0.587*getPixel(i,j)[1]+0.114*getPixel(i,j)[2]);
				gris.setPixel(i,j,a);
		    }
		}
		return gris;
	}
	/**
	 * seuillage à partir d'une valeur de seuil
	 * @param seuil
	 * @return
	 */
	public ImageTraitement binaire(int seuil){
		ImageTraitement binaire=new ImageTraitement(getWidth(),getHeight());
		int i,j;
		for(i=0;i<getWidth();i++){
			for(j=0;j<getHeight();j++){
				if (getPixel(i,j)[0]<seuil) {
					binaire.setPixel(i,j,0);
				}
				if (getPixel(i,j)[0]>=seuil) {
					binaire.setPixel(i,j,255);
				}
		  }
		}
		return binaire;
	}
	/**
	 * création de l'histogramme
	 * @return
	 */
	public ImageTraitement histogramme(){
		//ImageTraitement histogramme =new ImageTraitement(256,getHeight()*getWidth());
		Vector<Integer> p = new Vector<Integer> (0,256);
		int i;
		for (i=0;i<256;i++){
		p.add(0);	
		}
	    int x,y ;
		for (x=0;x<getWidth();x++){
			for (y=0;y<getHeight();y++){
				int i0=getPixel(x,y)[0];
				p.set(i0,p.get(i0)+1);
				//histogramme.setPixel(i0,getHeight()*getWidth()-(p.get(i0)),100);
			}
		}
		int j;
		for (j=0;j<256;j++){
		p.set(j,(int) Math.round(p.get(j)/11));	
			}
		ImageTraitement histogramme =new ImageTraitement(256,300);
		int s ,t;
		for (s=0;s<256;s++){
			for(t=0;t<p.get(s);t++){
				histogramme.setPixel(s,299-t,255);
			}
		}
		return histogramme ;
	}
	/**
	 * fonction de différence entre deux images
	 * @param image
	 * @return
	 */
	public ImageTraitement difference(ImageTraitement image){
		int L=this.getWidth();
		int h=this.getHeight();
		ImageTraitement res =new ImageTraitement(L,h);
		for(int i=0;i<L;i++)
			for(int j=0;j<h;j++)
				{
				int a=(int)Math.abs(this.getPixel(i,j)[0]-image.getPixel(i,j)[0]);
				res.setPixel(i, j,a);
				}
		return res;
	}
}


