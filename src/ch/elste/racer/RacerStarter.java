package ch.elste.racer;

import javax.swing.SwingUtilities;

public class RacerStarter {
	public static void main(String[] args) {
		SwingUtilities.invokeLater(RenderLogic.instance());
	}

}
