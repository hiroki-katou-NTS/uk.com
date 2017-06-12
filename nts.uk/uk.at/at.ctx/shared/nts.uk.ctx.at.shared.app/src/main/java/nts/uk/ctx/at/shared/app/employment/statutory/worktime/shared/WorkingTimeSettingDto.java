/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.employment.statutory.worktime.shared;

import java.util.List;

import lombok.Value;
import nts.uk.ctx.at.shared.dom.employment.statutory.worktime.shared.WorkingTimeSetting;

/**
 * The Class WorkingTimeSettingDto.
 */
@Value
public class WorkingTimeSettingDto {

	/** The daily. */
	private int daily;

	/** The monthly. */
	private List<MonthlyDto> monthly;

	/** The weekly. */
	private int weekly;

	/**
	 * From domain.
	 *
	 * @param domain the domain
	 * @return the working time setting dto
	 */
	public static WorkingTimeSettingDto fromDomain(WorkingTimeSetting domain) {
		return new WorkingTimeSettingDto(domain.getDaily(), MonthlyDto.fromDomain(domain.getMonthly()),
				domain.getWeekly());
	}
}
