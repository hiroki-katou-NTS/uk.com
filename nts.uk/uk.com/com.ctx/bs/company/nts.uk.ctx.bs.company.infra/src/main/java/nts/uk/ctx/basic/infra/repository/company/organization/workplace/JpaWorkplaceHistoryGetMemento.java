/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.basic.infra.repository.company.organization.workplace;

import java.util.List;

import nts.uk.ctx.basic.dom.common.history.Period;
import nts.uk.ctx.basic.dom.company.organization.CompanyId;
import nts.uk.ctx.basic.dom.company.organization.workplace.HistoryId;
import nts.uk.ctx.basic.dom.company.organization.workplace.WorkPlaceHierarchy;
import nts.uk.ctx.basic.dom.company.organization.workplace.WorkPlaceHistoryGetMemento;
import nts.uk.ctx.basic.infra.entity.company.organization.workplace.CwpmtWkpHist;

/**
 * The Class JpaWorkplaceHistoryGetMemento.
 */
public class JpaWorkplaceHistoryGetMemento implements WorkPlaceHistoryGetMemento {

	/** The cwpmt wkp hist. */
	private CwpmtWkpHist cwpmtWkpHist;
	
	/**
	 * Instantiates a new jpa workplace history get memento.
	 *
	 * @param cwpmtWkpHist the cwpmt wkp hist
	 */
	public JpaWorkplaceHistoryGetMemento(CwpmtWkpHist cwpmtWkpHist) {
		this.cwpmtWkpHist = cwpmtWkpHist;
	}

	/**
	 * Gets the company id.
	 *
	 * @return the company id
	 */
	@Override
	public CompanyId getCompanyId() {
		return new CompanyId(this.cwpmtWkpHist.getCwpmtWkpHistPK().getCid());
	}

	/**
	 * Gets the history id.
	 *
	 * @return the history id
	 */
	@Override
	public HistoryId getHistoryId() {
		return new HistoryId(this.cwpmtWkpHist.getCwpmtWkpHistPK().getHistId());
	}

	/**
	 * Gets the period.
	 *
	 * @return the period
	 */
	@Override
	public Period getPeriod() {
		return new Period(this.cwpmtWkpHist.getStrD(),this.cwpmtWkpHist.getEndD());
	}

	/**
	 * Gets the lst work hierarchy.
	 *
	 * @return the lst work hierarchy
	 */
	@Override
	public List<WorkPlaceHierarchy> getLstWorkHierarchy() {
		return null;
	}

}
