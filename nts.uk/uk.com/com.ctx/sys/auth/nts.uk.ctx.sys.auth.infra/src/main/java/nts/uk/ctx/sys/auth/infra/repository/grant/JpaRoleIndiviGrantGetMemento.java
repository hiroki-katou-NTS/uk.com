/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.auth.infra.repository.grant;

import nts.uk.ctx.sys.auth.dom.grant.RoleIndividualGrantGetMemento;
import nts.uk.ctx.sys.auth.dom.role.RoleType;
import nts.uk.ctx.sys.auth.infra.entity.grant.SacmtRoleIndiviGrant;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

/**
 * The Class JpaRoleIndiviGrantGetMemento.
 */
public class JpaRoleIndiviGrantGetMemento implements RoleIndividualGrantGetMemento {

	/** The entity. */
	private SacmtRoleIndiviGrant entity;

	/**
	 * Instantiates a new jpa role indivi grant get memento.
	 *
	 * @param item the item
	 */
	public JpaRoleIndiviGrantGetMemento(SacmtRoleIndiviGrant item) {
		this.entity = item;
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.sys.auth.dom.grant.RoleIndividualGrantGetMemento#getUserId()
	 */
	@Override
	public String getUserId() {
		return this.entity.getSacmtRoleIndiviGrantPK().getUserId();
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.sys.auth.dom.grant.RoleIndividualGrantGetMemento#getRoleId()
	 */
	@Override
	public String getRoleId() {
		return this.entity.getRoleId();
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.sys.auth.dom.grant.RoleIndividualGrantGetMemento#getCompanyId()
	 */
	@Override
	public String getCompanyId() {
		return this.entity.getSacmtRoleIndiviGrantPK().getCid();
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.sys.auth.dom.grant.RoleIndividualGrantGetMemento#getRoleType()
	 */
	@Override
	public RoleType getRoleType() {
		return null;
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.sys.auth.dom.grant.RoleIndividualGrantGetMemento#getValidPeriod()
	 */
	@Override
	public DatePeriod getValidPeriod() {
		return new DatePeriod(this.entity.getStrD(), this.entity.getEndD());
	}

}
