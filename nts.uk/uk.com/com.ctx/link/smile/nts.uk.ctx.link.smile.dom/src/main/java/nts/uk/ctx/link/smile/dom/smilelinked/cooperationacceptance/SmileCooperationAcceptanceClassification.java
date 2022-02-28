package nts.uk.smile.dom.smilelinked.cooperationacceptance;

/**
 * Smile連携受入区分
 *
 */
public enum SmileCooperationAcceptanceClassification {
	/** しない */
	DO_NOT(0, "しない"),

	/** する */
	DO(1, "する");

	public int value;
	public String nameId;

	private SmileCooperationAcceptanceClassification(int value, String nameId) {
		this.value = value;
		this.nameId = nameId;
	}
}
