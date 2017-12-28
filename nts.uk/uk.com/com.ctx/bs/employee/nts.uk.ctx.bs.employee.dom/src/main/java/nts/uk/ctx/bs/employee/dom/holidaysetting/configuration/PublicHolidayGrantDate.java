/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.bs.employee.dom.holidaysetting.configuration;

import lombok.Getter;
import lombok.Setter;
import nts.arc.layer.dom.DomainObject;

@Getter
@Setter
// 公休付与日
public class PublicHolidayGrantDate extends DomainObject{
	
	/** The period. */
	// 管理期間
	private PublicHolidayPeriod period;
	
	/**
	 * Instantiates a new public holiday grant date.
	 *
	 * @param memento the memento
	 */
	public PublicHolidayGrantDate() {
		super();
	}
}
