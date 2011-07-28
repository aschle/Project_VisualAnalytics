package squarification;

import java.util.ArrayList;
import java.util.Random;

public class Squarification {

	private ArrayList<RectInterface> results = new ArrayList<RectInterface>();

	// orientation
	private boolean isDimX;

	// the length of the side which the row is going to layout
	private double divided;
	private double notDivided;

	// position to start with the layout
	private double posX = 0;

	public void getSquarify(ArrayList<? extends RectInterface> children,
			double dimX, double dimY) {

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
	}

	private void squarify(ArrayList<? extends RectInterface> children,
			ArrayList<RectInterface> row) {

		// if there are no more children
		if (children.size() < 1) {
			layoutRow(row);
			return;
		}

		// get the child with the maximal area
		RectInterface child = getMax(children);
		
		if (row.isEmpty() || worst(row) >= worst(concat(row, child))) {

			squarify(getTail(children, child), concat(row, child));

		} else {
			layoutRow(row);
			squarify(children, new ArrayList<RectInterface>());
		}
	}

	private double worst(ArrayList<RectInterface> areas) {

		// calculate sum of areas
		double sum = getSum(areas);

		double currentMax = 0;

		for (int i = 0; i < areas.size(); i++) {
			
			double a = Math.pow(divided, 2) * areas.get(i).getArea();
			double b = Math.pow(sum, 2);

			double localMax = Math.max(a/b,b/a);
			
			if (localMax > currentMax) {
				currentMax = localMax;
			}
		}
		System.out.println(currentMax);
		return currentMax;
	}

	private void layoutRow(ArrayList<RectInterface> row) {
		System.out.println("new");
		
		double sum = getSum(row);
		double notDividedRect = sum / this.divided;
		
		double offsetX = 0;
		double offsetY = 0;

		if (isDimX == false) {
			offsetY = divided;
		} else {
			offsetY = notDivided - notDividedRect;
		}

		for (int i = 0; i < row.size(); i++) {

			System.out.println("area: "+ row.get(i).getArea() + " "+ notDividedRect);
			double dividedRect = row.get(i).getArea() / notDividedRect;

			if (isDimX == false) {
				offsetY = offsetY - dividedRect;
				row.get(i).setDimention((int)notDividedRect, (int)dividedRect);
				row.get(i).setStartPoint((int) (offsetX + posX), (int) offsetY);
			
			} else {
				row.get(i).setDimention( (int)dividedRect, (int)notDividedRect);
				row.get(i).setStartPoint((int) (offsetX + posX), (int) offsetY);
				offsetX = offsetX + dividedRect;
			}

			results.add(row.get(i));
		}

		if (isDimX == false) {
			// move posX more to the rigth
			posX = posX + notDividedRect;
		}

		// calculate new dimensions
		double tmp = divided;
		divided = notDivided - notDividedRect;
		notDivided = tmp;

		// dim tauschen
		isDimX = !isDimX;
	}

	/**
	 * Returns the rectangle with the maximal value.
	 * 
	 * @param children
	 * @return Rectangle with the maximal area.
	 */
	private RectInterface getMax(ArrayList<? extends RectInterface> children) {
		RectInterface max = children.get(0);
		for (int i = 1; i < children.size(); i++) {
			if (children.get(i).getArea() > max.getArea()) {
				max = children.get(i);
			}
		}
		return max;
	}

	/**
	 * 
	 * @param children
	 * @return
	 */
	private ArrayList<RectInterface> getTail(
			ArrayList<? extends RectInterface> children, RectInterface child) {
		ArrayList<RectInterface> tail = new ArrayList<RectInterface>();
		for (RectInterface r : children) {
			tail.add(r);
		}
		tail.remove(child);
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

		ArrayList<RectInterface> newList = new ArrayList<RectInterface>();
		for (RectInterface r : list) {
			newList.add(r);
		}

		newList.add(rect);
		return newList;
	}

	/**
	 * 
	 * @param list
	 * @return
	 */
	private double getSum(ArrayList<RectInterface> list) {
		double sum = 0;
		for (int i = 0; i < list.size(); i++) {
			sum = sum + list.get(i).getArea();
		}
		return sum;
	}
}
