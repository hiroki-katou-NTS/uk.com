/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.infra.repository.shift.totaltimes;

import nts.uk.ctx.at.schedule.dom.shift.pattern.WorkTypeCode;
import nts.uk.ctx.at.schedule.dom.shift.totaltimes.TotalSubjectsSetMemento;
import nts.uk.ctx.at.schedule.dom.shift.totaltimes.WorkTypeAtr;
import nts.uk.ctx.at.schedule.infra.entity.shift.totaltimes.KshstTotalSubjects;

/**
 * The Class JpaTotalSubjectsSetMemento.
 */
public class JpaTotalSubjectsSetMemento implements TotalSubjectsSetMemento {

	/** The entity. */
	private KshstTotalSubjects entity;

	/**
	 * Instantiates a new jpa total subjects set memento.
	 *
	 * @param entity
	 *            the entity
	 */
	public JpaTotalSubjectsSetMemento(KshstTotalSubjects entity) {
		this.entity = entity;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.schedule.dom.shift.totaltimes.TotalSubjectsSetMemento#
	 * setWorkTypeCode(nts.uk.ctx.at.schedule.dom.shift.pattern.WorkTypeCode)
	 */
	@Override
	public void setWorkTypeCode(WorkTypeCode setWorkTypeCode) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.schedule.dom.shift.totaltimes.TotalSubjectsSetMemento#
	 * setWorkTypeAtr(nts.uk.ctx.at.schedule.dom.shift.totaltimes.WorkTypeAtr)
	 */
	@Override
	public void setWorkTypeAtr(WorkTypeAtr setWorkTypeAtr) {
		// TODO Auto-generated method stub

	}

}
