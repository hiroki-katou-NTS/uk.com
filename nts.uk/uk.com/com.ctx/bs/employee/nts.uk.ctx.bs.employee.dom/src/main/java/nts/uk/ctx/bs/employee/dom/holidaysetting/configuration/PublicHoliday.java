/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.bs.employee.dom.holidaysetting.configuration;

import lombok.Getter;
import lombok.Setter;
import nts.arc.layer.dom.DomainObject;
import nts.arc.time.GeneralDate;

@Getter
@Setter
// 公休起算日
public class PublicHoliday extends DomainObject{
	
	/** The date. */
	// 年月日
	private GeneralDate date;
	
	/** The day month. */
	// 月日
	private int dayMonth;
	
	/** The determine start date. */
	// 起算日指定方法
	private DayOfPublicHoliday determineStartDate;
	
	public PublicHoliday() {
		super();
	}
}
