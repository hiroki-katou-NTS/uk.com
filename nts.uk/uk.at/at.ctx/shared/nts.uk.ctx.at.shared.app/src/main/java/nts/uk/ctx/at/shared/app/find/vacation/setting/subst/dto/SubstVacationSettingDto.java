/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.find.vacation.setting.subst.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.vacation.setting.ApplyPermission;
import nts.uk.ctx.at.shared.dom.vacation.setting.ExpirationTime;
import nts.uk.ctx.at.shared.dom.vacation.setting.ManageDistinct;
import nts.uk.ctx.at.shared.dom.vacation.setting.subst.ComSubstVacation;
import nts.uk.ctx.at.shared.dom.vacation.setting.subst.ManageDeadline;
import nts.uk.ctx.at.shared.dom.vacation.setting.subst.SubstVacationSetting;
import nts.uk.ctx.at.shared.dom.vacation.setting.subst.SubstVacationSettingSetMemento;

/**
 * The Class SubstVacationSettingDto.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubstVacationSettingDto implements SubstVacationSettingSetMemento {

	/** The is manage. */
	private Integer manageDeadline;

	/** The expiration date. */
	private Integer expirationDate;

	/** The allow prepaid leave. */
	private Integer allowPrepaidLeave;
	
	private Integer manageDistinct;
		
	private Integer  linkingManagementATR;
	

	@Override
	public void setExpirationDate(ExpirationTime expirationDate) {
		this.expirationDate = expirationDate.value;
	}

	@Override
	public void setAllowPrepaidLeave(ApplyPermission allowPrepaidLeave) {
		this.allowPrepaidLeave = allowPrepaidLeave.value;
	}
	

	@Override
	public void setManageDistinct(ManageDistinct manageDistinct) {
		this.manageDistinct = manageDistinct.value;
	}

	@Override
	public void setLinkingManagementATR(ManageDistinct linkingManagementATR) {
		this.linkingManagementATR = linkingManagementATR.value;
	}
	
	@Override
	public void setManageDeadline(ManageDeadline manageDeadline) {
		this.manageDeadline = manageDeadline.value;
	}
	
	public static SubstVacationSettingDto fromDomain(ComSubstVacation domain) {
		return new SubstVacationSettingDto(
				domain.getSetting().getManageDeadline().value,
				domain.getSetting().getExpirationDate().value,
				domain.getSetting().getAllowPrepaidLeave().value,
				domain.getManageDistinct().value,
				domain.getLinkingManagementATR().value);
	} 
	

}
