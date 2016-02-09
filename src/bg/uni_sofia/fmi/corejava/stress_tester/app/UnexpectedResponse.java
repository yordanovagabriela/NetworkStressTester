package bg.uni_sofia.fmi.corejava.stress_tester.app;


public class UnexpectedResponse extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3946472852576308749L;

	public UnexpectedResponse(String message) {
		super(message);
	}
}
