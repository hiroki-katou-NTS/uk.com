/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.function.dom.dailyworkschedule;

/**
 * The Enum RemarkInputContent.
 */
// @author HoangDD
// 備考入力内容
public enum RemarkInputContent {
	
	/** The remark no1. */
	// 備考No.1
	REMARK_NO1(0, "Enum_RemarkNo1"),

	/** The remark no2. */
	// 備考No.2
	REMARK_NO2(1, "Enum_RemarkNo2"),
	
	/** The remark no3. */
	// 備考No.3
	REMARK_NO3(2, "Enum_RemarkNo3"),
	
	/** The remark no4. */
	// 備考No.4
	REMARK_NO4(3, "Enum_RemarkNo4"),
	
	/** The remark no5. */
	// 備考No.5
	REMARK_NO5(4, "Enum_RemarkNo5");

	/** The value. */
	public final int value;

	/** The name id. */
	public final String nameId;

	/** The Constant values. */
	private final static RemarkInputContent[] values = RemarkInputContent.values();

	
	/**
	 * Instantiates a new remark input content.
	 *
	 * @param value the value
	 * @param nameId the name id
	 */
	private RemarkInputContent(int value, String nameId) {
		this.value = value;
		this.nameId = nameId;
	}
	
	/**
	 * Value of.
	 *
	 * @param value the value
	 * @return the remark input content
	 */
	public static RemarkInputContent valueOf(Integer value) {
		// Invalid object.
		if (value == null) {
			return null;
		}

		// Find value.
		for (RemarkInputContent val : RemarkInputContent.values) {
			if (val.value == value) {
				return val;
			}
		}

		// Not found.
		return null;
	}
}

