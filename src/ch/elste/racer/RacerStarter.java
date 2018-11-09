package ch.elste.racer;

public class RacerStarter {
	public static Thread renderThread;
	public static RenderLogic renderLogic;

	public static void main(String[] args) {
		try {
			renderLogic = new RenderLogic();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		renderThread = new Thread(renderLogic);
		renderThread.start();
	}

}
