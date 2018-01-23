package nts.uk.ctx.at.shared.dom.calculation.holiday;

import lombok.AllArgsConstructor;
/**
 * 
 * @author phongtq
 *通常、変形の所定超過時
 */
@AllArgsConstructor
public enum DeformatExcValueAtr {

	/** 就業時間として計算する*/
	CALC_AS_WORKING_HOURS(0),
	/** 残業時間として計算する*/
	CALC_AS_OVERTIME_HOURS(1);

	public final int value;
}
