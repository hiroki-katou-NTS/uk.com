package nts.uk.ctx.at.record.dom.workrecord.workfixed;

/**
 * The Enum ConfirmClsStatus.
 */
public enum ConfirmClsStatus {
	
	/** The confirm. */
	//確定
	Confirm(1, "Enum_ConfirmClsStatus_CONFIRM"),

	/** The pending. */
	//未確定
	Pending(0, "Enum_ConfirmClsStatus_PENDING");
	
	/** The value. */
	public int value;
	
	/** The name id. */
	public String nameId;
	
	/** The Constant values. */
	private final static ConfirmClsStatus[] values = ConfirmClsStatus.values();

	/**
	 * Instantiates a new confirm cls status.
	 *
	 * @param value the value
	 * @param nameId the name id
	 */
	private ConfirmClsStatus(int value, String nameId) {
		this.value = value;
		this.nameId = nameId;
	}
	
	/**
	 * Instantiates a new confirm cls status.
	 *
	 * @param value the value
	 */
	private ConfirmClsStatus(int value) {
		this.value = value;
	}

	/**
	 * Value of.
	 *
	 * @param value the value
	 * @return the confirm cls status
	 */
	public static ConfirmClsStatus valueOf(Integer value) {
		// Invalid object.
		if (value == null) {
			return null;
		}

		// Find value.
		for (ConfirmClsStatus val : ConfirmClsStatus.values) {
			if (val.value == value) {
				return val;
			}
		}
		// Not found.
		return null;
	}
			
}
