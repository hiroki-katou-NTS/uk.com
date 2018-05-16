/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.env.dom.mailnoticeset.company;

/**
 * The Enum SelfEditUserInfo.
 */
//ユーザー情報本人編集
public enum SelfEditUserInfo {

	/** The can edit. */
	CAN_EDIT(1, "Enum_SelfEditUserInfo_CAN_EDIT","編集可"),
	
	/** The can not edit. */
	CAN_NOT_EDIT(0, "Enum_SelfEditUserInfo_CAN_NOT_EDIT","編集不可");
	

	/** The value. */
	public final int value;

	/** The name id. */
	public final String nameId;

	/** The description. */
	public final String description;

	/** The Constant values. */
	private static final SelfEditUserInfo[] values = SelfEditUserInfo.values();

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
	private SelfEditUserInfo(int value, String nameId, String description) {
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
	public static SelfEditUserInfo valueOf(Integer value) {
		// Invalid object.
		if (value == null) {
			return null;
		}

		// Find value.
		for (SelfEditUserInfo val : SelfEditUserInfo.values) {
			if (val.value == value) {
				return val;
			}
		}

		// Not found.
		return null;
	}
}
