/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.app.command.executionlog.output;

import lombok.Getter;
import lombok.Setter;

/**
 * The Class WorktypeOutput.
 */
@Getter
@Setter
public class WorktypeOutput {
	
	/** The worktype code. */
	// 勤務種類コード
	private String worktypeCode;
	
	// 打刻の扱い方
	/** The handle embossing. */
	private Object handleEmbossing;

}
