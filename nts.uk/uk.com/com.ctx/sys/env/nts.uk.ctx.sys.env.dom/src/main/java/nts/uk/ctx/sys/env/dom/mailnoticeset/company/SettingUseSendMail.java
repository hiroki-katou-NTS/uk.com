/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.env.dom.mailnoticeset.company;

/**
 * The Enum SettingUseSendMail.
 */
//メール送信利用設定
public enum SettingUseSendMail {

	/** The use. */
	USE(1, "Enum_SettingUseSendMail_USE","利用する"),
	
	/** The not use. */
	NOT_USE(0, "Enum_SettingUseSendMail_NOT_USE","利用しない"),
	
	/** The personal selectable. */
	PERSONAL_SELECTABLE(2, "Enum_SettingUseSendMail_PERSONAL_SELECTABLE","個人選択可");

	/** The value. */
	public final int value;

	/** The name id. */
	public final String nameId;

	/** The description. */
	public final String description;

	/** The Constant values. */
	private final static SettingUseSendMail[] values = SettingUseSendMail.values();

	/**
	 * Instantiates a new abolish atr.
	 *
	 * @param value
	 *            the value
	 * @param nameId
	 *            the name id
	 * @param description
	 *            the description
	 */
	private SettingUseSendMail(int value, String nameId, String description) {
		this.value = value;
		this.nameId = nameId;
		this.description = description;
	}

	/**
	 * Value of.
	 *
	 * @param value
	 *            the value
	 * @return the abolish atr
	 */
	public static SettingUseSendMail valueOf(Integer value) {
		// Invalid object.
		if (value == null) {
			return null;
		}

		// Find value.
		for (SettingUseSendMail val : SettingUseSendMail.values) {
			if (val.value == value) {
				return val;
			}
		}

		// Not found.
		return null;
	}
}
