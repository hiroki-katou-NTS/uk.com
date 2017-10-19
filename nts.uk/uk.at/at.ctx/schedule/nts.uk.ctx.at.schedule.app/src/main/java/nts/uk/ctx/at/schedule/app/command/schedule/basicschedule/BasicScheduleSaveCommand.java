/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.app.command.schedule.basicschedule;

import lombok.Getter;
import lombok.Setter;
import nts.arc.time.GeneralDate;

/**
 * The Class BasicScheduleSaveCommand.
 */
@Getter
@Setter
public class BasicScheduleSaveCommand {

	/** The employee id. */
	// 社員ID
	private String employeeId;
	
	/** The ymd. */
	// 年月日
	private GeneralDate ymd;
	
	/** The schedule determination atr. */
	// 予定確定区分
	private int scheduleDeterminationAtr;
	
	/** The work type code. */
	// 勤務種類 
	private String worktypeCode;
	
	/** The work time code. */
	// 就業時間帯
	private String worktimeCode;
}
