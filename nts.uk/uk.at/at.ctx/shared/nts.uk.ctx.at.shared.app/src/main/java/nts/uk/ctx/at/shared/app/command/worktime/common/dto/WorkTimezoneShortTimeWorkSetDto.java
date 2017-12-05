/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.command.worktime.common.dto;

import lombok.Value;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimezoneShortTimeWorkSetGetMemento;

/**
 * The Class WorkTimezoneShortTimeWorkSetDto.
 */
@Value
public class WorkTimezoneShortTimeWorkSetDto implements WorkTimezoneShortTimeWorkSetGetMemento {

	/** The nurs timezone work use. */
	private boolean nursTimezoneWorkUse;

	/** The employment time deduct. */
	private boolean employmentTimeDeduct;

	/** The child care work use. */
	private boolean childCareWorkUse;

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.common.
	 * WorkTimezoneShortTimeWorkSetGetMemento#getNursTimezoneWorkUse()
	 */
	@Override
	public boolean getNursTimezoneWorkUse() {
		return this.nursTimezoneWorkUse;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.common.
	 * WorkTimezoneShortTimeWorkSetGetMemento#getEmploymentTimeDeduct()
	 */
	@Override
	public boolean getEmploymentTimeDeduct() {
		return this.employmentTimeDeduct;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.common.
	 * WorkTimezoneShortTimeWorkSetGetMemento#getChildCareWorkUse()
	 */
	@Override
	public boolean getChildCareWorkUse() {
		return this.childCareWorkUse;
	}

}
