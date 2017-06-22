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
import nts.uk.ctx.basic.infra.entity.company.organization.workplace.KwpmtWplHist;

public class JpaWorkplaceHistoryGetMemento implements WorkPlaceHistoryGetMemento {

	private KwpmtWplHist kwpmtWplHist;
	
	/**
	 * @param kwpmtWplHist
	 */
	public JpaWorkplaceHistoryGetMemento(KwpmtWplHist kwpmtWplHist) {
		this.kwpmtWplHist = kwpmtWplHist;
	}

	@Override
	public CompanyId getCompanyId() {
		return new CompanyId(this.kwpmtWplHist.getKwpmtWplHistPK().getCid());
	}

	@Override
	public HistoryId getHistoryId() {
		return new HistoryId(this.kwpmtWplHist.getKwpmtWplHistPK().getHistId());
	}

	@Override
	public Period getPeriod() {
		return new Period(this.kwpmtWplHist.getStrD(),this.kwpmtWplHist.getEndD());
	}

	@Override
	public List<WorkPlaceHierarchy> getLstWorkHierarchy() {
		return null;
	}

}
