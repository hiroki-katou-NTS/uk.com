/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.common;


/**
 * The Enum RestTimeOfficeWorkCalcMethod.
 */
//休憩時間中に退勤した場合の計算方法
public enum RestTimeOfficeWorkCalcMethod {

	/** The approp all. */
	// 全て計上する
	APPROP_ALL(0, "Enum_RestTimeOfficeWorkCalcMethod_allEmployee", "全て計上する"),

	/** The not approp all. */
	// 全て計上しない
	NOT_APPROP_ALL(1, "Enum_RestTimeOfficeWorkCalcMethod_departmentAndChild", "全て計上しない"),
	
	/** The office work approp all. */
	//退勤時刻まで計上する
	OFFICE_WORK_APPROP_ALL(2, "Enum_RestTimeOfficeWorkCalcMethod_departmentAndChild", "退勤時刻まで計上する");
	
	/** The value. */
	public final int value;

	/** The name id. */
	public final String nameId;

	/** The description. */
	public final String description;

	/** The Constant values. */
	private final static RestTimeOfficeWorkCalcMethod[] values = RestTimeOfficeWorkCalcMethod.values();

	/**
	 * Instantiates a new rest time office work calc method.
	 *
	 * @param value the value
	 * @param nameId the name id
	 * @param description the description
	 */
	private RestTimeOfficeWorkCalcMethod(int value, String nameId, String description) {
		this.value = value;
		this.nameId = nameId;
		this.description = description;
	}

	/**
	 * Value of.
	 *
	 * @param value the value
	 * @return the rest time office work calc method
	 */
	public static RestTimeOfficeWorkCalcMethod valueOf(Integer value) {
		// Invalid object.
		if (value == null) {
			return null;
		}

		// Find value.
		for (RestTimeOfficeWorkCalcMethod val : RestTimeOfficeWorkCalcMethod.values) {
			if (val.value == value) {
				return val;
			}
		}

		// Not found.
		return null;
	}

}
