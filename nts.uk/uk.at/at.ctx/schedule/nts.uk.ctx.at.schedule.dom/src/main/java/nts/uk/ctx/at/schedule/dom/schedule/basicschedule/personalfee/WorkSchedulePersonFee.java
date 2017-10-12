/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.dom.schedule.basicschedule.personalfee;

import lombok.Getter;
import nts.arc.layer.dom.DomainObject;

/**
 * The Class WorkSchedulePersonFee.
 */
// 勤務予定人件費
@Getter
public class WorkSchedulePersonFee extends DomainObject{
	
	/** The no. */
	// NO
	private ExtraTimeItemNo no;

	/** The personal fee amount. */
	// 人件費金額
	private PersonalFeeAmount personalFeeAmount; 
}
