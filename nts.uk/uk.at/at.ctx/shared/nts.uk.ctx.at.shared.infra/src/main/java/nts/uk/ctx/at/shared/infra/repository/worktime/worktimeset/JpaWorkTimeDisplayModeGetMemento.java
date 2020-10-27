/******************************************************************
 * Copyright (c) 2018 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.worktime.worktimeset;

import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
import nts.uk.ctx.at.shared.dom.worktime.worktimedisplay.DisplayMode;
import nts.uk.ctx.at.shared.dom.worktime.worktimedisplay.WorkTimeDisplayModeGetMemento;
import nts.uk.ctx.at.shared.infra.entity.worktime.KshmtWtComDispMode;

/**
 * The Class JpaWorkTimeDisplayModeGetMemento.
 */
public class JpaWorkTimeDisplayModeGetMemento implements WorkTimeDisplayModeGetMemento {

	/** The entity. */
	private KshmtWtComDispMode entity;

	/**
	 * Instantiates a new jpa work time display mode get memento.
	 *
	 * @param entity
	 *            the entity
	 */
	public JpaWorkTimeDisplayModeGetMemento(KshmtWtComDispMode entity) {
		this.entity = entity;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.worktimedisplay.
	 * WorkTimeDisplayModeGetMemento#getCompanyId()
	 */
	@Override
	public String getCompanyId() {
		return this.entity.getKshmtWtComDispModePK().getCid();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.worktimedisplay.
	 * WorkTimeDisplayModeGetMemento#getWorktimeCode()
	 */
	@Override
	public WorkTimeCode getWorktimeCode() {
		return new WorkTimeCode(this.entity.getKshmtWtComDispModePK().getWorktimeCd());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.worktimedisplay.
	 * WorkTimeDisplayModeGetMemento#getDisplayMode()
	 */
	@Override
	public DisplayMode getDisplayMode() {
		return DisplayMode.valueOf(this.entity.getDispMode());
	}

}
