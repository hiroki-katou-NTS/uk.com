/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.calculation.holiday;

import lombok.Getter;
import nts.arc.layer.dom.DomainObject;
import nts.uk.shr.com.enumcommon.NotUseAtr;

/**
 * The Class LeaveSetAdded.
 */
// 加算する休暇設定
@Getter
public class LeaveSetAdded extends DomainObject{
	
	/** The annual holiday. */
	// 年休
	private NotUseAtr annualHoliday;
	
	/** The yearly reserved. */
	// 積立年休
	private NotUseAtr yearlyReserved;
	
	/** The special holiday. */
	// 特別休暇
	private NotUseAtr specialHoliday;

	/**
	 * Instantiates a new leave set added.
	 *
	 * @param annualHoliday the annual holiday
	 * @param yearlyReserved the yearly reserved
	 * @param specialHoliday the special holiday
	 */
	public LeaveSetAdded(NotUseAtr annualHoliday, NotUseAtr yearlyReserved, NotUseAtr specialHoliday) {
		super();
		this.annualHoliday = annualHoliday;
		this.yearlyReserved = yearlyReserved;
		this.specialHoliday = specialHoliday;
	}
}

