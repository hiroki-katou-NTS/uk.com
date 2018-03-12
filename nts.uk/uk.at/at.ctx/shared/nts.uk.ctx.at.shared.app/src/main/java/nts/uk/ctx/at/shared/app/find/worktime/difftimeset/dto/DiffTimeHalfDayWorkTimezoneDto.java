/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.find.worktime.difftimeset.dto;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.worktime.common.AmPmAtr;
import nts.uk.ctx.at.shared.dom.worktime.difftimeset.DiffTimeHalfDaySetMemento;
import nts.uk.ctx.at.shared.dom.worktime.difftimeset.DiffTimeRestTimezone;
import nts.uk.ctx.at.shared.dom.worktime.difftimeset.DiffTimezoneSetting;

/**
 * The Class TimeDiffHalfDayWorkTimezone.
 */
@Getter
@Setter
public class DiffTimeHalfDayWorkTimezoneDto implements DiffTimeHalfDaySetMemento {

	/** The rest timezone. */
	private DiffTimeRestTimezoneDto restTimezone;

	/** The work timezone. */
	private DiffTimezoneSettingDto workTimezone;

	/** The Am pm cls. */
	private Integer amPmAtr;

	@Override
	public void setRestTimezone(DiffTimeRestTimezone restTimezone) {
		this.restTimezone = new DiffTimeRestTimezoneDto();
		restTimezone.saveToMemento(this.restTimezone);
	}

	@Override
	public void setWorkTimezone(DiffTimezoneSetting workTimezone) {
		this.workTimezone = new DiffTimezoneSettingDto();
		workTimezone.saveToMemento(this.workTimezone);
	}

	@Override
	public void setAmPmAtr(AmPmAtr amPmAtr) {
		this.amPmAtr = amPmAtr.value;
	}

}
