/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.app.command.executionlog.internal;

import lombok.Getter;
import lombok.Setter;

/**
 * The Class WorkTimeSetGetterCommand.
 */
@Getter
@Setter
// 「入力パラメータ」 会社ID ;勤務種類コード ; 就業時間帯コード
public class WorkTimeSetGetterCommand {

	/** The company id. */
	// 会社ID
	private String companyId;
	
	/** The work type code. */
	// 勤務種類コード
	private String worktypeCode;
	
	/** The working code. */
	// 就業時間帯コード
	private String workingCode;
}
