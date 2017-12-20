/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.scherec.totaltimes;

import nts.uk.ctx.at.shared.dom.scherec.totaltimes.TotalSubjectsGetMemento;
import nts.uk.ctx.at.shared.dom.scherec.totaltimes.WorkTypeAtr;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeCode;
import nts.uk.ctx.at.shared.infra.entity.scherec.totaltimes.KshstTotalSubjects;

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
	 * @see nts.uk.ctx.at.shared.dom.scherec.totaltimes.TotalSubjectsGetMemento#
	 * getWorkTypeCode()
	 */
	@Override
	public WorkTypeCode getWorkTypeCode() {
		return new WorkTypeCode(this.entity.getKshstTotalSubjectsPK().getWorkTypeCd());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.scherec.totaltimes.TotalSubjectsGetMemento#
	 * getWorkTypeAtr()
	 */
	@Override
	public WorkTypeAtr getWorkTypeAtr() {
		return WorkTypeAtr.valueOf(this.entity.getKshstTotalSubjectsPK().getWorkTypeAtr());
	}
}
