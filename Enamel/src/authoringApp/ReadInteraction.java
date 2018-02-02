package authoringApp;

public class ReadInteraction extends Interaction {

	private String data;
	
	public ReadInteraction() {
		super();
		this.data = "";
	}
	
	public ReadInteraction(String title) {
		super(title);
		this.data = "";
	}
	
	public ReadInteraction(ReadInteraction toCopy) {
		super(toCopy.title);
		this.data = new String(toCopy.data);
	}
	
	public String getData() {
		return new String(this.data);
	}
	
	public void setData(String d) {
		this.data = d;
	}
	@Override
	public String generateScenarioText() {
		return this.getData();
	}

}
