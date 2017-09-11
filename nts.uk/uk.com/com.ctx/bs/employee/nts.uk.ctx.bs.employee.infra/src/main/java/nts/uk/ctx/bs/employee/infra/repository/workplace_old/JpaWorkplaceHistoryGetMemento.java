/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.bs.employee.infra.repository.workplace_old;

import java.util.List;

import nts.uk.ctx.bs.employee.dom.common.CompanyId;
import nts.uk.ctx.bs.employee.dom.common.history.Period;
import nts.uk.ctx.bs.employee.dom.workplace_old.HistoryId;
import nts.uk.ctx.bs.employee.dom.workplace_old.WorkPlaceHierarchy;
import nts.uk.ctx.bs.employee.dom.workplace_old.WorkPlaceHistoryGetMemento;
import nts.uk.ctx.bs.employee.infra.entity.workplace_old.CwpmtWkpHist;

/**
 * The Class JpaWorkplaceHistoryGetMemento.
 */
public class JpaWorkplaceHistoryGetMemento implements WorkPlaceHistoryGetMemento {

	/** The cwpmt wkp hist. */
	private CwpmtWkpHist cwpmtWkpHist;

	/**
	 * Instantiates a new jpa workplace history get memento.
	 *
	 * @param cwpmtWkpHist
	 *            the cwpmt wkp hist
	 */
	public JpaWorkplaceHistoryGetMemento(CwpmtWkpHist cwpmtWkpHist) {
		this.cwpmtWkpHist = cwpmtWkpHist;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.bs.employee.dom.workplace_old.WorkPlaceHistoryGetMemento#
	 * getCompanyId()
	 */
	@Override
	public CompanyId getCompanyId() {
		return new CompanyId(this.cwpmtWkpHist.getCwpmtWkpHistPK().getCid());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.bs.employee.dom.workplace_old.WorkPlaceHistoryGetMemento#
	 * getHistoryId()
	 */
	@Override
	public HistoryId getHistoryId() {
		return new HistoryId(this.cwpmtWkpHist.getCwpmtWkpHistPK().getHistId());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.bs.employee.dom.workplace_old.WorkPlaceHistoryGetMemento#
	 * getPeriod()
	 */
	@Override
	public Period getPeriod() {
		return new Period(this.cwpmtWkpHist.getStrD(), this.cwpmtWkpHist.getEndD());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.bs.employee.dom.workplace_old.WorkPlaceHistoryGetMemento#
	 * getLstWorkHierarchy()
	 */
	@Override
	public List<WorkPlaceHierarchy> getLstWorkHierarchy() {
		return null;
	}

}
