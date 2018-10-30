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
	private Integer isManage;

	/** The expiration date. */
	private Integer expirationDate;

	/** The allow prepaid leave. */
	private Integer allowPrepaidLeave;

	/**
	 * Instantiates a new subst vacation setting dto.
	 */
//	public SubstVacationSettingDto() {
//		super();
//	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.vacation.setting.subst.
	 * SubstVacationSettingSetMemento#setIsManage(nts.uk.ctx.at.shared.dom.
	 * vacation.setting.ManageDistinct)
	 */
	@Override
	public void setIsManage(ManageDistinct isManage) {
		this.isManage = isManage.value;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.vacation.setting.subst.
	 * SubstVacationSettingSetMemento#setExpirationDate(nts.uk.ctx.at.shared.dom
	 * .vacation.setting.subst.VacationExpiration)
	 */
	@Override
	public void setExpirationDate(ExpirationTime expirationDate) {
		this.expirationDate = expirationDate.value;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.vacation.setting.subst.
	 * SubstVacationSettingSetMemento#setAllowPrepaidLeave(nts.uk.ctx.at.shared.
	 * dom.vacation.setting.ApplyPermission)
	 */
	@Override
	public void setAllowPrepaidLeave(ApplyPermission allowPrepaidLeave) {
		this.allowPrepaidLeave = allowPrepaidLeave.value;
	}
	
	public static SubstVacationSettingDto fromDomain(SubstVacationSetting domain) {
		return new SubstVacationSettingDto(domain.getIsManage().value, domain.getExpirationDate().value,
				domain.getAllowPrepaidLeave().value);
	}

}
