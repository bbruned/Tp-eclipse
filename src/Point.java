/**
 * 
 */

/**
 * @author Bruned
 *
 */
public class Point {
	/**
	 * abs du point 
	 */
	protected int x;
	/**
	 * cord du point
	 */
	protected int y;
	/**
	 *  constructeur qui prend en argument 
	 * @param x
	 * @param y
	 */
	public Point(int x, int y) {
		super();
		this.x = x;
		this.y = y;
	}
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	@Override
	public String toString() {
		return "Point [x=" + x + ", y=" + y + "]";
	}
	
}
