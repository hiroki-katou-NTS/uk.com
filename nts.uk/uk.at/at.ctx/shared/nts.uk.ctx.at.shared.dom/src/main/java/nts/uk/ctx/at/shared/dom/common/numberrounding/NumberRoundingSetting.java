/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.common.numberrounding;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.val;
import nts.arc.layer.dom.DomainObject;
import nts.uk.ctx.at.shared.dom.common.timerounding.Unit.Direction;

/**
 * The Class NumberRounding.
 */
// 数値丸め
@Getter
public class NumberRoundingSetting extends DomainObject{
	
	/** The unit. */
	// 単位
	private NumberUnit unit;
	
	/** The rounding. */
	// 端数処理
	private NumberRounding rounding;

	/**
	 * Instantiates a new number rounding.
	 *
	 * @param unit the unit
	 * @param rounding the rounding
	 */
	public NumberRoundingSetting(NumberUnit unit, NumberRounding rounding) {
		super();
		this.unit = unit;
		this.rounding = rounding;
	}
	
	
	public double round(double timeAsMinutes) {
		
		//単位が「なし」の場合は丸めない
		if(!unit.asNumber().isPresent())return timeAsMinutes;
		
		BigDecimal calctime = BigDecimal.valueOf(timeAsMinutes);
		
		switch (this.rounding) {
		case TRUNCATION:
			calctime = calctime.setScale(unit.asNumber().get(), BigDecimal.ROUND_DOWN);
			return calctime.intValue();
		case ROUND_UP:
			calctime = calctime.setScale(unit.asNumber().get(), BigDecimal.ROUND_UP);
			return calctime.intValue();
		case DOWN_4_UP_5:
			calctime = calctime.setScale(unit.asNumber().get(), BigDecimal.ROUND_HALF_UP);
			return calctime.intValue();
		default:
			throw new RuntimeException("invalid case: " + this.rounding);
		}
	}
	
	
	
}
