/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.calculation.holiday.kmk013_splitdomain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.layer.dom.DomainObject;

/**
 * The Class HolidayCalcMethodSet.
 */

@NoArgsConstructor
@Getter
// 休暇の計算方法の設定
public class HolidayCalcMethodSet extends DomainObject{
	
	/** The premium calc method detail of holiday. */
	// 休暇の割増計算方法
	private PremiumHolidayCalcMethod premiumCalcMethodOfHoliday;
	
	/** The work time calc method detail of holiday. */
	// 休暇の就業時間計算方法
	private WorkTimeHolidayCalcMethod workTimeCalcMethodOfHoliday;

	/**
	 * @param premiumCalcMethodOfHoliday
	 * @param workTimeCalcMethodOfHoliday
	 */
	public HolidayCalcMethodSet(PremiumHolidayCalcMethod premiumCalcMethodOfHoliday,
			WorkTimeHolidayCalcMethod workTimeCalcMethodOfHoliday) {
		super();
		this.premiumCalcMethodOfHoliday = premiumCalcMethodOfHoliday;
		this.workTimeCalcMethodOfHoliday = workTimeCalcMethodOfHoliday;
	}
}

