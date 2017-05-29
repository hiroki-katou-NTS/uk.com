/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.entity.vacation.setting.subst;

import javax.persistence.Column;

import lombok.Getter;
import lombok.Setter;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * The Class KsvstComSubstVacation.
 */
@Getter
@Setter
public abstract class KsvstSubstVacationSetting extends UkJpaEntity {

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
