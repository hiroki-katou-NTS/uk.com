/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.bs.employee.dom.holidaymanagement.workplace;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.bs.employee.dom.holidaymanagement.PublicHolidayMonthSetting;
import nts.uk.ctx.bs.employee.dom.holidaymanagement.Year;

@Getter
@Setter
// 職場月間日数設定
public class WorkplaceMonthDaySetting extends AggregateRoot{
	
	/** The cid. */
	// 会社ID
	private String CID;
	
	/** The management year. */
	// 管理年度
	private Year managementYear;
	
	/** The workplace ID. */
	// 職場ID
	private String workplaceID;
	
	/** The public holiday month settings. */
	// 月間公休日数
	private List<PublicHolidayMonthSetting> publicHolidayMonthSettings;
}
