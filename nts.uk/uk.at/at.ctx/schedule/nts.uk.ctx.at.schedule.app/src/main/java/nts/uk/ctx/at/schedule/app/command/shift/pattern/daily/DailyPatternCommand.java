/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.app.command.shift.pattern.daily;

import java.util.List;
import java.util.stream.Collectors;

import lombok.Getter;
import lombok.Setter;
import lombok.val;
import nts.uk.ctx.at.schedule.app.command.shift.pattern.daily.dto.DailyPatternValDto;
import nts.uk.ctx.at.schedule.dom.shift.workcycle.WorkCycle;
import nts.uk.ctx.at.schedule.dom.shift.workcycle.WorkCycleInfo;
import nts.uk.ctx.at.shared.dom.WorkInformation;

/**
 * The Class PatternCalendarCommand.
 */
@Setter
@Getter
public class DailyPatternCommand {
	
	/** The is editting. */
	private Boolean isEditting;

	/** The pattern code. */
	private String patternCode;

	/** The pattern name. */
	private String patternName;

	/** The list daily pattern val. */
	private List<DailyPatternValDto> dailyPatternVals;

	/**
	 * To domain.
	 *
	 * @param companyId
	 *            the company id
	 * @return the daily pattern
	 */
	public WorkCycle toDomain(String companyId) {
		val listInfor = dailyPatternVals.stream().map(i->
				WorkCycleInfo.create(i.getDays(),new WorkInformation(i.getWorkTypeSetCd(),i.getWorkingHoursCd())))
				.collect(Collectors.toList());
		return WorkCycle.create(companyId,patternCode,patternName,listInfor);
	}



}
