/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.gateway.infra.repository.login;

import nts.uk.ctx.sys.gateway.dom.login.ContractCode;
import nts.uk.ctx.sys.gateway.dom.login.EmployeeLoginSettingGetMemento;
import nts.uk.ctx.sys.gateway.dom.login.ManageDistinct;
import nts.uk.ctx.sys.gateway.infra.entity.login.SgwmtEmployeeLogin;

/**
 * The Class JpaEmployeeLoginSettingGetMemento.
 */
public class JpaEmployeeLoginSettingGetMemento implements EmployeeLoginSettingGetMemento {

	/** The entity. */
	private SgwmtEmployeeLogin entity;
	
	/**
	 * Instantiates a new jpa employee login setting get memento.
	 *
	 * @param enity the enity
	 */
	public JpaEmployeeLoginSettingGetMemento(SgwmtEmployeeLogin enity) {
		this.entity = enity;
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.sys.gateway.dom.login.EmployeeLoginSettingGetMemento#getContractCode()
	 */
	@Override
	public ContractCode getContractCode() {
		return new ContractCode(this.entity.getContractCd());
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.sys.gateway.dom.login.EmployeeLoginSettingGetMemento#getForm2PermitAtr()
	 */
	@Override
	public ManageDistinct getForm2PermitAtr() {
		return ManageDistinct.valueOf(new Integer(this.entity.getForm2PermitAtr()));
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.sys.gateway.dom.login.EmployeeLoginSettingGetMemento#getForm3PermitAtr()
	 */
	@Override
	public ManageDistinct getForm3PermitAtr() {
		return ManageDistinct.valueOf(new Integer(this.entity.getForm3PermitAtr()));
	}

}
