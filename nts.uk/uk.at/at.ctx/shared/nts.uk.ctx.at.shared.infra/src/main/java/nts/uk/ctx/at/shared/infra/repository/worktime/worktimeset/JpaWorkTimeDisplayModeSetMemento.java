/******************************************************************
 * Copyright (c) 2018 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.worktime.worktimeset;

import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
import nts.uk.ctx.at.shared.dom.worktime.worktimedisplay.DisplayMode;
import nts.uk.ctx.at.shared.dom.worktime.worktimedisplay.WorkTimeDisplayModeSetMemento;
import nts.uk.ctx.at.shared.infra.entity.worktime.KshmtWorktimeDispMode;
import nts.uk.ctx.at.shared.infra.entity.worktime.KshmtWorktimeDispModePK;

/**
 * The Class JpaWorkTimeDisplayModeSetMemento.
 */
public class JpaWorkTimeDisplayModeSetMemento implements WorkTimeDisplayModeSetMemento {

	/** The entity. */
	private KshmtWorktimeDispMode entity;

	/**
	 * Instantiates a new jpa work time display mode set memento.
	 *
	 * @param entity
	 *            the entity
	 */
	public JpaWorkTimeDisplayModeSetMemento(KshmtWorktimeDispMode entity) {
		if (entity.getKshmtWorktimeDispModePK() == null) {
			entity.setKshmtWorktimeDispModePK(new KshmtWorktimeDispModePK());
		}
		this.entity = entity;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.worktimedisplay.
	 * WorkTimeDisplayModeSetMemento#setCompanyId(java.lang.String)
	 */
	@Override
	public void setCompanyId(String companyId) {
		this.entity.getKshmtWorktimeDispModePK().setCid(companyId);
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
		this.entity.getKshmtWorktimeDispModePK().setWorktimeCd(workTimeCode.v());
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
		this.entity.setDispMode(displayMode.value);
	}

}
