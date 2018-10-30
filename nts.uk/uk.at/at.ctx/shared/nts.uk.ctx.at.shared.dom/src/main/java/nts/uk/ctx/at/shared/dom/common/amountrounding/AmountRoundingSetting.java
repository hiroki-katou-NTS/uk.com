/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.common.amountrounding;

import java.math.BigDecimal;

import lombok.Getter;
import nts.arc.layer.dom.DomainObject;

/**
 * The Class AmountRounding.
 */
// 金額丸め
@Getter
public class AmountRoundingSetting extends DomainObject{
	
	/** The unit. */
	// 単位
	private AmountUnit unit;
	
	/** The rounding. */
	// 端数処理
	private AmountRounding rounding;

	/**
	 * Instantiates a new amount rounding.
	 *
	 * @param unit the unit
	 * @param rounding the rounding
	 */
	public AmountRoundingSetting(AmountUnit unit, AmountRounding rounding) {
		super();
		this.unit = unit;
		this.rounding = rounding;
	}
	
	
	public BigDecimal round(BigDecimal timeAsMinutes) {
		
		BigDecimal calctime = timeAsMinutes;
		
		switch (this.rounding) {
		case TRUNCATION:
			calctime = calctime.setScale(unit.asAmount(), BigDecimal.ROUND_DOWN);
			return calctime;
		case ROUND_UP:
			calctime = calctime.setScale(unit.asAmount(), BigDecimal.ROUND_UP);
			return calctime;
		case DOWN_1_UP_2:
			timeAsMinutes = timeAsMinutes.add(BigDecimal.valueOf((8 * unit.asRoundingSet())));
			calctime = timeAsMinutes;
			calctime = calctime.setScale(unit.asAmount(), BigDecimal.ROUND_DOWN);
			return calctime;
		case DOWN_2_UP_3:
			timeAsMinutes = timeAsMinutes.add(BigDecimal.valueOf((7 * unit.asRoundingSet())));
			calctime = timeAsMinutes;
			calctime = calctime.setScale(unit.asAmount(), BigDecimal.ROUND_DOWN);
			return calctime;
		case DOWN_3_UP_4:
			timeAsMinutes = timeAsMinutes.add(BigDecimal.valueOf((6 * unit.asRoundingSet())));
			calctime = timeAsMinutes;
			calctime = calctime.setScale(unit.asAmount(), BigDecimal.ROUND_DOWN);
			return calctime;
		case DOWN_4_UP_5:
			timeAsMinutes = timeAsMinutes.add(BigDecimal.valueOf((5 * unit.asRoundingSet())));
			calctime = timeAsMinutes;
			calctime = calctime.setScale(unit.asAmount(), BigDecimal.ROUND_DOWN);
			return calctime;		
		case DOWN_5_UP_6:
			timeAsMinutes = timeAsMinutes.add(BigDecimal.valueOf((4 * unit.asRoundingSet())));
			calctime = timeAsMinutes;
			calctime = calctime.setScale(unit.asAmount(), BigDecimal.ROUND_DOWN);
			return calctime;
		case DOWN_6_UP_7:
			timeAsMinutes = timeAsMinutes.add(BigDecimal.valueOf((3 * unit.asRoundingSet())));
			calctime = timeAsMinutes;
			calctime = calctime.setScale(unit.asAmount(), BigDecimal.ROUND_DOWN);
			return calctime;
		case DOWN_7_UP_8:
			timeAsMinutes = timeAsMinutes.add(BigDecimal.valueOf((2 * unit.asRoundingSet())));
			calctime = timeAsMinutes;
			calctime = calctime.setScale(unit.asAmount(), BigDecimal.ROUND_DOWN);
			return calctime;
		case DOWN_8_UP_9:
			timeAsMinutes = timeAsMinutes.add(BigDecimal.valueOf((1 * unit.asRoundingSet())));
			calctime = timeAsMinutes;
			calctime = calctime.setScale(unit.asAmount(), BigDecimal.ROUND_DOWN);
			return calctime;	
		default:
			throw new RuntimeException("invalid case: " + this.rounding);
		}
	}
}
