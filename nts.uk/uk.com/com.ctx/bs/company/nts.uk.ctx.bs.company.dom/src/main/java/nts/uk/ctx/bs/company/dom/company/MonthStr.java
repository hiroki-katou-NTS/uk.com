package nts.uk.ctx.bs.company.dom.company;

import lombok.AllArgsConstructor;

/**
 * 期首月
 * @author yennth
 *
 */
@AllArgsConstructor
public enum MonthStr {
	one(1, "1月", "Enum_StartingMonthType_JANUARY"),
	two(2, "2月", "Enum_StartingMonthType_FEBRUARY"),
	three(3, "3月", "Enum_StartingMonthType_MARCH"),
	four(4, "4月", "Enum_StartingMonthType_APRIL"),
	five(5, "5月", "Enum_StartingMonthType_MAY"),
	six(6, "6月", "Enum_StartingMonthType_JUNE"),
	seven(7, "7月", "Enum_StartingMonthType_JULY"),
	eight(8, "8月", "Enum_StartingMonthType_AUGUST"),
	nine(9, "9月", "Enum_StartingMonthType_SEPTEMBER"),
	ten(10, "10月", "Enum_StartingMonthType_OCTOBER"),
	eleven(11, "11月", "Enum_StartingMonthType_NOVEMBER"),
	twelve(12, "12月", "Enum_StartingMonthType_DECEMBER");
	public final int value;
	public String month;
	public String nameId;
}
