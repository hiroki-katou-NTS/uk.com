/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.request.infra.repository.application.vacaction.history;

import nts.uk.ctx.at.request.dom.settting.worktype.history.OptionalMaxDay;
import nts.uk.ctx.at.request.dom.settting.worktype.history.PlanVacationHistoryGetMemento;
import nts.uk.ctx.at.request.infra.entity.valication.history.KrqmtVacationHistory;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

/**
 * The Class JpaPlanVacationHistoryGetMemento.
 */
public class JpaPlanVacationHistoryGetMemento implements PlanVacationHistoryGetMemento{

	/** The entity. */
	private KrqmtVacationHistory entity;
	
	/**
	 * Instantiates a new jpa plan vacation history get memento.
	 *
	 * @param item the item
	 */
	public JpaPlanVacationHistoryGetMemento(KrqmtVacationHistory item) {
		this.entity = item;
	}
	
	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.request.dom.settting.worktype.history.PlanVacationHistoryGetMemento#getHistoryId()
	 */
	@Override
	public String getHistoryId() {
		return this.entity.getKrqmtVacationHistoryPK().getHistoryId();
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.request.dom.settting.worktype.history.PlanVacationHistoryGetMemento#getCompanyId()
	 */
	@Override
	public String getCompanyId() {
		return this.entity.getKrqmtVacationHistoryPK().getCid();
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.request.dom.settting.worktype.history.PlanVacationHistoryGetMemento#getWorkTypeCode()
	 */
	@Override
	public String getWorkTypeCode() {
		return this.entity.getKrqmtVacationHistoryPK().getWorktypeCd();
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.request.dom.settting.worktype.history.PlanVacationHistoryGetMemento#getMaxDay()
	 */
	@Override
	public OptionalMaxDay getMaxDay() {
		return new OptionalMaxDay(this.entity.getMaxDay());
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.request.dom.settting.worktype.history.PlanVacationHistoryGetMemento#getPeriod()
	 */
	@Override
	public DatePeriod getPeriod() {
		return new DatePeriod(this.entity.getStartDate(),this.entity.getEndDate());
	}

}
