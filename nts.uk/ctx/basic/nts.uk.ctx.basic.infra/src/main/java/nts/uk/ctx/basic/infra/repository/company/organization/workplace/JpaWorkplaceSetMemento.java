/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.basic.infra.repository.company.organization.workplace;

import lombok.Setter;
import nts.uk.ctx.basic.dom.common.history.Period;
import nts.uk.ctx.basic.dom.company.organization.CompanyId;
import nts.uk.ctx.basic.dom.company.organization.workplace.WorkplaceCode;
import nts.uk.ctx.basic.dom.company.organization.workplace.WorkplaceId;
import nts.uk.ctx.basic.dom.company.organization.workplace.WorkplaceName;
import nts.uk.ctx.basic.dom.company.organization.workplace.WorkplaceSetMemento;
import nts.uk.ctx.basic.infra.entity.company.organization.workplace.KwpmtWorkPlace;
import nts.uk.ctx.basic.infra.entity.company.organization.workplace.KwpmtWorkPlacePK;

public class JpaWorkplaceSetMemento implements WorkplaceSetMemento{
	
	@Setter
	private KwpmtWorkPlace kwpmtWorkPlace;
	
	/**
	 * Instantiates a new jpa workplace set memento.
	 *
	 * @param kwpmtWorkPlace the kwpmt work place
	 */
	public JpaWorkplaceSetMemento(KwpmtWorkPlace kwpmtWorkPlace) {
		this.kwpmtWorkPlace = kwpmtWorkPlace;
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
		KwpmtWorkPlacePK pk = new KwpmtWorkPlacePK();
		pk.setCcid(companyId.v());
		this.kwpmtWorkPlace.setKwpmtWorkPlacePK(pk);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.basic.dom.company.organization.workplace.WorkplaceSetMemento#
	 * setPeriod(nts.uk.ctx.basic.dom.common.history.Period)
	 */
	@Override
	public void setPeriod(Period period) {
		this.kwpmtWorkPlace.setStrD(period.getStartDate());
		this.kwpmtWorkPlace.setEndD(period.getEndDate());
		
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
		KwpmtWorkPlacePK pk = this.kwpmtWorkPlace.getKwpmtWorkPlacePK();
		pk.setWkpid(workplaceId.v());
		this.kwpmtWorkPlace.setKwpmtWorkPlacePK(pk);
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
		KwpmtWorkPlacePK pk = this.kwpmtWorkPlace.getKwpmtWorkPlacePK();
		pk.setWkpcd(workplaceCode.v());
		this.kwpmtWorkPlace.setKwpmtWorkPlacePK(pk);
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
		this.kwpmtWorkPlace.setWkpname(workplaceName.v());
	}

}
