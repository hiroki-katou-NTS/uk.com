/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.gateway.infra.login;

import nts.uk.ctx.sys.gateway.dom.login.EmployCodeEditType;
import nts.uk.ctx.sys.gateway.dom.login.EmployeeCodeSettingGetMemento;
import nts.uk.ctx.sys.gateway.entity.login.SgwstEmployeeCodeSet;

/**
 * The Class JpaEmployeeCodeSettingGetMemento.
 */
public class JpaEmployeeCodeSettingGetMemento implements EmployeeCodeSettingGetMemento {

	/** The entity. */
	private SgwstEmployeeCodeSet entity;

	/**
	 * Instantiates a new jpa employee code setting get memento.
	 *
	 * @param entity the entity
	 */
	public JpaEmployeeCodeSettingGetMemento(SgwstEmployeeCodeSet entity) {
		this.entity = entity;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.sys.gateway.dom.login.EmployeeCodeSettingGetMemento#
	 * getCompanyId()
	 */
	@Override
	public String getCompanyId() {
		return this.entity.getCid();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.sys.gateway.dom.login.EmployeeCodeSettingGetMemento#
	 * getNumberDigit()
	 */
	@Override
	public Integer getNumberDigit() {
		return (int) (long) this.entity.getNumberDigit();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.sys.gateway.dom.login.EmployeeCodeSettingGetMemento#
	 * getEditType()
	 */
	@Override
	public EmployCodeEditType getEditType() {
		return EmployCodeEditType.valueOf(new Integer(this.entity.getEditType()));
	}

}
