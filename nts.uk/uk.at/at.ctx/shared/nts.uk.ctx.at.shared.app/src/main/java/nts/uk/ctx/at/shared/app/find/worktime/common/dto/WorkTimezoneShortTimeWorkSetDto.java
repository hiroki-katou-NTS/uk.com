/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.find.worktime.common.dto;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimezoneShortTimeWorkSetSetMemento;

/**
 * The Class WorkTimezoneShortTimeWorkSetDto.
 */
@Getter
@Setter
public class WorkTimezoneShortTimeWorkSetDto implements WorkTimezoneShortTimeWorkSetSetMemento {
	
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
	 * WorkTimezoneShortTimeWorkSetSetMemento#setNursTimezoneWorkUse(boolean)
	 */
	@Override
	public void setNursTimezoneWorkUse(boolean val) {
		this.nursTimezoneWorkUse = val;
		
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.common.
	 * WorkTimezoneShortTimeWorkSetSetMemento#setEmploymentTimeDeduct(boolean)
	 */
	@Override
	public void setEmploymentTimeDeduct(boolean val) {
		this.employmentTimeDeduct = val;
		
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.common.
	 * WorkTimezoneShortTimeWorkSetSetMemento#setChildCareWorkUse(boolean)
	 */
	@Override
	public void setChildCareWorkUse(boolean val) {
		this.childCareWorkUse = val;
		
	}
	
}
