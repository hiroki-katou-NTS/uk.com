/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.gateway.dom.loginold;

/**
 * The Enum InstallForm.
 */
public enum InstallForm {

	/** The On pre. */
	OnPre(0, "オンプレ", "オンプレ"),

	/** The Cloud. */
	Cloud(1, "クラウド", "クラウド");

	/** The value. */
	public int value;

	/** The name id. */
	public String nameId;

	/** The description. */
	public String description;

	/** The Constant values. */
	private final static InstallForm[] values = InstallForm.values();

	/**
	 * Instantiates a new install form.
	 *
	 * @param value the value
	 * @param nameId the name id
	 * @param description the description
	 */
	private InstallForm(int value, String nameId, String description) {
		this.value = value;
		this.nameId = nameId;
		this.description = description;
	}

	/**
	 * Value of.
	 *
	 * @param value the value
	 * @return the install form
	 */
	public static InstallForm valueOf(Integer value) {
		// Invalid object.
		if (value == null) {
			return null;
		}

		// Find value.
		for (InstallForm val : InstallForm.values) {
			if (val.value == value) {
				return val;
			}
		}
		// Not found.
		return null;
	}
}
