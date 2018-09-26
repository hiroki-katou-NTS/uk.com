/**
 * 
 */
package nts.uk.ctx.at.record.dom.workrecord.goout;

/**
 * The Enum GoingOutReason.
 *
 * @author HoangDD
 * 外出理由
 */
public enum GoingOutReason {
	// 私用
	PRIVATE(0, "Enum_Private"),
	
	// 公用
	PUBLIC(1,"Enum_Public"),
	
	// 有償
	COMPENSATION(2, "Enum_Compensation"),
	
	// 組合
	UNION(3, "Enum_Union");

	/** The value. */
	public final int value;

	/** The name id. */
	public final String nameId;
	

	/** The Constant values. */
	private final static GoingOutReason[] values = GoingOutReason.values();
	
	
	/**
	 * Instantiates a new going out reason.
	 *
	 * @param value the value
	 * @param nameId the name id
	 */
	private GoingOutReason(int value, String nameId) {
		this.value = value;
		this.nameId = nameId;
	}
	
	
	/**
	 * Value of.
	 *
	 * @param value the value
	 * @return the going out reason
	 */
	public static GoingOutReason valueOf(Integer value) {
		// Invalid object.
		if (value == null) {
			return null;
		}

		// Find value.
		for (GoingOutReason val : GoingOutReason.values) {
			if (val.value == value) {
				return val;
			}
		}

		// Not found.
		return null;
	}
}

