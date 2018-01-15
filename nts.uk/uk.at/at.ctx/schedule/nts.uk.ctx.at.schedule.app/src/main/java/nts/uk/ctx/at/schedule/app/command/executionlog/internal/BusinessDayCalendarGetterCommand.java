/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.app.command.executionlog.internal;

import lombok.Getter;
import lombok.Setter;

/**
 * The Class BusinessDayCalendarGetterCommand.
 */
// 「入力パラメータ」 実行ID ;会社ID ;社員ID ;年月日 ;営業日カレンダーの参照先 
@Getter
@Setter
public class BusinessDayCalendarGetterCommand {

	/** The employee id. */
	// 社員ID
	private String employeeId;
	
	/** The base getter. */
	//  実行ID ;会社ID ;年月日
	private ScheduleErrorLogGeterCommand baseGetter;
}
