/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.find.worktime.difftimeset.dto;

import java.util.List;
import java.util.stream.Collectors;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.app.find.worktime.common.dto.EmTimeZoneSetDto;
import nts.uk.ctx.at.shared.dom.worktime.common.EmTimeZoneSet;
import nts.uk.ctx.at.shared.dom.worktime.difftimeset.DiffTimeOTTimezoneSet;
import nts.uk.ctx.at.shared.dom.worktime.difftimeset.DiffTimezoneSettingSetMemento;

/**
 * The Class DiffTimezoneSetting.
 */
@Getter
@Setter
public class DiffTimezoneSettingDto implements DiffTimezoneSettingSetMemento {

	/** The employment timezone. */
	private List<EmTimeZoneSetDto> employmentTimezones;

	/** The OT timezone. */
	private List<DiffTimeOTTimezoneSetDto> oTTimezones;

	@Override
	public void setEmploymentTimezones(List<EmTimeZoneSet> employmentTimezones) {
		employmentTimezones.stream().map(item -> {
			EmTimeZoneSetDto dto = new EmTimeZoneSetDto();
			item.saveToMemento(dto);
			return dto;
		}).collect(Collectors.toList());
	}

	@Override
	public void setOTTimezones(List<DiffTimeOTTimezoneSet> oTTimezones) {
		oTTimezones.stream().map(item -> {
			DiffTimeOTTimezoneSetDto dto = new DiffTimeOTTimezoneSetDto();
			item.saveToMemento(dto);
			return dto;
		}).collect(Collectors.toList());
	}

}
