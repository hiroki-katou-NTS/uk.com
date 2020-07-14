/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.command.worktime.predset.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.shared.dom.worktime.predset.PrescribedTimezoneSettingGetMemento;
import nts.uk.ctx.at.shared.dom.worktime.predset.TimezoneUse;
import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 * The Class PrescribedTimezoneSettingDto.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PrescribedTimezoneSettingDto implements PrescribedTimezoneSettingGetMemento{
	
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
	 * PrescribedTimezoneSettingGetMemento#getMorningEndTime()
	 */
	@Override
	public TimeWithDayAttr getMorningEndTime() {
		return new TimeWithDayAttr(this.morningEndTime);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.predset.
	 * PrescribedTimezoneSettingGetMemento#getAfternoonStartTime()
	 */
	@Override
	public TimeWithDayAttr getAfternoonStartTime() {
		return new TimeWithDayAttr(this.afternoonStartTime);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.predset.
	 * PrescribedTimezoneSettingGetMemento#getLstTimezone()
	 */
	@Override
	public List<TimezoneUse> getLstTimezone() {
		if(CollectionUtil.isEmpty(this.lstTimezone)){
			return new ArrayList<>();
		}
		return this.lstTimezone.stream().map(dto-> new TimezoneUse(dto)).collect(Collectors.toList());
	}

}
