/******************************************************************
 * Copyright (c) 2018 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.worktime.worktimeset;

import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
import nts.uk.ctx.at.shared.dom.worktime.worktimedisplay.DisplayMode;
import nts.uk.ctx.at.shared.dom.worktime.worktimedisplay.WorkTimeDisplayModeSetMemento;
import nts.uk.ctx.at.shared.infra.entity.worktime.KshmtWtComDispMode;
import nts.uk.ctx.at.shared.infra.entity.worktime.KshmtWtComDispModePK;

/**
 * The Class JpaWorkTimeDisplayModeSetMemento.
 */
public class JpaWorkTimeDisplayModeSetMemento implements WorkTimeDisplayModeSetMemento {

	/** The entity. */
	private KshmtWtComDispMode entity;

	/**
	 * Instantiates a new jpa work time display mode set memento.
	 *
	 * @param entity
	 *            the entity
	 */
	public JpaWorkTimeDisplayModeSetMemento(KshmtWtComDispMode entity) {
		if (entity.getKshmtWtComDispModePK() == null) {
			entity.setKshmtWtComDispModePK(new KshmtWtComDispModePK());
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
		this.entity.getKshmtWtComDispModePK().setCid(companyId);
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
		this.entity.getKshmtWtComDispModePK().setWorktimeCd(workTimeCode.v());
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
