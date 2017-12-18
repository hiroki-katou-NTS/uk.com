/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.command.worktime.flowset.dto;

import java.util.List;
import java.util.stream.Collectors;

import lombok.Value;
import nts.uk.ctx.at.shared.app.command.worktime.common.dto.FlowWorkRestTimezoneDto;
import nts.uk.ctx.at.shared.dom.worktime.common.FlowWorkRestTimezone;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlOffdayWtzGetMemento;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowWorkHolidayTimeZone;

/**
 * The Class FlOffdayWorkTzDto.
 */
@Value
public class FlOffdayWorkTzDto implements FlOffdayWtzGetMemento {

	/** The rest time zone. */
	private FlowWorkRestTimezoneDto restTimeZone;

	/** The lst work timezone. */
	private List<FlWorkHdTimeZoneDto> lstWorkTimezone;

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.flowset.FlOffdayWtzGetMemento#
	 * getRestTimeZone()
	 */
	@Override
	public FlowWorkRestTimezone getRestTimeZone() {
		return new FlowWorkRestTimezone(this.restTimeZone);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.flowset.FlOffdayWtzGetMemento#
	 * getLstWorkTimezone()
	 */
	@Override
	public List<FlowWorkHolidayTimeZone> getLstWorkTimezone() {
		return this.lstWorkTimezone.stream().map(item -> new FlowWorkHolidayTimeZone(item)).collect(Collectors.toList());
	}

}
