/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.find.vacation.setting.acquisitionrule;

import lombok.Builder;
import nts.uk.ctx.at.shared.dom.vacation.setting.SettingDistinct;
import nts.uk.ctx.at.shared.dom.vacation.setting.acquisitionrule.AcquisitionRuleSetMemento;
import nts.uk.ctx.at.shared.dom.vacation.setting.acquisitionrule.AnnualHoliday;
import nts.uk.ctx.at.shared.dom.vacation.setting.acquisitionrule.HoursHoliday;

/**
 * The Class VacationAcquisitionRuleDto.
 */
@Builder
public class AcquisitionRuleDto implements AcquisitionRuleSetMemento {

	/** The company id. */
	public String companyId;

	/** The category. */
	public int category;
	
	/** */
	public AnnualHolidayItemDto annualHolidayShow;
	
	/** */
	public HoursHolidayItemDto hoursHolidayShow;

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.vacation.setting.acquisitionrule.
	 * AcquisitionRuleSetMemento#setCompanyId(java.lang.String)
	 */
	@Override
	public void setCompanyId(String companyId) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.core.dom.vacationacquisitionrule.VaAcRuleSetMemento#
	 * setSettingclassification(nts.uk.ctx.pr.core.dom.vacationacquisitionrule.
	 * Settingclassification)
	 */
	@Override
	public void setCategory(SettingDistinct category) {
		this.category = category.value;
	}

	/*
	 * (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.vacation.setting.acquisitionrule.
	 * AcquisitionRuleSetMemento#setAnnualHoliday
	 * (nts.uk.ctx.at.shared.dom.vacation.setting.acquisitionrule.AnnualHoliday)
	 */
	@Override
	public void setAnnualHoliday(AnnualHoliday annualHoliday) {
		AnnualHolidayItemDto dto = AnnualHolidayItemDto.builder().build();
		annualHoliday.saveToMemento(dto);
		this.annualHolidayShow = dto;		
	}

	/*
	 * (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.vacation.setting.acquisitionrule.
	 * AcquisitionRuleSetMemento#setHoursHoliday
	 * (nts.uk.ctx.at.shared.dom.vacation.setting.acquisitionrule.HoursHoliday)
	 */

}
