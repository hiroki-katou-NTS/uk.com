package nts.uk.ctx.at.function.dom.alarm.extractionrange;

/**
 * @author thanhpv
 * 前・先区分
 */
public enum PreviousClassification {

	/**
	 * 前
	 */
	BEFORE(0, "EnumPreviousClassification_BEFORE", "前"),
	/**
	 * 先
	 */
	AHEAD(1, "EnumPreviousClassification_AHEAD", "先");
	
	/** The value. */
	public final int value;

	/** The name id. */
	public final String nameId;

	/** The description. */
	public final String description;

	/** The Constant values. */
	private final static PreviousClassification[] values = PreviousClassification.values();
	
	private PreviousClassification(int value, String nameId, String description) {
		this.value = value;
		this.nameId = nameId;
		this.description = description;
	}
	public static PreviousClassification valueOf(Integer value) {
		// Invalid object.
		if (value == null) {
			return null;
		}

		// Find value.
		for (PreviousClassification val : PreviousClassification.values) {
			if (val.value == value) {
				return val;
			}
		}

		// Not found.
		return null;
	}
}
