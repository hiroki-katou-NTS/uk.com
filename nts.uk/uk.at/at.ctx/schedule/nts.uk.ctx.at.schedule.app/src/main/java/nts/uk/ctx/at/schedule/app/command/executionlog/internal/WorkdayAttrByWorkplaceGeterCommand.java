/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.app.command.executionlog.internal;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * The Class WorkdayAttrByWorkplaceGeterCommand.
 */
// 「入力パラメータ」 実行ID; 会社ID; 社員ID; 年月日; 職場ID（List）
@Getter
@Setter
public class WorkdayAttrByWorkplaceGeterCommand {
	
	/** The employee id. */
	// 社員ID
	private String employeeId;
	
	/** The base getter. */
	//  実行ID ;会社ID ;年月日
	private ScheduleErrorLogGeterCommand baseGetter;
	
	/** The workplace ids. */
	// 職場ID（List）
	private List<String> workplaceIds;
	
}
