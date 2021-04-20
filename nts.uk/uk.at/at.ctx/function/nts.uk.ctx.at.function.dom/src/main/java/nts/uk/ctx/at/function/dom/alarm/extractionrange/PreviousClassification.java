package nts.uk.ctx.at.function.dom.alarm.extractionrange;

/**
 * @author thanhpv
 * 前・先区分
 */
public enum PreviousClassification {

	/**
	 * 前
	 */
	BEFORE(0, "前"),
	/**
	 * 後
	 */
	AHEAD(1, "後");
	
	/** The value. */
	public final int value;

	/** The name id. */
	public final String nameId;
	
	private PreviousClassification(int value, String nameId) {
		this.value = value;
		this.nameId = nameId;
	}
}
