package nts.uk.ctx.pr.core.dom.vacationsetting.yearvacationsetting;

public enum PreemptionPermit {

	/** The Not allow. */
	FIFO(0),

	/** The Allow. */
	LIFO(1);

	/** The value. */
	public int value;

	/** The Constant values. */
	private final static PreemptionPermit[] values = PreemptionPermit.values();

	/**
	 * Instantiates a new preemption permit.
	 *
	 * @param value the value
	 */
	private PreemptionPermit(int value) {
		this.value = value;
	}

	/**
	 * Value of.
	 *
	 * @param value the value
	 * @return the preemption permit
	 */
	public static PreemptionPermit valueOf(Integer value) {
		// Invalid object.
		if (value == null) {
			return null;
		}

		// Find value.
		for (PreemptionPermit val : PreemptionPermit.values) {
			if (val.value == value) {
				return val;
			}
		}
		// Not found.
		return null;
	}
}
