/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.bs.employee.dom.holidaymanagement.employment;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.bs.employee.dom.holidaymanagement.PublicHolidayMonthSetting;
import nts.uk.ctx.bs.employee.dom.holidaymanagement.Year;

@Getter
@Setter
// 雇用月間日数設定
public class EmploymentMonthDaySetting extends AggregateRoot{
	
	/** The company ID. */
	// 会社ID
	private String companyID;
	
	/** The management year. */
	// 管理年度
	private Year managementYear;
	
	/** The employment code. */
	// 雇用コード
	private String employmentCode;
	
	/** The public holiday month settings. */
	// 月間公休日数
	private List<PublicHolidayMonthSetting> publicHolidayMonthSettings;
}
