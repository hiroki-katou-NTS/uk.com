/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.command.worktime.flexset.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import lombok.Getter;
import lombok.Setter;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.shared.app.command.worktime.common.dto.EmTimeZoneSetDto;
import nts.uk.ctx.at.shared.app.command.worktime.common.dto.OverTimeOfTimeZoneSetDto;
import nts.uk.ctx.at.shared.dom.worktime.common.EmTimeZoneSet;
import nts.uk.ctx.at.shared.dom.worktime.common.FixedWorkTimezoneSetGetMemento;
import nts.uk.ctx.at.shared.dom.worktime.common.OverTimeOfTimeZoneSet;

/**
 * The Class FixedWorkTimezoneSetDto.
 */
@Getter
@Setter
public class FixedWorkTimezoneSetDto implements FixedWorkTimezoneSetGetMemento {
	
	/** The lst working timezone. */
	private List<EmTimeZoneSetDto> lstWorkingTimezone;

	/** The lst OT timezone. */
	private List<OverTimeOfTimeZoneSetDto> lstOTTimezone;
	
	/** The work timezone no. */
	private int workTimezoneNo = 0;
	
	/** The employment time frame no. */
	private int employmentTimeFrameNo = 0;

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.worktime.common.FixedWorkTimezoneSetGetMemento#getLstWorkingTimezone()
	 */
	@Override
	public List<EmTimeZoneSet> getLstWorkingTimezone() {
		if (CollectionUtil.isEmpty(lstWorkingTimezone)) {
			return new ArrayList<>();
		}
		this.lstWorkingTimezone = this.lstWorkingTimezone.stream().sorted((timezone1, timezone2) -> timezone1
				.getTimezone().getStart().compareTo(timezone2.getTimezone().getStart())).collect(Collectors.toList());

		employmentTimeFrameNo = 0;
		this.lstWorkingTimezone.forEach(timezone -> {
			employmentTimeFrameNo++;
			timezone.setEmploymentTimeFrameNo(employmentTimeFrameNo);
		});

		return this.lstWorkingTimezone.stream().map(timezone -> new EmTimeZoneSet(timezone))
				.collect(Collectors.toList());
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.worktime.common.FixedWorkTimezoneSetGetMemento#getLstOTTimezone()
	 */
	@Override
	public List<OverTimeOfTimeZoneSet> getLstOTTimezone() {
		if (CollectionUtil.isEmpty(lstOTTimezone)) {
			return new ArrayList<>();
		}
		
		
		this.lstOTTimezone = this.lstOTTimezone.stream().sorted((timezone1, timezone2) -> timezone1.getTimezone()
				.getStart().compareTo(timezone2.getTimezone().getStart())).collect(Collectors.toList());
		
		workTimezoneNo = 0;
		this.lstOTTimezone.forEach(timezone -> {
			workTimezoneNo++;
			timezone.setWorkTimezoneNo(workTimezoneNo);
		});
		
		return this.lstOTTimezone.stream().map(timezone -> new OverTimeOfTimeZoneSet(timezone))
				.collect(Collectors.toList());
	}
	
}
