/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.calculation.holiday.kmk013_splitdomain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.layer.dom.DomainObject;
import nts.uk.shr.com.enumcommon.NotUseAtr;

/**
 * The Class PremiumCalcMethodDetailOfHoliday.
 */
// 休暇の割増計算方法詳細
@NoArgsConstructor
@Getter
public class PremiumCalcMethodDetailOfHoliday extends DomainObject{
	// 休暇分を含める設定
	private IncludeHolidaysPremiumCalcDetailSet includeVacationSet;
	
	// 育児・介護時間を含めて計算する
	private NotUseAtr calculateIncludCareTime;
	
	// 遅刻・早退を控除しない
	private DeductLeaveEarly notDeductLateLeaveEarly;
	
	// インターバル免除時間を含めて計算する
	private NotUseAtr calculateIncludIntervalExemptionTime;

	/**
	 * @param includeVacationSet
	 * @param calculateIncludCareTime
	 * @param notDeductLateLeaveEarly
	 * @param calculateIncludIntervalExemptionTime
	 */
	public PremiumCalcMethodDetailOfHoliday(IncludeHolidaysPremiumCalcDetailSet includeVacationSet,
			int calculateIncludCareTime, DeductLeaveEarly notDeductLateLeaveEarly,
			int calculateIncludIntervalExemptionTime) {
		super();
		this.includeVacationSet = includeVacationSet;
		this.calculateIncludCareTime = NotUseAtr.valueOf(calculateIncludCareTime);
		this.notDeductLateLeaveEarly = notDeductLateLeaveEarly;
		this.calculateIncludIntervalExemptionTime = NotUseAtr.valueOf(calculateIncludIntervalExemptionTime);
	}
}

