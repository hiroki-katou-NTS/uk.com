/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.app.command.shift.pattern.daily.dto;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.schedule.dom.shift.workcycle.WorkCycleInfo;
import nts.uk.ctx.at.shared.dom.WorkInformation;


/**
 * The Class DailyPatternValDto.
 */
@Getter
@Setter
public class DailyPatternValDto {

	/** The disp order. */
	private Integer dispOrder;

	/** The work type set cd. */
	private String workTypeSetCd;

	/** The working hours cd. */
	private String workingHoursCd;

	/** The days. */
	private Integer days;

	/**
	 * ToDomain
	 * @return
	 */
	public WorkCycleInfo toDomain(){
		WorkCycleInfo result = WorkCycleInfo.create(days,new WorkInformation(getWorkTypeSetCd(), getWorkingHoursCd()));
		return result;
	}

}
