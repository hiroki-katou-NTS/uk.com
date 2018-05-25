/******************************************************************
 * Copyright (c) 2018 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.auth.infra.repository.password.changelog;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.sys.auth.dom.password.changelog.PasswordChangeLog;
import nts.uk.ctx.sys.auth.dom.password.changelog.PasswordChangeLogRepository;
import nts.uk.ctx.sys.auth.infra.entity.password.changelog.SacdtPasswordChangeLog;
import nts.uk.ctx.sys.auth.infra.entity.password.changelog.SacdtPasswordChangeLogPK;

/**
 * The Class PasswordChangeLogRepositoryImpl.
 */
@Stateless
public class PasswordChangeLogRepositoryImpl extends JpaRepository implements PasswordChangeLogRepository {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.sys.auth.dom.password.changelog.PasswordChangeLogRepository#
	 * register(nts.uk.ctx.sys.auth.dom.password.changelog.PasswordChangeLog)
	 */
	@Override
	public void add(PasswordChangeLog passwordChangeLog) {
		SacdtPasswordChangeLog entity = new SacdtPasswordChangeLog(new SacdtPasswordChangeLogPK());
		passwordChangeLog.saveToMemento(new JpaPasswordChangeLogSetMemento(entity));
		this.commandProxy().insert(entity);

	}

}
