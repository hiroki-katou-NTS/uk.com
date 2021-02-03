/**
 * 11:19:54 AM Nov 9, 2017
 */
package nts.uk.ctx.at.record.dom.workrecord.erroralarm.enums;

import lombok.AllArgsConstructor;

/**
 * @author hungnm
 *
 */
//単一値との比較演算の種別
@AllArgsConstructor
public enum SingleValueCompareType {

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
	GREATER_THAN(5, "Enum_SingleValueCompareType_GreaterThan");
	

	public final int value;

	public final String nameId;
	
}
