package nts.uk.ctx.at.function.dom.alarm.alarmlist.aggregationprocess.daily.dailyaggregationprocess;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum CompareType {


	/* 等しくない（≠） */
	NOT_EQUAL(0, "Enum_SingleValueCompareType_NotEqual"),
	/* 等しい（＝） */
	EQUAL(1, "Enum_SingleValueCompareType_Equal"),
	/* 以下（≦） */
	LESS_OR_EQUAL(2, "Enum_SingleValueCompareType_LessOrEqual"),
	/* 以上（≧） */
	GREATER_OR_EQUAL(3, "Enum_SingleValueCompareType_GreaterOrEqual"),
	/* より小さい（＜） */
	LESS_THAN(4, "Enum_SingleValueCompareType_LessThan"),
	/* より大きい（＞） */
	GREATER_THAN(5, "Enum_SingleValueCompareType_GreaterThan"),

	
	/* 範囲の間（境界値を含まない）（＜＞） */
	BETWEEN_RANGE_OPEN(6, "Enum_RangeCompareType_BetweenRangeOpen"),
	/* 範囲の間（境界値を含む）（≦≧） */
	BETWEEN_RANGE_CLOSED(7, "Enum_RangeCompareType_BetweenRangeClosed"),
	/* 範囲の外（境界値を含まない）（＞＜） */
	OUTSIDE_RANGE_OPEN(8, "Enum_RangeCompareType_OutsideRangeOpen"),
	/* 範囲の外（境界値を含む）（≧≦） */
	OUTSIDE_RANGE_CLOSED(9, "Enum_RangeCompareType_OutsideRangeClosed");

	public final int value;

	public final String nameId;
}
