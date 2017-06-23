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
import nts.uk.ctx.basic.infra.entity.company.organization.workplace.KwpmtWorkplace;

/**
 * The Class JpaWorkplaceGetMemento.
 */
public class JpaWorkplaceGetMemento implements WorkplaceGetMemento {
	
	/** The kwpmt work place. */
	
	/**
	 * Sets the kwpmt work place.
	 *
	 * @param kwpmtWorkPlace the new kwpmt work place
	 */
	@Setter
	private KwpmtWorkplace kwpmtWorkplace; 
	
	/**
	 * Instantiates a new jpa workplace get memento.
	 *
	 * @param kwpmtWorkPlace the kwpmt work place
	 */
	public JpaWorkplaceGetMemento(KwpmtWorkplace kwpmtWorkPlace) {
		this.kwpmtWorkplace = kwpmtWorkPlace;
	}

	/**
	 * Gets the company id.
	 *
	 * @return the company id
	 */
	@Override
	public CompanyId getCompanyId() {
		return new CompanyId(this.kwpmtWorkplace.getKwpmtWorkplacePK().getCid());
	}

	/**
	 * Gets the period.
	 *
	 * @return the period
	 */
	@Override
	public Period getPeriod() {
		return new Period(this.kwpmtWorkplace.getStrD(), this.kwpmtWorkplace.getEndD());
	}

	/**
	 * Gets the workplace id.
	 *
	 * @return the workplace id
	 */
	@Override
	public WorkplaceId getWorkplaceId() {
		return new WorkplaceId(this.kwpmtWorkplace.getKwpmtWorkplacePK().getWplid());
	}

	/**
	 * Gets the workplace code.
	 *
	 * @return the workplace code
	 */
	@Override
	public WorkplaceCode getWorkplaceCode() {
		return new WorkplaceCode(this.kwpmtWorkplace.getKwpmtWorkplacePK().getWplcd());
	}

	/**
	 * Gets the workplace name.
	 *
	 * @return the workplace name
	 */
	@Override
	public WorkplaceName getWorkplaceName() {
		return new WorkplaceName(this.kwpmtWorkplace.getWplname());
	}

}
