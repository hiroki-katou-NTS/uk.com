package nts.uk.ctx.at.aggregation.dom.form9;

import lombok.RequiredArgsConstructor;
import nts.arc.enums.EnumAdaptor;

/**
 * 小数点以下
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.予実集計.様式９.小数点以下
 * @author lan_lt
 *
 */
@RequiredArgsConstructor
public enum RoundingUnit {
	/** 1桁目 **/
	ONE_DIGIT(0, "Enum_RoundingUnit_ONE_DIGIT"),
	/** 2桁目 **/
	TWO_DIGIT(1, "Enum_RoundingUnit_TWO_DIGIT"),
	/** 3桁目 **/
	THREE_DIGIT(2, "Enum_RoundingUnit_THREE_DIGIT"),
	/** 4桁目 **/
	FOUR_DIGIT(3, "Enum_RoundingUnit_FOUR_DIGIT"),
	/** 5桁目 **/
	FIVE_DIGIT(4, "Enum_RoundingUnit_FIVE_DIGIT");
	
	/** The value. */
	public final int value;
	
	/** The name id. */
	public final String nameId;
	
	public static RoundingUnit of(int value) {
		return EnumAdaptor.valueOf(value, RoundingUnit.class);
	}
	
}
