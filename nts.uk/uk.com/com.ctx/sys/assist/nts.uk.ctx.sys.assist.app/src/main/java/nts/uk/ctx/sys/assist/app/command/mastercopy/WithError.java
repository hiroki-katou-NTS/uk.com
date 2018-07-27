/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.assist.app.command.mastercopy;

/**
 * The Enum WithError.
 */
// エラー有無
public enum WithError {

	/** The no error. */
	// エラーなし
	NO_ERROR(0, "Enum_WithError_noError", "エラーなし"),

	/** The with error. */
	// エラーあり
	WITH_ERROR(1, "Enum_WithError_withError", "エラーあり");

	/** The value. */
	public final int value;

	/** The name id. */
	public final String nameId;

	/** The description. */
	public final String description;

	/** The Constant values. */
	private final static WithError[] values = WithError.values();

	/**
	 * Instantiates a new with error.
	 *
	 * @param value the value
	 * @param nameId the name id
	 * @param description the description
	 */
	private WithError(int value, String nameId, String description) {
		this.value = value;
		this.nameId = nameId;
		this.description = description;
	}

	/**
	 * Value of.
	 *
	 * @param value the value
	 * @return the with error
	 */
	public static WithError valueOf(Integer value) {
		// Invalid object.
		if (value == null) {
			return null;
		}

		// Find value.
		for (WithError val : WithError.values) {
			if (val.value == value) {
				return val;
			}
		}

		// Not found.
		return null;
	}

}
