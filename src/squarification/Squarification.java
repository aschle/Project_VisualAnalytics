package squarification;

import java.util.ArrayList;
import org.w3c.dom.css.Rect;

public class Squarification {

	private ArrayList<RectInterface> results = new ArrayList<RectInterface>();

	// orientation
	private boolean isDimX;

	// the length of the side which the row is going to layout
	private int divided;
	private int notDivided;

	// position to start with the layout
	private int posX = 0;
	private int posY = 0;

	public ArrayList<RectInterface> getSquarify(
			ArrayList<RectInterface> children, int dimX, int dimY) {

		if (dimY <= dimX) {
			isDimX = false;
			divided = dimY;
			notDivided = dimX;
		} else {
			isDimX = true;
			divided = dimX;
			notDivided = dimY;
		}

		squarify(children, new ArrayList<RectInterface>());

		return new ArrayList<RectInterface>();
	}

	private void squarify(ArrayList<RectInterface> children,
			ArrayList<RectInterface> row) {

		// if there are no more children
		if (children.size() < 1) {
			return;
		}

		// get the child with the maximal area
		RectInterface child = getMax(children);

		if (worst(row, divided) >= worst(concat(row, child), divided)) {
			squarify(getTail(children), concat(row, child));

		} else {
			layoutRow(row);
			squarify(children, new ArrayList<RectInterface>());
		}
	}

	private float worst(ArrayList<RectInterface> areas, int width) {

		// calculate sum of areas
		int sum = getSum(areas);

		float currentMax = 0;

		for (int i = 0; i < areas.size(); i++) {

			float localMax = Math.max((width * width * areas.get(i).getArea())
					/ (sum * sum), (sum * sum) / width * width
					* areas.get(i).getArea());
			if (localMax > currentMax) {
				currentMax = localMax;
			}

		}
		return currentMax;
	}

	private void layoutRow(ArrayList<RectInterface> row) {

		int sum = getSum(row);
		int notDividedRect = sum / this.divided;

		int offsetX = 0;
		int offsetY = 0;

		if (isDimX == false) {
			offsetY = divided;
		}
		else {
			offsetY = notDivided - notDividedRect;
		}

		for (int i = 0; i < row.size(); i++) {

			int dividedRect = row.get(i).getArea() / notDividedRect;

			if (isDimX == false) {
				offsetY = offsetY - dividedRect;
				row.get(i).setDimention(notDividedRect, dividedRect);
				row.get(i).setStartPoint(offsetX + posX, offsetY + posY);
			} else {
				row.get(i).setDimention(dividedRect, notDividedRect);
				row.get(i).setStartPoint(offsetX + posX, offsetY + posY);
				offsetX = offsetX + dividedRect;
			}
		}

		if (isDimX == false) {
			// move posX more to the rigth
			posX = posX + notDividedRect;
		}
		
		// calculate new dimensions
		int tmp = divided;
		divided = notDivided - notDividedRect;
		notDivided = tmp;

		// dim tauschen
		isDimX = !isDimX;
	}

	/**
	 * Returns the rectangle with the maximal value.
	 * 
	 * @param list
	 * @return Rectangle with the maximal area.
	 */
	private RectInterface getMax(ArrayList<RectInterface> list) {
		RectInterface max = null;
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).getArea() > max.getArea()) {
				max = list.get(i);
			}
		}
		return max;
	}

	/**
	 * 
	 * @param list
	 * @return
	 */
	private ArrayList<RectInterface> getTail(ArrayList<RectInterface> list) {
		ArrayList<RectInterface> tail = new ArrayList<RectInterface>();
		for (int i = 1; i < list.size(); i++) {
			tail.add(list.get(i));
		}
		return tail;
	}

	/**
	 * 
	 * @param list
	 * @param rect
	 * @return
	 */
	private ArrayList<RectInterface> concat(ArrayList<RectInterface> list,
			RectInterface rect) {
		ArrayList<RectInterface> newList = (ArrayList<RectInterface>) list
				.clone();
		newList.add(rect);
		return newList;
	}

	/**
	 * 
	 * @param list
	 * @return
	 */
	private int getSum(ArrayList<RectInterface> list) {
		int sum = 0;
		for (int i = 0; i < list.size(); i++) {
			sum = sum + list.get(i).getArea();
		}
		return sum;
	}
}
