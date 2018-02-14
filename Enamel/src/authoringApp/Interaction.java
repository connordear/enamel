package authoringApp;


/*
 * Class is meant to be inherited and used to create the other Interaction classes
 */
public abstract class Interaction {
	
	private static int id_counter = 0;
	private int id;
	private String title;
	public static final String READ = "READ";
	public static final String VOICE = "VOICE";
	public static final String DISPLAY_BRAILLE = "DISPLAY BRAILLE";
	public static final String KEYWORD = "KEYWORD";
	public static final String PAUSE = "PAUSE";
	public static final String SKIP_BUTTON = "SKIP BUTTON";
	public static final String USER_INPUT = "USER INPUT";
	public static final String RESET_BUTTONS = "RESET BUTTONS";
	public static final String CLEAR_BRAILLE = "CLEAR BRAILLE";	
	public Interaction() {
		this("Untitled");
	}
	
	public Interaction(String title) {
		this.title = title;
		this.id = id_counter;
		id_counter++;
	}
	
	public void setTitle(String newTitle) {
		this.title = newTitle;
	}
	
	public String getTitle() {
		return this.title;
	}
	
	@Override
	public String toString() {
		return this.getType();
	}
	
	public int getId() {
		return this.id;
	}
	
	abstract public String generateScenarioText();
	
	abstract public String getType();
}
