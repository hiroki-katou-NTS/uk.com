package nts.uk.smile.dom.smilelinked.cooperationacceptance;

/**
 * Smile連携受入区分
 *
 */
public enum SmileCooperationAcceptanceClassification {
	/** しない */
	DO_NOT(0),

	/** する */
	DO(1);
							
	public int value;
	
	private SmileCooperationAcceptanceClassification (int value) {
		this.value = value;
	}
}
