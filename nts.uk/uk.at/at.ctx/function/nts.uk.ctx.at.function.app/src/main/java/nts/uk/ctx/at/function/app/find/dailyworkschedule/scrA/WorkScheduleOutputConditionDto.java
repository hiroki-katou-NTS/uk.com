/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.function.app.find.dailyworkschedule.scrA;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.function.app.find.dailyworkschedule.FreeSettingOfOutputItemForDailyWorkScheduleDto;
import nts.uk.ctx.at.function.app.find.dailyworkschedule.OutputStandardSettingOfDailyWorkScheduleDto;


/**
 * Sets the lst output item daily work schedule.
 *
 * @param lstOutputItemDailyWorkSchedule the new lst output item daily work schedule
 * @author HoangDD
 */
@Setter
@Getter
@NoArgsConstructor
public class WorkScheduleOutputConditionDto {
	
	/** The start date. */
	private GeneralDate startDate;
	
	/** The end date. */
	private GeneralDate endDate;
	
	/** The str return. */
	private String strReturn;
	
	/** The employee charge. */
	private boolean employeeCharge;
	
	private String msgErrClosingPeriod;
	
	private int selectionType;
	
	/** The standard setting. */
	private OutputStandardSettingOfDailyWorkScheduleDto standardSetting;
	
	/** The free setting. */
	private FreeSettingOfOutputItemForDailyWorkScheduleDto freeSetting;
	
	private boolean configFreeSetting;

	/**
	 * Instantiates a new work schedule output condition dto.
	 *
	 * @param startDate the start date
	 * @param endDate the end date
	 * @param strReturn the str return
	 * @param lstOutputItemDailyWorkSchedule the lst output item daily work schedule
	 * @param employeeCharge the employee charge
	 * @param msgErrClosingPeriod the msg err closing period
	 */
	public WorkScheduleOutputConditionDto(GeneralDate startDate
			, GeneralDate endDate
			, String strReturn
			, OutputStandardSettingOfDailyWorkScheduleDto standardSetting
			, FreeSettingOfOutputItemForDailyWorkScheduleDto freeSetting
			, boolean employeeCharge
			, String msgErrClosingPeriod) {
		super();
		this.startDate = startDate;
		this.endDate = endDate;
		this.strReturn = strReturn;
		this.standardSetting = standardSetting;
		this.freeSetting = freeSetting;
		this.employeeCharge = employeeCharge;
		this.msgErrClosingPeriod = msgErrClosingPeriod;
	}
}


