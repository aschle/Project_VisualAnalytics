package gui;

import java.awt.Color;
import java.sql.ResultSet;
import java.util.ArrayList;

import database.Querying;
import processing.core.PConstants;
import processing.core.PFont;
import squarification.RectInterface;
import squarification.Squarification;

public class Rectangle implements RectInterface {

	private double area;
	private int dimX;
	private int dimY;
	private int startX;
	private int startY;
	private String title; // Category
	private Color color = new Color(0xFF0000);

	private static int margin = 1;

	// Subgroups
	ArrayList<SubRectangle> subRects = new ArrayList<SubRectangle>();
	private int sum;

	// Remember Klicked
	private boolean klicked;

	Diagram parent;

	public Rectangle(double area, String title, char category, Diagram parent) {
		this.area = area;
		this.parent = parent;
		this.title = title;
		this.klicked = false;
		setColor(category);
	}

	public void getSubRectangles() throws Exception {

		subRects = new ArrayList<SubRectangle>();
		sum = 0;
		
		boolean[] gender = parent.genderSlider.getState();
		boolean[] origin = parent.originSlider.getState();
		boolean[] age = parent.ageSlider.getState();

		ResultSet rs = Querying.getSubDBvaluesByFilter(title, gender, origin, age);

		while (rs.next()) {
			subRects.add(new SubRectangle(rs.getInt(1), rs.getString(2), rs
					.getString(3).toCharArray()[0], parent));
			sum = sum + rs.getInt(1);
		}

		Squarification s = new Squarification();
		s.getSquarify(subRects, dimX - 8, dimY - 8);

		for (int i = 0; i < subRects.size(); i++) {
			subRects.get(i).setOffset(startX + 4, startY + 4);
		}
	}

	@Override
	public double getArea() {
		return area;
	}

	@Override
	public void setDimention(int dimX, int dimY) {
		this.dimX = dimX;
		this.dimY = dimY;
	}

	@Override
	public void setStartPoint(int startX, int startY) {
		this.startX = startX;
		this.startY = startY;
	}

	public void setColor(char category) {
		switch (category) {
		case 'A':
			color = new Color(0x009ECE); // blue
			break;
		case 'B':
			color = new Color(0xFF9E00); // orange
			break;
		case 'C':
			color = new Color(0xF7D708); // yellow
			break;
		case 'D':
			color = new Color(0xCE0000); // red
			break;
		case 'E':
			color = new Color(0x7DCC00); // green
			break;
		case 'F':
			color = new Color(0x339900); // dark green
			break;
		case 'G':
			color = new Color(0xAC54AA); // purple
			break;
		case 'H':
			color = new Color(0x666666); // grey
			break;
		case 'I':
			color = new Color(0x2956B2); // blue
			break;
		default:
			break;
		}
	}

	public void setOffset(int offsetX, int offsetY) {
		startX += offsetX;
		startY += offsetY;
	}

	public void display() {

		parent.parent.fill(color.getRed(), color.getGreen(), color.getBlue());
		parent.parent.rect((float) (startX + margin), (float) (startY + margin),
				(float) (dimX - 2 * margin), (float) (dimY - 2 * margin));

		if (klicked == true) {
			for (int i = 0; i < subRects.size(); i++) {
				subRects.get(i).display();
			}
			
			for (int i = 0; i < subRects.size(); i++) {
				subRects.get(i).mouseText();
			}
		}
	}

	public void mouseText() {
		if (!klicked) {
			if (parent.parent.mouseX > startX && parent.parent.mouseX < startX + dimX
					&& parent.parent.mouseY > startY && parent.parent.mouseY < startY + dimY) {
				parent.parent.fill(0);
				PFont f = parent.parent.createFont("FFScala", 20);
				parent.parent.textFont(f);
				parent.parent.textAlign(PConstants.CENTER);
				parent.parent.text(title, parent.parent.mouseX, parent.parent.mouseY);
			}
		}
	}

	public void mouseKlick() {

		if (parent.parent.mouseX > startX && parent.parent.mouseX < startX + dimX
				&& parent.parent.mouseY > startY && parent.parent.mouseY < startY + dimY) {

			klicked = !klicked;

			try {
				getSubRectangles();
			} catch (Exception e) {
				System.out
						.println("Error, while getting subCategries from DB.");
				e.printStackTrace();
			}
		}

	}
}
