package authoringApp;

public class KeywordInteraction extends Interaction {

	private String keyword;
	
	public KeywordInteraction(String keyword) {
		super("Keyword Interaction");
		this.keyword = keyword;
	}
	
	public KeywordInteraction() {
		this("UNDEFINED");
	}
	
	public void setKeyword(String newKeyword) {
		this.keyword = newKeyword;
	}
	
	public String getKeyword() {
		return new String(this.keyword);
	}
	
	@Override
	public String getType() {
		return Interaction.InteractionTypes.get(Interaction.InteractionType.KEYWORD);
		
	}
	
	@Override
	public String generateScenarioText() {
		String base = "/~";
		return base + this.getKeyword();
	}

}
