/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.env.infra.mailserver;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.uk.ctx.sys.env.dom.mailserver.MailServer;
import nts.uk.ctx.sys.env.dom.mailserver.MailServerRepository;

/**
 * The Class JpaMailServerRepository.
 */
@Stateless
public class JpaMailServerRepository implements MailServerRepository {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.sys.env.dom.mailserver.MailServerRepository#findBy(java.lang.
	 * String)
	 */
	@Override
	public Optional<MailServer> findBy(String companyId) {
		// TODO Auto-generated method stub
		return null;
	}

}
