/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.find.vacation.setting.sixtyhours.dto;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.vacation.setting.sixtyhours.Emp60HourVacationSetMemento;
import nts.uk.ctx.at.shared.dom.vacation.setting.sixtyhours.SixtyHourVacationSetting;

/**
 * The Class EmpSubstVacationDto.
 */
@Getter
@Setter
public class Emp60HourVacationDto extends SixtyHourVacationSettingDto
		implements Emp60HourVacationSetMemento {

	/** The contract type code. */
	private String contractTypeCode;

	/**
	 * Instantiates a new emp subst vacation dto.
	 */
	public Emp60HourVacationDto() {
		super();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.vacation.setting.subst.
	 * EmpSubstVacationSetMemento#setCompanyId(java.lang.String)
	 */
	@Override
	public void setCompanyId(String companyId) {
		// Do nothing.
	}

	@Override
	public void setEmpContractTypeCode(String contractTypeCode) {
		this.contractTypeCode = contractTypeCode;
	}

	@Override
	public void setSetting(SixtyHourVacationSetting setting) {
		this.setIsManage(setting.getIsManage());
		this.setSixtyHourExtra(setting.getSixtyHourExtra());
		this.setDigestiveUnit(setting.getDigestiveUnit());
	}

}
