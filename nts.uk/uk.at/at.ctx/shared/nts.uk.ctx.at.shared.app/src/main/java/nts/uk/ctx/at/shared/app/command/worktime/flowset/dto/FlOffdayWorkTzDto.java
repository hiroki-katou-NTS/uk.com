/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.command.worktime.flowset.dto;

import java.util.List;
import java.util.stream.Collectors;

import nts.uk.ctx.at.shared.app.find.worktime.common.dto.FlowWorkRestTimezoneDto;
import nts.uk.ctx.at.shared.dom.worktime.common.FlowWorkRestTimezone;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlOffdayWtzGetMemento;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlWorkHdTimeZone;

/**
 * The Class FlowOffdayWorkTimezoneDto.
 */
public class FlOffdayWorkTzDto implements FlOffdayWtzGetMemento {

	/** The rest time zone. */
	private FlowWorkRestTimezoneDto restTimeZone;

	/** The lst work timezone. */
	private List<FlWorkHdTimeZoneDto> lstWorkTimezone;

	@Override
	public FlowWorkRestTimezone getRestTimeZone() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<FlWorkHdTimeZone> getLstWorkTimezone() {
		return this.lstWorkTimezone.stream().map(item -> new FlWorkHdTimeZone(item)).collect(Collectors.toList());
	}

}
