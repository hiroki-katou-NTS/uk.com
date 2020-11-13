/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.gateway.infra.repository.login;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.sys.gateway.dom.loginold.ContractCode;
import nts.uk.ctx.sys.gateway.dom.loginold.ContractGetMemento;
import nts.uk.ctx.sys.gateway.dom.loginold.HashPassword;
import nts.uk.ctx.sys.gateway.infra.entity.login.SgwdtContract;
import nts.arc.time.calendar.period.DatePeriod;

/**
 * The Class JpaContractGetMemento.
 */
public class JpaContractGetMemento implements ContractGetMemento {

	/** The entity. */
	private SgwdtContract entity;

	/**
	 * Instantiates a new jpa contract get memento.
	 *
	 * @param entity the entity
	 */
	public JpaContractGetMemento(SgwdtContract entity) {
		this.entity = entity;
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.sys.gateway.dom.login.ContractGetMemento#getPassword()
	 */
	@Override
	public HashPassword getPassword() {
		return new HashPassword(this.entity.getPassword());
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.sys.gateway.dom.login.ContractGetMemento#getContractCode()
	 */
	@Override
	public ContractCode getContractCode() {
		return new ContractCode(this.entity.getContractCd());
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.sys.gateway.dom.login.ContractGetMemento#getContractPeriod()
	 */
	@Override
	public DatePeriod getContractPeriod() {
		return new DatePeriod(GeneralDate.legacyDate(this.entity.getStrD()), GeneralDate.legacyDate(this.entity.getEndD()));
	}

}
