package ch.elste.racer;

public class RacerStarter {
	public static Thread renderThread;
	public static RenderLogic renderLogic;

	public static void main(String[] args) {
		renderThread = new Thread(renderLogic);
	}

}
