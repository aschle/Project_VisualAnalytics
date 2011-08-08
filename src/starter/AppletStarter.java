package starter;

import gui.Main;

/**
 * Simple class used to start a java applet. Use as main class in
 * runnable jars.
 */
public class AppletStarter {

	public static void main(String[] args) {
		Main main = new Main();
		main.init();
		main.start();

		// Create a window (JFrame) and make applet the content pane.
		javax.swing.JFrame window = new javax.swing.JFrame("Strafverfolgungsstatistik-Explorer");

		window.setContentPane(main);
		window.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
		window.pack();
		window.setVisible(true);
		window.setSize(main.screenWidth, main.screenHeight);
	}
}
