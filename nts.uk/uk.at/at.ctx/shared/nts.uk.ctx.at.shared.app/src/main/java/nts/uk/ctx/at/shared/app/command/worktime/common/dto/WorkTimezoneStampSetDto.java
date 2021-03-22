/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.command.worktime.common.dto;

import java.util.List;
import java.util.stream.Collectors;

import lombok.Value;
import nts.uk.ctx.at.shared.dom.worktime.common.PrioritySetting;
import nts.uk.ctx.at.shared.dom.worktime.common.RoundingSet;
import nts.uk.ctx.at.shared.dom.worktime.common.RoundingTime;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimezoneStampSetGetMemento;

/**
 * The Class WorkTimezoneStampSetDto.
 */
@Value
public class WorkTimezoneStampSetDto implements WorkTimezoneStampSetGetMemento {

	/** The rounding set. */
	private RoundingTimeDto roundingTime;

	/** The priority set. */
	private List<PrioritySettingDto> prioritySets;
	
	
	/*
	 * (非 Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.worktime.common.WorkTimezoneStampSetGetMemento#getRoundingTime()
	 */
	@Override
	public RoundingTime getRoundingTime() {
		return new RoundingTime(this.roundingTime);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.common.WorkTimezoneStampSetGetMemento#
	 * getPrioritySet()
	 */
	@Override
	public List<PrioritySetting> getPrioritySet() {
		return this.prioritySets.stream().map(item -> new PrioritySetting(item)).collect(Collectors.toList());
	}


}
