/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.vacation.setting.compensleave;

import lombok.Getter;
import nts.arc.layer.dom.DomainObject;
import nts.uk.ctx.at.shared.dom.vacation.setting.ApplyPermission;
import nts.uk.ctx.at.shared.dom.vacation.setting.ManageDistinct;

/**
 * The Class CompensatoryLeaveSetting.
 */
@Getter
public class CompensatoryLeaveSetting extends DomainObject {

	/** The manage dist. */
	private ManageDistinct isManage;

	/** The expiration date. */
	private VacationExpiration expirationDate;

	/** The allow prepaid leave. */
	private ApplyPermission allowPrepaidLeave;

	/**
	 * Instantiates a new compensatory leave setting.
	 *
	 * @param isManage
	 *            the manage dist
	 * @param expirationDate
	 *            the expiration date
	 * @param allowPrepaidLeave
	 *            the allow prepaid leave
	 */
	public CompensatoryLeaveSetting(ManageDistinct isManage, VacationExpiration expirationDate,
			ApplyPermission allowPrepaidLeave) {
		super();
		this.isManage = isManage;
		this.expirationDate = expirationDate;
		this.allowPrepaidLeave = allowPrepaidLeave;
	}

}
