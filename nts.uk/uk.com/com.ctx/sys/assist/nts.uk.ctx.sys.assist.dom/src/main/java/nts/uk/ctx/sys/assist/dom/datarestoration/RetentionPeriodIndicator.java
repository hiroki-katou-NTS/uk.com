package nts.uk.ctx.sys.assist.dom.datarestoration;

import lombok.AllArgsConstructor;

/**
 * 保存期間区分
 *
 */
@AllArgsConstructor
public enum RetentionPeriodIndicator {

	/**
	 * なし
	 */
	NONE           (0, "Enum_RetentionPeriodIndicator_NONE"),

	/**
	 * 年
	 */
	YEAR           (3, "Enum_RetentionPeriodIndicator_YEAR"),

	/**
	 * 年月
	 */
	YEAR_MONTH     (2, "Enum_RetentionPeriodIndicator_YEAR_MONTH"),

	/**
	 * 年月日
	 */
	YEAR_MONTH_DAY (1, "Enum_RetentionPeriodIndicator_YEAR_MONTH_DAY");

	/** The value. */
	public final int value;

	/** The name id. */
	public final String nameId;
}
