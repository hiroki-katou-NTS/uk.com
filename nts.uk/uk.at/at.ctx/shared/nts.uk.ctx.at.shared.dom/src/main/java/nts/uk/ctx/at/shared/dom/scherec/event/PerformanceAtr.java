/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.scherec.event;

/**
 * The Enum PerformanceClassification.
 */
// 任意項目利用区分
public enum PerformanceAtr {

	/** The monthly performance. */
	// 月別実績
	MONTHLY_PERFORMANCE(0, "Enum_PerformanceAtr_MONTHLY_PERFORMANCE", "月別実績"),

	/** The daily performance. */
	// 日別実績
	DAILY_PERFORMANCE(1, "Enum_PerformanceAtr_DAILY_PERFORMANCE", "日別実績");

	/** The value. */
	public int value;

	/** The name id. */
	public String nameId;

	/** The description. */
	public String description;

	/** The Constant values. */
	private final static PerformanceAtr[] values = PerformanceAtr.values();

	/**
	 * Instantiates a new performance atr.
	 *
	 * @param value the value
	 * @param nameId the name id
	 * @param description the description
	 */
	private PerformanceAtr(int value, String nameId, String description) {
		this.value = value;
		this.nameId = nameId;
		this.description = description;
	}

	/**
	 * Value of.
	 *
	 * @param value the value
	 * @return the emp condition atr
	 */
	public static PerformanceAtr valueOf(Integer value) {
		// Invalid object.
		if (value == null) {
			return null;
		}

		// Find value.
		for (PerformanceAtr val : PerformanceAtr.values) {
			if (val.value == value) {
				return val;
			}
		}

		// Not found.
		return null;
	}
	
	/**
	 * 月別実績か判定する
	 * @return 月別実績ならtrue
	 */
	public boolean isMonthlyPerformance() {
		return MONTHLY_PERFORMANCE.equals(this);
	}
	
	/**
	 * 日別実績か判定する
	 * @return 日別実績ならtrue
	 */
	public boolean isDailyPerformance() {
		return DAILY_PERFORMANCE.equals(this);
	}	
	
	
}
