/**
 * 11:20:07 AM Nov 9, 2017
 */
package nts.uk.ctx.at.record.dom.workrecord.erroralarm.enums;

import lombok.AllArgsConstructor;

/**
 * @author hungnm
 *
 */
//範囲との比較演算の種別
@AllArgsConstructor
public enum RangeCompareType {

	/** 範囲の間（境界値を含まない）（＜＞） */
	BETWEEN_RANGE_OPEN(6, "Enum_RangeCompareType_BetweenRangeOpen"),
	/** 範囲の間（境界値を含む）（≦≧） */
	BETWEEN_RANGE_CLOSED(7, "Enum_RangeCompareType_BetweenRangeClosed"),
	/** 範囲の外（境界値を含まない）（＞＜） */
	OUTSIDE_RANGE_OPEN(8, "Enum_RangeCompareType_OutsideRangeOpen"),
	/** 範囲の外（境界値を含む）（≧≦） */
	OUTSIDE_RANGE_CLOSED(9, "Enum_RangeCompareType_OutsideRangeClosed");

	public final int value;

	public final String nameId;
	
}
