/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.command.vacation.setting.subst;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.vacation.setting.ManageDistinct;

/**
 * The Class SubstVacationDto.
 */
@Getter
@Setter
public class SubstVacationSaveBaseCommand {
	
	/** The is manage. */
	private Integer manageDeadline;

	/** The expiration date. */
	private Integer expirationDate;

	/** The allow prepaid leave. */
	private Integer allowPrepaidLeave;
		
	private Integer linkingManagementATR;

	
	private Integer isManage;
	
	public Integer getManageDistinct() {
		return isManage;
	}
	
	
}
