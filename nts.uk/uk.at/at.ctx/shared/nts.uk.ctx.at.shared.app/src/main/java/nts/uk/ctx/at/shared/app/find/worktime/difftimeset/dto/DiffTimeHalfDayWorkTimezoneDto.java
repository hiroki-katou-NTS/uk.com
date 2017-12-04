/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.find.worktime.difftimeset.dto;

import lombok.Getter;
import nts.uk.ctx.at.shared.dom.worktime.difftimeset.DiffTimeHalfDaySetMemento;
import nts.uk.ctx.at.shared.dom.worktime.difftimeset.DiffTimeRestTimezone;
import nts.uk.ctx.at.shared.dom.worktime.difftimeset.DiffTimezoneSetting;
import nts.uk.ctx.at.shared.dom.worktime_old.AmPmClassification;

/**
 * The Class TimeDiffHalfDayWorkTimezone.
 */
@Getter
public class DiffTimeHalfDayWorkTimezoneDto implements DiffTimeHalfDaySetMemento {

	/** The rest timezone. */
	private DiffTimeRestTimezoneDto restTimezone;

	/** The work timezone. */
	private DiffTimezoneSettingDto workTimezone;

	/** The Am pm cls. */
	private Integer AmPmCls;

	@Override
	public void setRestTimezone(DiffTimeRestTimezone restTimezone) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setWorkTimezone(DiffTimezoneSetting workTimezone) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setAmPmCls(AmPmClassification AmPmCls) {
		// TODO Auto-generated method stub
		
	}

}
