package nts.uk.ctx.at.function.dom.monthlyworkschedule;

import lombok.AllArgsConstructor;

// TODO: Auto-generated Javadoc
/**
 * Instantiates a new prints the setting remarks column.
 *
 * @param value
 *            the value
 */
@AllArgsConstructor
public enum PrintSettingRemarksColumn {

	/** The not print remark. */
	// 印字しない
	NOT_PRINT_REMARK(0),

	/** The print remark. */
	// 印字する
	PRINT_REMARK(1);

	/** The value. */
	public final int value;

	/** The Constant values. */
	private final static PrintSettingRemarksColumn[] values = PrintSettingRemarksColumn.values();

	/**
	 * Value of.
	 *
	 * @param value
	 *            the value
	 * @return the prints the setting remarks column
	 */
	public static PrintSettingRemarksColumn valueOf(Integer value) {
		// Invalid object.
		if (value == null) {
			return null;
		}

		// Find value.
		for (PrintSettingRemarksColumn val : PrintSettingRemarksColumn.values) {
			if (val.value == value) {
				return val;
			}
		}

		// Not found.
		return null;
	}
}
