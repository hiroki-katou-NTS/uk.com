/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.configuration;

import lombok.Getter;
import lombok.Setter;
import nts.arc.layer.dom.DomainObject;

/**
 * The Class PublicHolidayGrantDate.
 */
// 公休付与日
@Getter
@Setter
public class PublicHolidayGrantDate extends DomainObject
					implements PublicHolidayManagementStartDate
{
	
	/** The period. */
	// 管理期間
	private PublicHolidayPeriod period;
	
	/**
	 * Instantiates a new public holiday grant date.
	 *
	 * @param period the period
	 */
	public PublicHolidayGrantDate(PublicHolidayPeriod period) {
		this.period = period;
	}
}
