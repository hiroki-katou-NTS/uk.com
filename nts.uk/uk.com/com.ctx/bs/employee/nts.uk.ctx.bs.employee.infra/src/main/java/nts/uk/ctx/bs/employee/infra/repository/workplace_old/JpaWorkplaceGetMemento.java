/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.bs.employee.infra.repository.workplace_old;

import lombok.Setter;
import nts.uk.ctx.bs.employee.dom.common.CompanyId;
import nts.uk.ctx.bs.employee.dom.common.history.Period;
import nts.uk.ctx.bs.employee.dom.workplace_old.WorkplaceCode;
import nts.uk.ctx.bs.employee.dom.workplace_old.WorkplaceGetMemento;
import nts.uk.ctx.bs.employee.dom.workplace_old.WorkplaceId;
import nts.uk.ctx.bs.employee.dom.workplace_old.WorkplaceName;
import nts.uk.ctx.bs.employee.infra.entity.workplace_old.CwpmtWorkplace;

/**
 * The Class JpaWorkplaceGetMemento.
 */
public class JpaWorkplaceGetMemento implements WorkplaceGetMemento {

	/** The cwpmt workplace. */

	/**
	 * Sets the cwpmt workplace.
	 *
	 * @param cwpmtWorkplace
	 *            the new cwpmt workplace
	 */
	@Setter
	private CwpmtWorkplace cwpmtWorkplace;

	/**
	 * Instantiates a new jpa workplace get memento.
	 *
	 * @param cwpmtWorkPlace
	 *            the cwpmt work place
	 */
	public JpaWorkplaceGetMemento(CwpmtWorkplace cwpmtWorkPlace) {
		this.cwpmtWorkplace = cwpmtWorkPlace;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.bs.employee.dom.workplace_old.WorkplaceGetMemento#getCompanyId
	 * ()
	 */
	@Override
	public CompanyId getCompanyId() {
		return new CompanyId(this.cwpmtWorkplace.getCwpmtWorkplacePK().getCid());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.bs.employee.dom.workplace_old.WorkplaceGetMemento#getPeriod()
	 */
	@Override
	public Period getPeriod() {
		return new Period(this.cwpmtWorkplace.getCwpmtWorkplacePK().getStrD(),
				this.cwpmtWorkplace.getCwpmtWorkplacePK().getEndD());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.bs.employee.dom.workplace_old.WorkplaceGetMemento#
	 * getWorkplaceId()
	 */
	@Override
	public WorkplaceId getWorkplaceId() {
		return new WorkplaceId(this.cwpmtWorkplace.getCwpmtWorkplacePK().getWkpid());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.bs.employee.dom.workplace_old.WorkplaceGetMemento#
	 * getWorkplaceCode()
	 */
	@Override
	public WorkplaceCode getWorkplaceCode() {
		return new WorkplaceCode(this.cwpmtWorkplace.getWkpcd());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.bs.employee.dom.workplace_old.WorkplaceGetMemento#
	 * getWorkplaceName()
	 */
	@Override
	public WorkplaceName getWorkplaceName() {
		return new WorkplaceName(this.cwpmtWorkplace.getWkpname());
	}

}
