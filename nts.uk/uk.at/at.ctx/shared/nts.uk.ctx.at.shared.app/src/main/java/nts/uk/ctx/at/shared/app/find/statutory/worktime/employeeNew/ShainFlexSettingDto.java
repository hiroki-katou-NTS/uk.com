/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.find.statutory.worktime.employeeNew;

import java.util.List;

import lombok.Getter;
import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.common.EmployeeId;
import nts.uk.ctx.at.shared.dom.common.Year;
import nts.uk.ctx.at.shared.dom.statutory.worktime.employeeNew.ShainFlexSettingSetMemento;
import nts.uk.ctx.at.shared.dom.statutory.worktime.sharedNew.MonthlyUnit;


/**
 * The Class ShainFlexSettingDto.
 */
@Getter
public class ShainFlexSettingDto implements ShainFlexSettingSetMemento{
	
	/** The employee id. */
	/** 社員ID. */
	private String employeeId;
	
	/** The year. */
	/** 年. */
	private int year;
	
	/** The statutory setting. */
	/** 法定時間. */
	private List<MonthlyUnit> statutorySetting;
	
	/** The specified setting. */
	/** 所定時間. */
	private List<MonthlyUnit> specifiedSetting;

	/**
	 * Sets the company id.
	 *
	 * @param companyId the new company id
	 */
	@Override
	public void setCompanyId(CompanyId companyId) {
		// 		
	}

	/**
	 * Sets the employee id.
	 *
	 * @param employeeId the new employee id
	 */
	@Override
	public void setEmployeeId(EmployeeId employeeId) {
		this.employeeId = employeeId.toString();
		
	}

	/**
	 * Sets the year.
	 *
	 * @param year the new year
	 */
	@Override
	public void setYear(Year year) {
		this.year = year.v();
	}

	/**
	 * Sets the statutory setting.
	 *
	 * @param statutorySetting the new statutory setting
	 */
	@Override
	public void setStatutorySetting(List<MonthlyUnit> statutorySetting) {
		this.statutorySetting = statutorySetting;
		
	}

	/**
	 * Sets the specified setting.
	 *
	 * @param specifiedSetting the new specified setting
	 */
	@Override
	public void setSpecifiedSetting(List<MonthlyUnit> specifiedSetting) {
		this.specifiedSetting = specifiedSetting;
	}
}
