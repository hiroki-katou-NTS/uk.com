/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.resttime;

import nts.uk.ctx.at.shared.dom.resttime.TimeTableGetMemento;
import nts.uk.ctx.at.shared.dom.worktimeset.TimeDayAtr;
import nts.uk.ctx.at.shared.infra.entity.resttime.KwtmtRestTime;

/**
 * The Class JpaTimeTableGetMemento.
 */
public class JpaTimeTableGetMemento implements TimeTableGetMemento{

	/** The entity. */
	KwtmtRestTime entity;
	
	
	/**
	 * @param entity
	 */
	public JpaTimeTableGetMemento(KwtmtRestTime entity) {
		this.entity = entity;
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.resttime.TimeTableGetMemento#getStartDay()
	 */
	@Override
	public TimeDayAtr getStartDay() {
		return null;
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.resttime.TimeTableGetMemento#getStartTime()
	 */
	@Override
	public Integer getStartTime() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.resttime.TimeTableGetMemento#getEndDay()
	 */
	@Override
	public TimeDayAtr getEndDay() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.resttime.TimeTableGetMemento#getEndTime()
	 */
	@Override
	public Integer getEndTime() {
		// TODO Auto-generated method stub
		return null;
	}

}
