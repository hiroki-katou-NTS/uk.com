/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.infra.repository.shift.totaltimes;

import nts.uk.ctx.at.schedule.dom.shift.pattern.WorkTypeCode;
import nts.uk.ctx.at.schedule.dom.shift.totaltimes.TotalSubjectsGetMemento;
import nts.uk.ctx.at.schedule.dom.shift.totaltimes.WorkTypeAtr;
import nts.uk.ctx.at.schedule.infra.entity.shift.totaltimes.KshstTotalSubjects;

/**
 * The Class JpaTotalSubjectsGetMemento.
 */
public class JpaTotalSubjectsGetMemento implements TotalSubjectsGetMemento {

	/** The entity. */
	private KshstTotalSubjects entity;

	/**
	 * Instantiates a new jpa total subjects get memento.
	 *
	 * @param entity
	 *            the entity
	 */
	public JpaTotalSubjectsGetMemento(KshstTotalSubjects entity) {
		this.entity = entity;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.schedule.dom.shift.totaltimes.TotalSubjectsGetMemento#
	 * getWorkTypeCode()
	 */
	@Override
	public WorkTypeCode getWorkTypeCode() {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.schedule.dom.shift.totaltimes.TotalSubjectsGetMemento#
	 * getWorkTypeAtr()
	 */
	@Override
	public WorkTypeAtr getWorkTypeAtr() {
		// TODO Auto-generated method stub
		return null;
	}
}
