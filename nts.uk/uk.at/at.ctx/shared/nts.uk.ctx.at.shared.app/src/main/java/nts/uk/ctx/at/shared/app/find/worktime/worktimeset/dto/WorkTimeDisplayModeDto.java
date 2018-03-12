/******************************************************************
 * Copyright (c) 2018 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.find.worktime.worktimeset.dto;

import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
import nts.uk.ctx.at.shared.dom.worktime.worktimedisplay.DisplayMode;
import nts.uk.ctx.at.shared.dom.worktime.worktimedisplay.WorkTimeDisplayModeSetMemento;

/**
 * The Class WorkTimeDisplayModeDto.
 */
public class WorkTimeDisplayModeDto implements WorkTimeDisplayModeSetMemento {

	/** The company id. */
	public String companyId;

	/** The worktime code. */
	public String worktimeCode;

	/** The display mode. */
	public Integer displayMode;

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.worktimedisplay.
	 * WorkTimeDisplayModeSetMemento#setCompanyId(java.lang.String)
	 */
	@Override
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.worktimedisplay.
	 * WorkTimeDisplayModeSetMemento#setWorktimeCode(nts.uk.ctx.at.shared.dom.
	 * worktime.common.WorkTimeCode)
	 */
	@Override
	public void setWorktimeCode(WorkTimeCode workTimeCode) {
		this.worktimeCode = workTimeCode.v();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.worktimedisplay.
	 * WorkTimeDisplayModeSetMemento#setWorkTimeDivision(nts.uk.ctx.at.shared.
	 * dom.worktime.worktimedisplay.DisplayMode)
	 */
	@Override
	public void setWorkTimeDivision(DisplayMode displayMode) {
		this.displayMode = displayMode.value;
	}

}
