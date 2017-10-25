/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.auth.dom.grant;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.sys.auth.dom.role.RoleType;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

/**
 * The Class RoleIndividualGrant.
 */
@Getter
public class RoleIndividualGrant extends AggregateRoot {

	/** The user id. */
	// ユーザID
	private String userId;

	/** The role id. */
	// ロールID
	private String roleId;

	/** The company id. */
	// 会社ID
	private String companyId;

	/** The role type. */
	// ロール種類
	private RoleType roleType;

	/** The valid period. */
	// 有効期間
	private DatePeriod validPeriod;

	/**
	 * Instantiates a new role individual grant.
	 *
	 * @param memento
	 *            the memento
	 */
	public RoleIndividualGrant(RoleIndividualGrantGetMemento memento) {
		this.userId = memento.getUserId();
		this.roleId = memento.getRoleId();
		this.companyId = memento.getCompanyId();
		this.roleType = memento.getRoleType();
		this.validPeriod = memento.getValidPeriod();
	}

	/**
	 * Save to memento.
	 *
	 * @param memento
	 *            the memento
	 */
	public void saveToMemento(RoleIndividualGrantSetMemento memento) {
		memento.setUserId(this.userId);
		memento.setRoleId(this.roleId);
		memento.setCompanyId(this.companyId);
		memento.setRoleType(this.roleType);
		memento.setValidPeriod(this.validPeriod);
	}
}
