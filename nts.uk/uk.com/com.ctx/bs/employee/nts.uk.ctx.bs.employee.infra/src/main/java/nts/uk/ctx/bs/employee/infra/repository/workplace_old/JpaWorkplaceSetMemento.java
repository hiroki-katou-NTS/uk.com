/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.bs.employee.infra.repository.workplace_old;

import lombok.Setter;
import nts.uk.ctx.bs.employee.dom.common.CompanyId;
import nts.uk.ctx.bs.employee.dom.workplace_old.WorkplaceCode;
import nts.uk.ctx.bs.employee.dom.workplace_old.WorkplaceId;
import nts.uk.ctx.bs.employee.dom.workplace_old.WorkplaceName;
import nts.uk.ctx.bs.employee.dom.workplace_old.WorkplaceSetMemento;
import nts.uk.ctx.bs.employee.infra.entity.workplace_old.CwpmtWorkplace;
import nts.uk.ctx.bs.employee.infra.entity.workplace_old.CwpmtWorkplacePK;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

/**
 * The Class JpaWorkplaceSetMemento.
 */
public class JpaWorkplaceSetMemento implements WorkplaceSetMemento {

	/**
	 * Sets the kwpmt workplace.
	 *
	 * @param kwpmtWorkplace
	 *            the new kwpmt workplace
	 */
	@Setter
	private CwpmtWorkplace kwpmtWorkplace;

	/**
	 * Instantiates a new jpa workplace set memento.
	 *
	 * @param kwpmtWorkPlace
	 *            the kwpmt work place
	 */
	public JpaWorkplaceSetMemento(CwpmtWorkplace kwpmtWorkPlace) {
		this.kwpmtWorkplace = kwpmtWorkPlace;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.basic.dom.company.organization.workplace.WorkplaceSetMemento#
	 * setCompanyId(nts.uk.ctx.basic.dom.company.organization.CompanyId)
	 */
	@Override
	public void setCompanyId(CompanyId companyId) {
		CwpmtWorkplacePK pk = new CwpmtWorkplacePK();
		pk.setCid(companyId.v());
		this.kwpmtWorkplace.setCwpmtWorkplacePK(pk);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.basic.dom.company.organization.workplace.WorkplaceSetMemento#
	 * setPeriod(nts.uk.ctx.basic.dom.common.history.Period)
	 */
	@Override
	public void setPeriod(DatePeriod period) {
		CwpmtWorkplacePK pk = this.kwpmtWorkplace.getCwpmtWorkplacePK();
		pk.setStrD(period.start());
		pk.setEndD(period.end());
		this.kwpmtWorkplace.setCwpmtWorkplacePK(pk);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.basic.dom.company.organization.workplace.WorkplaceSetMemento#
	 * setWorkplaceId(nts.uk.ctx.basic.dom.company.organization.workplace.
	 * WorkplaceId)
	 */
	@Override
	public void setWorkplaceId(WorkplaceId workplaceId) {
		CwpmtWorkplacePK pk = this.kwpmtWorkplace.getCwpmtWorkplacePK();
		pk.setWkpid(workplaceId.v());
		this.kwpmtWorkplace.setCwpmtWorkplacePK(pk);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.basic.dom.company.organization.workplace.WorkplaceSetMemento#
	 * setWorkplaceCode(nts.uk.ctx.basic.dom.company.organization.workplace.
	 * WorkplaceCode)
	 */
	@Override
	public void setWorkplaceCode(WorkplaceCode workplaceCode) {
		CwpmtWorkplacePK pk = this.kwpmtWorkplace.getCwpmtWorkplacePK();
		pk.setWkpid(workplaceCode.v());
		this.kwpmtWorkplace.setCwpmtWorkplacePK(pk);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.basic.dom.company.organization.workplace.WorkplaceSetMemento#
	 * setWorkplaceName(nts.uk.ctx.basic.dom.company.organization.workplace.
	 * WorkplaceName)
	 */
	@Override
	public void setWorkplaceName(WorkplaceName workplaceName) {
		this.kwpmtWorkplace.setWkpname(workplaceName.v());
	}

}
