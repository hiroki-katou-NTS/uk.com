/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.function.app.find.dailyworkschedule.scrA;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.time.GeneralDate;


@Setter
@Getter
@NoArgsConstructor
public class WorkScheduleOutputConditionDto {
	
	/** The start date. */
	private GeneralDate startDate;
	
	/** The end date. */
	private GeneralDate endDate;
	
	/** The str return. */
	private String strReturn;
	
	/**
	 * Instantiates a new work schedule output condition dto.
	 *
	 * @param startDate the start date
	 * @param endDate the end date
	 * @param strReturn the str return
	 */
	public WorkScheduleOutputConditionDto(GeneralDate startDate, GeneralDate endDate, String strReturn) {
		super();
		this.startDate = startDate;
		this.endDate = endDate;
		this.strReturn = strReturn;
	}

	
}
