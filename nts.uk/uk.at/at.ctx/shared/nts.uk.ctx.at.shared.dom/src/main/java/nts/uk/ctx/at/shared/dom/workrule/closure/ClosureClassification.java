package nts.uk.ctx.at.shared.dom.workrule.closure;

public enum ClosureClassification {

	// 締め日変更前期間
	ClassificationClosingBefore(0),
	
	// 締め日変更後期間
	ClassificationClosingAfter(1);
	
	/** The value. */
	public int value;
	
	/** The Constant values. */
	private final static ClosureClassification[] values = ClosureClassification.values();
	
	/**
	 * Instantiates a new closing classification.
	 *
	 * @param value the value
	 */
	private ClosureClassification(int value) {
		this.value = value;
	}
	
	/**
	 * Value of.
	 *
	 * @param value the value
	 * @return the use classification
	 */
	public static ClosureClassification valueOf(Integer value) {
		// Invalid object.
		if (value == null) {
			return null;
		}

		// Find value.
		for (ClosureClassification val : ClosureClassification.values) {
			if (val.value == value) {
				return val;
			}
		}

		// Not found.
		return null;
	}
}
