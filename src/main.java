
public class main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("Hello world");
		ImageTraitement lena = new ImageTraitement("./lena512x512.pgm");
		lena.show();
		lena.histogramme().show();
		ImageTraitement peppers = new ImageTraitement("./peppers512x512.pgm");
		peppers.show();	
		lena.binaire(100).show();
		lena.difference(peppers).histogramme().show();
	}
	
}