/******************************************************************
 * Copyright (c) 2018 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.auth.infra.repository.password.changelog;

import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.sys.auth.dom.password.changelog.PasswordChangeLogSetMemento;
import nts.uk.ctx.sys.auth.infra.entity.password.changelog.SacdtPasswordChangeLog;
import nts.uk.ctx.sys.shared.dom.user.password.HashPassword;

/**
 * The Class JpaPasswordChangeLogSetMemento.
 */
public class JpaPasswordChangeLogSetMemento implements PasswordChangeLogSetMemento {

	/** The entity. */
	private SacdtPasswordChangeLog entity;

	/**
	 * Instantiates a new jpa password change log set memento.
	 *
	 * @param entity the entity
	 */
	public JpaPasswordChangeLogSetMemento(SacdtPasswordChangeLog entity) {
		super();
		this.entity = entity;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.sys.auth.dom.password.changelog.PasswordChangeLogSetMemento#
	 * setLoginId(nts.uk.ctx.sys.auth.dom.password.changelog.LoginId)
	 */
	@Override
	public void setLogId(String logID) {
		this.entity.getSacdtPasswordChangeLogPK().setLogId(logID);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.sys.auth.dom.password.changelog.PasswordChangeLogSetMemento#
	 * setUserId(java.lang.String)
	 */
	@Override
	public void setUserId(String userID) {
		this.entity.getSacdtPasswordChangeLogPK().setUserId(userID);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.sys.auth.dom.password.changelog.PasswordChangeLogSetMemento#
	 * setModifiedDate(nts.arc.time.GeneralDateTime)
	 */
	@Override
	public void setModifiedDate(GeneralDateTime modifiedDate) {
		this.entity.getSacdtPasswordChangeLogPK().setModifiedDatetime(modifiedDate);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.sys.auth.dom.password.changelog.PasswordChangeLogSetMemento#
	 * setPassword(nts.uk.ctx.sys.auth.dom.password.changelog.HashPassword)
	 */
	@Override
	public void setPassword(HashPassword password) {
		this.entity.setPassword(password.v());
	}

}
