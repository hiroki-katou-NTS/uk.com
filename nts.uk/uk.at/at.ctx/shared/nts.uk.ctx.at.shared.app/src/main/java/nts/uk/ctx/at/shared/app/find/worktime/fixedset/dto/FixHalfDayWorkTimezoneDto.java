/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.find.worktime.fixedset.dto;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.app.find.worktime.common.dto.TimezoneOfFixedRestTimeSetDto;
import nts.uk.ctx.at.shared.app.find.worktime.flexset.dto.FixedWorkTimezoneSetDto;
import nts.uk.ctx.at.shared.dom.worktime.common.AmPmAtr;
import nts.uk.ctx.at.shared.dom.worktime.common.FixedWorkTimezoneSet;
import nts.uk.ctx.at.shared.dom.worktime.common.TimezoneOfFixedRestTimeSet;
import nts.uk.ctx.at.shared.dom.worktime.fixedset.FixHalfDayWorkTimezoneSetMemento;

/**
 * The Class FixHalfDayWorkTimezoneDto.
 */
@Getter
@Setter
public class FixHalfDayWorkTimezoneDto implements FixHalfDayWorkTimezoneSetMemento {

	/** The rest time zone. */
	private TimezoneOfFixedRestTimeSetDto restTimezone;

	/** The work timezone. */
	private FixedWorkTimezoneSetDto workTimezone;

	/** The day atr. */
	private Integer dayAtr;

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.fixedset.
	 * FixHalfDayWorkTimezoneSetMemento#setRestTimeZone(nts.uk.ctx.at.shared.dom
	 * .worktime.fixedset.FixRestTimezoneSet)
	 */
	@Override
	public void setRestTimezone(TimezoneOfFixedRestTimeSet restTimezone) {
		if (restTimezone != null) {
			this.restTimezone = new TimezoneOfFixedRestTimeSetDto();
			restTimezone.saveToMemento(this.restTimezone);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.fixedset.
	 * FixHalfDayWorkTimezoneSetMemento#setWorkTimezone(nts.uk.ctx.at.shared.dom
	 * .worktime.fixedset.FixedWorkTimezoneSet)
	 */
	@Override
	public void setWorkTimezone(FixedWorkTimezoneSet workTimezone) {
		if (workTimezone != null) {
			this.workTimezone = new FixedWorkTimezoneSetDto();
			workTimezone.saveToMemento(this.workTimezone);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.fixedset.
	 * FixHalfDayWorkTimezoneSetMemento#setDayAtr(nts.uk.ctx.at.shared.dom.
	 * worktime_old.AmPmClassification)
	 */
	@Override
	public void setDayAtr(AmPmAtr dayAtr) {
		this.dayAtr = dayAtr.value;
	}

}
