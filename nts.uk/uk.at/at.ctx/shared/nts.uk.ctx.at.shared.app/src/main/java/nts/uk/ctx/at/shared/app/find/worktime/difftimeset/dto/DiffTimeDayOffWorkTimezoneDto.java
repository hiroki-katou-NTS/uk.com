/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.find.worktime.difftimeset.dto;

import java.util.List;

import lombok.Getter;
import nts.uk.ctx.at.shared.dom.worktime.difftimeset.DayOffTimezoneSetting;
import nts.uk.ctx.at.shared.dom.worktime.difftimeset.DiffTimeDayOffWorkTimezoneSetMemento;
import nts.uk.ctx.at.shared.dom.worktime.difftimeset.DiffTimeRestTimezone;

/**
 * The Class TimeDiffDayOffWorkTimezone.
 */
@Getter
public class DiffTimeDayOffWorkTimezoneDto implements DiffTimeDayOffWorkTimezoneSetMemento {

	/** The rest timezone. */
	private DiffTimeRestTimezoneDto restTimezone;

	/** The work timezone. */
	private List<DayOffTimezoneSettingDto> workTimezones;

	@Override
	public void setRestTimezone(DiffTimeRestTimezone restTimezone) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setWorkTimezones(List<DayOffTimezoneSetting> workTimezone) {
		// TODO Auto-generated method stub
		
	}
}
