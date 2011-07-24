package squarification;

/**
 * 
 * @author Alexa Schlegel
 * 
 */
public interface RectInterface {

	/**
	 * The rectangle needs to have an area.
	 * 
	 * @return the area of the rectangle
	 */
	public int getArea();

	/**
	 * This Method is used to set the dimension of the rectangle.
	 * 
	 * @param dimX
	 *            width
	 * @param dimY
	 *            height
	 */
	public void setDimention(int dimX, int dimY);

	/**
	 * This method is used to set the start point of the rectangle. The start
	 * point is relative to (0|0).
	 * 
	 * @param startX
	 * @param startY
	 */
	public void setStartPoint(int startX, int startY);

}
