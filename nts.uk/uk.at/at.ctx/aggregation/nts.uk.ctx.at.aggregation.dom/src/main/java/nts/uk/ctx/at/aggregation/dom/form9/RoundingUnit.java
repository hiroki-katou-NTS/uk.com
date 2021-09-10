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
	ONE_DIGIT(1),
	/** 2桁目 **/
	TWO_DIGIT(2),
	/** 3桁目 **/
	THREE_DIGIT(3),
	/** 4桁目 **/
	FOUR_DIGIT(4),
	/** 5桁目 **/
	FIVE_DIGIT(5);
	
	public final int value;
	
	public static RoundingUnit of(int value) {
		return EnumAdaptor.valueOf(value, RoundingUnit.class);
	}
	
}
