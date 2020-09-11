/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.app.command.executionlog.internal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * The Class WorkdayAttrByClassGetterCommand.
 */
// 「入力パラメータ」 実行ID ;会社ID ;社員ID ;年月日 ;分類コード
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class WorkdayAttrByClassGetterCommand {
	
	/** The employee id. */
	// 社員ID
	private String employeeId;
	
	/** The base getter. */
	//  実行ID ;会社ID ;年月日
	private ScheduleErrorLogGeterCommand baseGetter;
	
	/** The classification code. */
	// 分類コード
	private String classificationCode;
	
}
