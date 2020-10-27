/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.entity.vacation.setting.subst;

import javax.persistence.Column;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.MappedSuperclass;

import lombok.Getter;
import lombok.Setter;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/**
 * The Class KsvstComSubstVacation.
 */
@Getter
@Setter
@MappedSuperclass
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class KsvstSubstVacationSetting extends ContractUkJpaEntity {

	/** The is manage. */
	@Column(name = "IS_MANAGE")
	private int isManage;

	/** The expiration date set. */
	@Column(name = "EXPIRATION_DATE_SET")
	private int expirationDateSet;

	/** The allow prepaid leave. */
	@Column(name = "ALLOW_PREPAID_LEAVE")
	private int allowPrepaidLeave;

}
