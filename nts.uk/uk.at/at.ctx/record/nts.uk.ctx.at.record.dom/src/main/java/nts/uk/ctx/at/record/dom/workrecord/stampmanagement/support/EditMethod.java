package nts.uk.ctx.at.record.dom.workrecord.stampmanagement.support;

import lombok.AllArgsConstructor;

/**
 * @author nws_namnv2
 * 
 *         編集方法
 */
@AllArgsConstructor
public enum EditMethod {
	
	// 0：前ゼロ
	PREVIOUS_ZERO(0, "前ゼロ"),
	
	// 1：後ゼロ
	AFTER_ZERO(1, "後ゼロ"),
	
	// 2：前スペース
	PREVIOUS_SPACE(2, "前スペース"),
	
	// 3：後スペース
	AFTER_SPACE(3, "後スペース");
	
	public final int value;

	public final String name;

	/** The Constant values. */
	private final static EditMethod[] values = EditMethod.values();

	public static EditMethod valueOf(Integer value) {
		// Invalid object.
		if (value == null) {
			return null;
		}

		// Find value.
		for (EditMethod val : EditMethod.values) {
			if (val.value == value) {
				return val;
			}
		}
		// Not found.
		return null;
	}

}
