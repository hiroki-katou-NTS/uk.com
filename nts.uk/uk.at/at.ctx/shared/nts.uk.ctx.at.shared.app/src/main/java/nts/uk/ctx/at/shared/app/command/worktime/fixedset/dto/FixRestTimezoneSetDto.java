/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.command.worktime.fixedset.dto;

import java.util.List;
import java.util.stream.Collectors;

import lombok.Value;
import nts.uk.ctx.at.shared.app.command.worktime.common.dto.DeductionTimeDto;
import nts.uk.ctx.at.shared.dom.worktime.common.DeductionTime;
import nts.uk.ctx.at.shared.dom.worktime.fixedset.FixRestTimezoneSetGetMemento;

/**
 * The Class FixRestTimezoneSetDto.
 */
@Value
public class FixRestTimezoneSetDto implements FixRestTimezoneSetGetMemento {

	/** The lst timezone. */
	private List<DeductionTimeDto> timezones;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.fixedset.FixRestTimezoneSetGetMemento#
	 * getLstTimezone()
	 */
	@Override
	public List<DeductionTime> getLstTimezone() {
		return this.timezones.stream().map(item -> new DeductionTime(item)).collect(Collectors.toList());
	}

}
