/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.report.infra.repository.salarydetail.memento;

/**
 * The Enum OutputSetting.
 */
public enum OutputSetting {

	/** The not show. */
	NOT_SHOW(0, false),

	/** The show. */
	SHOW(1, true);

	/** The value. */
	public final Integer value;

	/** The bol. */
	public final Boolean bol;

	/**
	 * Instantiates a new output setting.
	 *
	 * @param value
	 *            the value
	 */
	private OutputSetting(Integer value, Boolean bol) {
		this.value = value;
		this.bol = bol;
	}

	/**
	 * Value of.
	 *
	 * @param value
	 *            the value
	 * @return the output setting
	 */
	public static OutputSetting valueOf(Integer value) {
		// Invalid object.
		if (value == null) {
			return null;
		}

		// Find value.
		for (OutputSetting val : OutputSetting.values()) {
			if (val.value == value) {
				return val;
			}
		}

		// Not found.
		return null;
	}
}
