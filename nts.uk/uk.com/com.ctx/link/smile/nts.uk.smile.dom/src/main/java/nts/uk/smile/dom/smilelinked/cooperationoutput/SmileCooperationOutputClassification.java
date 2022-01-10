package nts.uk.smile.dom.smilelinked.cooperationoutput;

/**
 * Smile連携出力区分
 * 
 */
public enum SmileCooperationOutputClassification {

	/** しない */
	DO_NOT(0),

	/** する */
	DO(1);
	
	public int value;
	
	private SmileCooperationOutputClassification (int value) {
		this.value = value;
	}
}
