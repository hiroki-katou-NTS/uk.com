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
import nts.uk.ctx.at.shared.dom.statutory.worktime.employeeNew.ShainDeforLaborSettingSetMemento;
import nts.uk.ctx.at.shared.dom.statutory.worktime.sharedNew.MonthlyUnit;


/**
 * The Class ShainDeforLaborSettingDto.
 */
@Getter
public class ShainDeforLaborSettingDto implements ShainDeforLaborSettingSetMemento {

	/** The company id. */
	/** 会社ID. */
	private CompanyId companyId;

	/** The employee id. */
	/** 社員ID. */
	private EmployeeId employeeId;

	/** The year. */
	/** 年. */
	private Year year;

	/** The statutory setting. */
	/** 法定時間. */
	private List<MonthlyUnit> statutorySetting;

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.employeeNew.
	 * EmpDeforLaborSettingSetMemento#setCompanyId(nts.uk.ctx.at.shared.dom.
	 * common.CompanyId)
	 */
	@Override
	public void setCompanyId(CompanyId companyId) {
          
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.employeeNew.
	 * EmpDeforLaborSettingSetMemento#setEmployeeId(nts.uk.ctx.at.shared.dom.
	 * common.EmployeeId)
	 */
	@Override
	public void setEmployeeId(EmployeeId employeeId) {
		this.employeeId = employeeId;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.employeeNew.
	 * EmpDeforLaborSettingSetMemento#setYear(nts.uk.ctx.at.shared.dom.common.
	 * Year)
	 */
	@Override
	public void setYear(Year year) {
		this.year = year;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.employeeNew.
	 * EmpDeforLaborSettingSetMemento#setStatutorySetting(java.util.List)
	 */
	@Override
	public void setStatutorySetting(List<MonthlyUnit> statutorySetting) {
		this.statutorySetting = statutorySetting;
	}
}
