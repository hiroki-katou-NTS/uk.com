/******************************************************************
 * Copyright (c) 2018 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.env.infra.repository.mailnoticeset;

import java.util.List;

import javax.ejb.Stateless;

import nts.uk.ctx.sys.env.dom.mailnoticeset.MailFunction;
import nts.uk.ctx.sys.env.dom.mailnoticeset.MailFunctionRepository;

/**
 * The Class JpaMailFunctionRepository.
 */
@Stateless
public class JpaMailFunctionRepository implements MailFunctionRepository {

	/* (non-Javadoc)
	 * @see nts.uk.ctx.sys.env.dom.mailnoticeset.MailFunctionRepository#findAll(java.lang.Boolean)
	 */
	@Override
	public List<MailFunction> findAll(Boolean proprietySendMailSettingAtr) {
		// TODO Auto-generated method stub
		return null;
	}

}
