/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.find.worktime.predset.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.shared.dom.worktime.predset.PrescribedTimezoneSettingSetMemento;
import nts.uk.ctx.at.shared.dom.worktime.predset.TimezoneUse;
import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 * The Class PrescribedTimezoneSettingDto.
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PrescribedTimezoneSettingDto implements PrescribedTimezoneSettingSetMemento{
	
	/** The morning end time. */
	public Integer morningEndTime;
	
	/** The afternoon start time. */
	public Integer afternoonStartTime;
	
	/** The lst timezone. */
	public List<TimezoneDto> lstTimezone;

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.predset.
	 * PrescribedTimezoneSettingSetMemento#setMorningEndTime(nts.uk.shr.com.time
	 * .TimeWithDayAttr)
	 */
	@Override
	public void setMorningEndTime(TimeWithDayAttr morningEndTime) {
		this.morningEndTime = morningEndTime.valueAsMinutes();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.predset.
	 * PrescribedTimezoneSettingSetMemento#setAfternoonStartTime(nts.uk.shr.com.
	 * time.TimeWithDayAttr)
	 */
	@Override
	public void setAfternoonStartTime(TimeWithDayAttr afternoonStartTime) {
		this.afternoonStartTime = afternoonStartTime.valueAsMinutes();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.predset.
	 * PrescribedTimezoneSettingSetMemento#setLstTimezone(java.util.List)
	 */
	@Override
	public void setLstTimezone(List<TimezoneUse> lstTimezone) {
		if (CollectionUtil.isEmpty(lstTimezone)) {
			this.lstTimezone = new ArrayList<>();
		} else {
			this.lstTimezone = lstTimezone.stream().map(domain->{
				TimezoneDto dto = new TimezoneDto();
				domain.saveToMemento(dto);
				return dto;
			}).collect(Collectors.toList());
		}
	}

}
