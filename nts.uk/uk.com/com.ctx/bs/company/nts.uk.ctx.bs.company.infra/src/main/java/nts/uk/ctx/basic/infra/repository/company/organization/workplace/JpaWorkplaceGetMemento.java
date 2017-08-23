/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.basic.infra.repository.company.organization.workplace;


import lombok.Setter;
import nts.uk.ctx.basic.dom.common.history.Period;
import nts.uk.ctx.basic.dom.company.organization.CompanyId;
import nts.uk.ctx.basic.dom.company.organization.workplace.WorkplaceCode;
import nts.uk.ctx.basic.dom.company.organization.workplace.WorkplaceGetMemento;
import nts.uk.ctx.basic.dom.company.organization.workplace.WorkplaceId;
import nts.uk.ctx.basic.dom.company.organization.workplace.WorkplaceName;
import nts.uk.ctx.basic.infra.entity.company.organization.workplace.CwpmtWorkplace;

/**
 * The Class JpaWorkplaceGetMemento.
 */
public class JpaWorkplaceGetMemento implements WorkplaceGetMemento {
	
	/** The cwpmt workplace. */
	
	@Setter
	private CwpmtWorkplace cwpmtWorkplace; 
	
	/**
	 * Instantiates a new jpa workplace get memento.
	 *
	 * @param cwpmtWorkPlace the cwpmt work place
	 */
	public JpaWorkplaceGetMemento(CwpmtWorkplace cwpmtWorkPlace) {
		this.cwpmtWorkplace = cwpmtWorkPlace;
	}

	/**
	 * Gets the company id.
	 *
	 * @return the company id
	 */
	@Override
	public CompanyId getCompanyId() {
		return new CompanyId(this.cwpmtWorkplace.getCwpmtWorkplacePK().getCid());
	}

	/**
	 * Gets the period.
	 *
	 * @return the period
	 */
	@Override
	public Period getPeriod() {
		return new Period(this.cwpmtWorkplace.getCwpmtWorkplacePK().getStrD(), this.cwpmtWorkplace.getCwpmtWorkplacePK().getEndD());
	}

	/**
	 * Gets the workplace id.
	 *
	 * @return the workplace id
	 */
	@Override
	public WorkplaceId getWorkplaceId() {
		return new WorkplaceId(this.cwpmtWorkplace.getCwpmtWorkplacePK().getWkpid());
	}

	/**
	 * Gets the workplace code.
	 *
	 * @return the workplace code
	 */
	@Override
	public WorkplaceCode getWorkplaceCode() {
		return new WorkplaceCode(this.cwpmtWorkplace.getWkpcd());
	}

	/**
	 * Gets the workplace name.
	 *
	 * @return the workplace name
	 */
	@Override
	public WorkplaceName getWorkplaceName() {
		return new WorkplaceName(this.cwpmtWorkplace.getWkpname());
	}

}
