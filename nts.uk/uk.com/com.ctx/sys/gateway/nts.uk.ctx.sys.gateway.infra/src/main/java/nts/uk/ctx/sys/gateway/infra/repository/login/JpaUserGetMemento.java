/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.gateway.infra.repository.login;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.sys.gateway.dom.login.ContractCode;
import nts.uk.ctx.sys.gateway.dom.login.HashPassword;
import nts.uk.ctx.sys.gateway.dom.login.LoginId;
import nts.uk.ctx.sys.gateway.dom.login.MailAddress;
import nts.uk.ctx.sys.gateway.dom.login.UserGetMemento;
import nts.uk.ctx.sys.gateway.dom.login.UserName;
import nts.uk.ctx.sys.gateway.infra.entity.login.SgwmtUser;

/**
 * The Class JpaUserGetMemento.
 */
public class JpaUserGetMemento implements UserGetMemento {

	public final static Integer True = 1;
	public final static Integer False = 0;
	/** The entity. */
	private SgwmtUser entity;

	/**
	 * Instantiates a new jpa user get memento.
	 *
	 * @param result
	 *            the result
	 */
	public JpaUserGetMemento(SgwmtUser entity) {
		this.entity = entity;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.sys.gateway.dom.login.UserGetMemento#getUserId()
	 */
	@Override
	public String getUserId() {
		return this.entity.getUserId();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.sys.gateway.dom.login.UserGetMemento#getPassword()
	 */
	@Override
	public HashPassword getPassword() {
		return new HashPassword(this.entity.getPassword());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.sys.gateway.dom.login.UserGetMemento#getLoginId()
	 */
	@Override
	public LoginId getLoginId() {
		return new LoginId(this.entity.getLoginId());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.sys.gateway.dom.login.UserGetMemento#getContractCode()
	 */
	@Override
	public ContractCode getContractCode() {
		return new ContractCode(this.entity.getContractCd());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.sys.gateway.dom.login.UserGetMemento#getExpirationDate()
	 */
	@Override
	public GeneralDate getExpirationDate() {
		return GeneralDate.legacyDate(this.entity.getExpirationDate());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.sys.gateway.dom.login.UserGetMemento#isSpecialUser()
	 */
	@Override
	public boolean isSpecialUser() {
		return this.entity.getSpecialUser() == True ? true : false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.sys.gateway.dom.login.UserGetMemento#isMultiCompanyConcurrent(
	 * )
	 */
	@Override
	public boolean isMultiCompanyConcurrent() {
		return this.entity.getMultiCom() == True ? true : false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.sys.gateway.dom.login.UserGetMemento#getMailAddress()
	 */
	@Override
	public MailAddress getMailAddress() {
		return new MailAddress(this.entity.getMailAdd());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.sys.gateway.dom.login.UserGetMemento#getUserName()
	 */
	@Override
	public UserName getUserName() {
		return new UserName(this.entity.getUserName());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.sys.gateway.dom.login.UserGetMemento#getAssociatedPersonId()
	 */
	@Override
	public String getAssociatedPersonId() {
		return this.entity.getAssoSid();
	}

}
