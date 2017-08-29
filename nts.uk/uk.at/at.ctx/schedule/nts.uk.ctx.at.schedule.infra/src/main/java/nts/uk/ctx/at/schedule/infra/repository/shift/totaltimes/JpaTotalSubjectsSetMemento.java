/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.infra.repository.shift.totaltimes;

import nts.uk.ctx.at.schedule.dom.shift.pattern.WorkTypeCode;
import nts.uk.ctx.at.schedule.dom.shift.totaltimes.TotalSubjectsSetMemento;
import nts.uk.ctx.at.schedule.dom.shift.totaltimes.WorkTypeAtr;
import nts.uk.ctx.at.schedule.infra.entity.shift.totaltimes.KshstTotalSubjects;
import nts.uk.ctx.at.schedule.infra.entity.shift.totaltimes.KshstTotalSubjectsPK;

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
	public JpaTotalSubjectsSetMemento(String cid, Integer totalTimesNo, KshstTotalSubjects entity) {
		if (entity.getKshstTotalSubjectsPK() == null) {
			KshstTotalSubjectsPK pk = new KshstTotalSubjectsPK();
			pk.setCid(cid);
			pk.setTotalTimesNo(totalTimesNo);
			entity.setKshstTotalSubjectsPK(pk);
		}
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
		this.entity.getKshstTotalSubjectsPK().setWorkTypeCd(setWorkTypeCode.v());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.schedule.dom.shift.totaltimes.TotalSubjectsSetMemento#
	 * setWorkTypeAtr(nts.uk.ctx.at.schedule.dom.shift.totaltimes.WorkTypeAtr)
	 */
	@Override
	public void setWorkTypeAtr(WorkTypeAtr setWorkTypeAtr) {
		KshstTotalSubjectsPK pk = this.entity.getKshstTotalSubjectsPK();
		pk.setWorkTypeAtr(setWorkTypeAtr.value);
		this.entity.setKshstTotalSubjectsPK(pk);
	}

}
