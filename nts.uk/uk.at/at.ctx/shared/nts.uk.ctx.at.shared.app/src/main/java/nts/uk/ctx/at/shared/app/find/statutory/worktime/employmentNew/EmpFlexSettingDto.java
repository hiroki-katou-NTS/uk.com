/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.find.statutory.worktime.employmentNew;

import java.util.List;

import lombok.Getter;
import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.common.Year;
import nts.uk.ctx.at.shared.dom.statutory.worktime.employmentNew.EmpFlexSettingSetMemento;
import nts.uk.ctx.at.shared.dom.statutory.worktime.sharedNew.MonthlyUnit;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.EmploymentCode;

/**
 * The Class EmpMentFlexSettingDto.
 */
@Getter
public class EmpFlexSettingDto implements EmpFlexSettingSetMemento {


	/** The employment code. */
	/** 社員ID. */
	private String employmentCode;

	/** The year. */
	/** 年. */
	private int year;

	/** The statutory setting. */
	/** 法定時間. */
	private List<MonthlyUnit> statutorySetting;

	/** The specified setting. */
	/** 所定時間. */
	private List<MonthlyUnit> specifiedSetting;

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.employmentNew.
	 * EmpMentFlexSettingSetMemento#setCompanyId(nts.uk.ctx.at.shared.dom.common
	 * .CompanyId)
	 */
	@Override
	public void setCompanyId(CompanyId CompanyId) {
		// TODO Auto-generated method stub
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.employmentNew.
	 * EmpMentFlexSettingSetMemento#setEmploymentCode(nts.uk.ctx.at.shared.dom.
	 * vacation.setting.compensatoryleave.EmploymentCode)
	 */
	@Override
	public void setEmploymentCode(EmploymentCode employmentCode) {
		this.employmentCode = employmentCode.toString();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.employmentNew.
	 * EmpMentFlexSettingSetMemento#setYear(nts.uk.ctx.at.shared.dom.common.
	 * Year)
	 */
	@Override
	public void setYear(Year year) {
		this.year = year.v();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.employmentNew.
	 * EmpMentFlexSettingSetMemento#setStatutorySetting(java.util.List)
	 */
	@Override
	public void setStatutorySetting(List<MonthlyUnit> statutorySetting) {
		this.statutorySetting = statutorySetting;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.employmentNew.
	 * EmpMentFlexSettingSetMemento#setSpecifiedSetting(java.util.List)
	 */
	@Override
	public void setSpecifiedSetting(List<MonthlyUnit> specifiedSetting) {
		this.specifiedSetting = specifiedSetting;
	}

}
