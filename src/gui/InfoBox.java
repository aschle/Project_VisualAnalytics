package gui;

import java.text.DecimalFormat;
import java.util.Locale;

import processing.core.PConstants;
import squarification.RectInterface;

public class InfoBox {

	private RectInterface parentRect;
	private Diagram parentDia;
	private Main parentMain;

	private String title;

	private int titleLines = 1;
	private int space = 5;

	private int w = 260;
	private int h = 150;

	private int borderX = 10;
	private int borderY = 15;

	int textW;
	int textH;

	private String[] labels = { "gegen die Person", "Bundes", "sexuelle",
			"Raub", "öffentliche", "Umwelt", "Straßenverkehr",
			"Betäubungsmittel", "Aufenthaltsgesetz", "schwere" };

	public InfoBox(String title, RectInterface parentRect, Diagram parent,
			Main parentApplet) {

		this.parentRect = parentRect;
		this.parentDia = parent;
		this.parentMain = parentApplet;
		this.title = title;

		for (int i = 0; i < labels.length; i++) {

			if (title.contains(labels[i]))
				titleLines = 2;
		}

		textW = w - 2 * borderX;
		textH = h - 2 * borderY;
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

		int x = parentMain.mouseX - offsetX;
		int y = parentMain.mouseY - offsetY;

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
		parentMain.text(title, x, y, textW, textH);
		parentMain.textFont(parentMain.f, 14);

		// Borderline
		parentMain.fill(100);

		y = y + titleLines * 20 + titleLines * space;

		parentMain.rect(x, y, w - 2 * borderX, 1);

		// Gesamtanzahl
		y = y + 20;
		parentMain.text("Gesamtanzahl: ", x, y);
		parentMain.textAlign(PConstants.RIGHT);

		DecimalFormat df = (DecimalFormat) DecimalFormat
				.getInstance(Locale.GERMAN);
		df.applyPattern("#,###,##0");

		String s;
		if (parentRect instanceof SubRectangle) {
			s = df.format(((SubRectangle) parentRect).parentRect.getArea());
		} else {
			s = df.format(parentDia.sum);
		}

		parentMain.fill(0);
		parentMain.text(s, x + textW, y);
		parentMain.textAlign(PConstants.LEFT);
		parentMain.fill(100);

		// Absolute Zahlen
		y = y + 20;
		parentMain.text("Verurteilte: ", x, y);

		if (parentRect instanceof SubRectangle) {
			s = df.format(parentRect.getArea());
		} else {
			s = df.format(parentRect.getArea());
		}

		parentMain.textAlign(PConstants.RIGHT);

		parentMain.fill(0);
		parentMain.text(s, x + textW, y);
		parentMain.textAlign(PConstants.LEFT);

		// BorderLine
		parentMain.fill(100);
		y = y + space + 5;
		parentMain.rect(x, y, w - 2 * borderX, 1);

		// Numbers in percent
		y = y + 20;
		double numberPercent = (100 * parentRect.getArea()) / parentDia.sum;
		double my = Math.round(numberPercent * 100.) / 100.;
		DecimalFormat dfnew = new DecimalFormat("0.00");
		s = dfnew.format(my);
		parentMain.fill(0);
		parentMain.textAlign(PConstants.RIGHT);
		parentMain.text(s.concat(" %"), x + textW, y);
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
