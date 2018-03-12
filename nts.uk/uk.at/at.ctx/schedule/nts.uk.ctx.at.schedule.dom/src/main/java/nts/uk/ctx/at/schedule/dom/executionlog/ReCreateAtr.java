/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.dom.executionlog;

/**
 * The Enum ReCreateAtr.
 */
// 再作成区分
public enum ReCreateAtr {

	/** The all case. */
	// 全件
	ALL_CASE(0, "KSC001_4", "全件"),

	/** The only un confirm. */
	// 未確定データのみ
	ONLY_UNCONFIRM(1, "KSC001_5", "未確定データのみ");

	/** The value. */
	public final int value;

	/** The name id. */
	public final String nameId;

	/** The description. */
	public final String description;

	/** The Constant values. */
	private final static ReCreateAtr[] values = ReCreateAtr.values();

	/**
	 * Instantiates a new re create atr.
	 *
	 * @param value the value
	 * @param nameId the name id
	 * @param description the description
	 */
	private ReCreateAtr(int value, String nameId, String description) {
		this.value = value;
		this.nameId = nameId;
		this.description = description;
	}

	/**
	 * Value of.
	 *
	 * @param value the value
	 * @return the re create atr
	 */
	public static ReCreateAtr valueOf(Integer value) {
		// Invalid object.
		if (value == null) {
			return null;
		}

		// Find value.
		for (ReCreateAtr val : ReCreateAtr.values) {
			if (val.value == value) {
				return val;
			}
		}

		// Not found.
		return null;
	}

}
