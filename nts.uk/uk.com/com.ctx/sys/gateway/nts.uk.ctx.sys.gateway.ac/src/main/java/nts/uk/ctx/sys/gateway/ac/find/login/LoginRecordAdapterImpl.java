/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.gateway.ac.find.login;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.sys.gateway.dom.login.adapter.loginrecord.LoginRecordAdapter;
import nts.uk.ctx.sys.gateway.dom.login.adapter.loginrecord.LoginRecordDto;

/**
 * The Class LoginRecordAdapterImpl.
 */
@Stateless
public class LoginRecordAdapterImpl implements LoginRecordAdapter{
	
	/** The login record publisher. */
	@Inject
	private LoginRecordPublisher loginRecordPublisher;
	
	/* (non-Javadoc)
	 * @see nts.uk.ctx.sys.gateway.dom.login.adapter.loginrecord.LoginRecordAdapter#addLoginRecord(nts.uk.ctx.sys.gateway.dom.login.adapter.loginrecord.LoginRecordDto)
	 */
	@Override
	public void addLoginRecord(LoginRecordDto dto){
		this.loginRecordPublisher.addLoginRecord(dto);
	}

}
