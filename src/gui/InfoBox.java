package gui;

import java.awt.image.TileObserver;
import java.text.DecimalFormat;
import java.util.Locale;
import processing.core.PConstants;

public class InfoBox {

	private Rectangle parentRect;
	private Diagram parentDia;
	private Main parentMain;

	private String title;

	private int titleLines = 1;
	private int space = 10;

	private int w = 360;
	private int h = 250;

	private int borderX = 10;
	private int borderY = 15;

	int textWTitle;
	int textH;

	int textWcategory;

	int leading = 16;

	// for positioning the text
	int x;
	int y;

	int X;
	int Y;

	int lines;

	private String[] labels = { "Bundes", "sexuelle", "Raub", "öffentliche",
			"Umwelt" };

	private String[] subLabels = {};

	public InfoBox(String title, Rectangle parentRect, Diagram parent,
			Main parentApplet) {

		this.parentRect = parentRect;
		this.parentDia = parent;
		this.parentMain = parentApplet;
		this.title = title;

		for (int i = 0; i < labels.length; i++) {

			if (title.contains(labels[i]))
				titleLines = 2;
		}

		textWTitle = w - 2 * borderX;
		textH = h - 2 * borderY;
		textWcategory = (textWTitle / 5) * 3 + space;

	}

	public void display() {

		if (isLeft() && isTop()) {
			drawBox(0, 0);
		}

		if (isLeft() && !isTop()) {
			drawBox(0, h);
		}

		if (!isLeft() && isTop()) {
			drawBox(w, 0);
		}

		if (!isLeft() && !isTop()) {
			drawBox(w, h);
		}
	}

	private void drawBox(int offsetX, int offsetY) {

		x = parentMain.mouseX - offsetX;
		y = parentMain.mouseY - offsetY;
		X = parentMain.mouseX - offsetX;
		Y = parentMain.mouseY - offsetY;

		// Rectangle(background)
		parentMain.stroke(100, 100, 100);
		parentMain.strokeWeight(2);
		parentMain.fill(255, 255, 255, 240);
		parentMain.rect(x, y, w, h);
		parentMain.noStroke();

		// Title
		x = x + borderX;
		y = y + borderY;

		parentMain.fill(0);
		parentMain.textFont(parentMain.fbold);
		parentMain.textAlign(PConstants.LEFT);
		parentMain.text(title, x, y, textWTitle, textH);

		// Borderline
		parentMain.fill(100);
		y = y + titleLines * 20 + titleLines * space;
		parentMain.rect(x, y, w - 2 * borderX, 1);

		// neuen Text laden
		parentMain.textFont(parentMain.fsmall);
		parentMain.textLeading(leading);

		y = y + space;

		if (parentRect.subRects.size() > 1) {
			// Unterkategorie % #

			// Sonstiges soll ans Ende
			String titleSonstiges = "Sonstiges";
			double valueSonstiges = 0;

			for (SubRectangle r : parentRect.subRects) {

				if (r.title.equals("Sonstiges")) {
					valueSonstiges = r.getArea();
				} else {

					printLine(r.title, r.getArea());
				}
			}

			if (valueSonstiges != 0) {
				printLine(titleSonstiges, valueSonstiges);
			}

			// Border
			parentMain.fill(100);
			parentMain.rect(x, y, w - 2 * borderX, 1);
		}

		// Gesamtanzahl % Summe
		parentMain.fill(0);
		DecimalFormat komma = new DecimalFormat("0.00");
		String sPercent = komma
				.format(Math.round((parentRect.getArea() * 100 / parentDia.sum) * 100.) / 100.);
		y = y + space;
		parentMain.text("Gesamt:", x, y, textWcategory, 20);
		int tempX = x + textWcategory - space;
		parentMain.textAlign(PConstants.RIGHT);
		parentMain.text(sPercent.concat(" %"), tempX, y, 60, 20);
		tempX = X + textWTitle - 40;
		DecimalFormat dots = (DecimalFormat) DecimalFormat
				.getInstance(Locale.GERMAN);
		dots.applyPattern("#,###,##0");
		String sAbsolut = dots.format(parentRect.getArea());
		parentMain.text(sAbsolut, tempX, y, 50, 20);

	}

	private void printLine(String title, double value) {

		// Gesamtsumme
		int mainSum = (int) parentDia.sum;

		// Dezimaldarstellung
		DecimalFormat dots = (DecimalFormat) DecimalFormat
				.getInstance(Locale.GERMAN);
		dots.applyPattern("#,###,##0");
		DecimalFormat komma = new DecimalFormat("0.00");

		String sAbsolut = dots.format(value);
		String sPercent = komma.format(Math
				.round((value * 100 / mainSum) * 100.) / 100.);

		lines = (int) Math.ceil(parentMain.textWidth(title) / textWcategory);

		parentMain.text(title, x, y, textWcategory, textH);
		int tempX = x + textWcategory - space;
		parentMain.textAlign(PConstants.RIGHT);
		parentMain.text(sPercent.concat(" %"), tempX, y, 60, 20);

		tempX = X + textWTitle - 40;

		parentMain.text(sAbsolut, tempX, y, 50, 20);
		y = y + lines * leading + space;
		parentMain.textAlign(PConstants.LEFT);
	}

	private boolean isLeft() {
		if (parentMain.mouseX < parentDia.areaSartX + parentDia.dimX - w + 70) {
			return true;

		} else
			return false;

	}

	private boolean isTop() {

		if (parentMain.mouseY < parentDia.areaStartY + parentDia.dimY - h + 20) {
			return true;

		} else
			return false;
	}
}
