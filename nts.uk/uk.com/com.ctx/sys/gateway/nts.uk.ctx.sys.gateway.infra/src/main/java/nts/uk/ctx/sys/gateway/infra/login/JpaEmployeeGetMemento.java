/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.gateway.infra.login;

import nts.uk.ctx.sys.gateway.dom.login.EmployeeCode;
import nts.uk.ctx.sys.gateway.dom.login.EmployeeGetMemento;
import nts.uk.ctx.sys.gateway.entity.login.SgwdtEmployee;

/**
 * The Class JpaEmployeeGetMemento.
 */
public class JpaEmployeeGetMemento implements EmployeeGetMemento {

	/** The entity. */
	private SgwdtEmployee entity;

	/**
	 * Instantiates a new jpa employee get memento.
	 *
	 * @param result
	 *            the result
	 */
	public JpaEmployeeGetMemento(SgwdtEmployee entity) {
		this.entity = entity;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.sys.gateway.dom.login.EmployeeGetMemento#getBusinessName()
	 */
	@Override
	public String getBusinessName() {
		return this.entity.getBusinessName();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.sys.gateway.dom.login.EmployeeGetMemento#getCompanyId()
	 */
	@Override
	public String getCompanyId() {
		return this.entity.getSgwdtEmployeePK().getCid();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.sys.gateway.dom.login.EmployeeGetMemento#getEmployeeId()
	 */
	@Override
	public Integer getEmployeeId() {
		return Integer.valueOf(this.entity.getSgwdtEmployeePK().getSid());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.sys.gateway.dom.login.EmployeeGetMemento#getEmployeeCode()
	 */
	@Override
	public EmployeeCode getEmployeeCode() {
		return new EmployeeCode(this.entity.getScd());
	}

}
