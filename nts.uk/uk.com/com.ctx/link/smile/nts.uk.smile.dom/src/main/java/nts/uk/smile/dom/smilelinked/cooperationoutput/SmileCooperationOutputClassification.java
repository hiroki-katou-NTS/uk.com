package nts.uk.smile.dom.smilelinked.cooperationoutput;

/**
 * Smile連携出力区分
 * 
 */
public enum SmileCooperationOutputClassification {

	/** しない */
	DO_NOT(0, "しない"),

	/** する */
	DO(1, "する");
	
	public int value;
	public String nameId;
	
	private SmileCooperationOutputClassification (int value, String nameId) {
		this.value = value;
		this.nameId = nameId;
	}
}
