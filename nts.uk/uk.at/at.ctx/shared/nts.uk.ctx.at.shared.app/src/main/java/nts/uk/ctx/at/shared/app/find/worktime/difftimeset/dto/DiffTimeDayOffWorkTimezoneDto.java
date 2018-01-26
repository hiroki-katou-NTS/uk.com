/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.find.worktime.difftimeset.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.worktime.difftimeset.DayOffTimezoneSetting;
import nts.uk.ctx.at.shared.dom.worktime.difftimeset.DiffTimeDayOffWorkTimezoneSetMemento;
import nts.uk.ctx.at.shared.dom.worktime.difftimeset.DiffTimeRestTimezone;

/**
 * The Class TimeDiffDayOffWorkTimezone.
 */
@Getter
@Setter
public class DiffTimeDayOffWorkTimezoneDto implements DiffTimeDayOffWorkTimezoneSetMemento {

	/** The rest timezone. */
	private DiffTimeRestTimezoneDto restTimezone;

	/** The work timezone. */
	private List<DayOffTimezoneSettingDto> workTimezones;

	@Override
	public void setRestTimezone(DiffTimeRestTimezone restTimezone) {
		this.restTimezone = new DiffTimeRestTimezoneDto();
		restTimezone.saveToMemento(this.restTimezone);
	}

	@Override
	public void setWorkTimezones(List<DayOffTimezoneSetting> workTimezone) {
		this.workTimezones = new ArrayList<DayOffTimezoneSettingDto>();
		this.workTimezones.addAll(workTimezone.stream().map(item -> {
			DayOffTimezoneSettingDto dto = new DayOffTimezoneSettingDto();
			item.saveToMemento(dto);
			return dto;
		}).collect(Collectors.toList()));
	}
}
