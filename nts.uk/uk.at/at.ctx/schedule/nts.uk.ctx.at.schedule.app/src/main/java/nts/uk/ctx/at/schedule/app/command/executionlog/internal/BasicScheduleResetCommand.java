/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.app.command.executionlog.internal;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.schedule.app.command.executionlog.ScheduleCreatorExecutionCommand;
import nts.uk.ctx.at.schedule.dom.executionlog.ResetAtr;
import nts.uk.ctx.at.schedule.dom.executionlog.ScheduleCreateContent;
import nts.uk.ctx.at.schedule.dom.executionlog.ScheduleCreator;

/**
 * The Class BasicScheduleResetCommand.
 */

@Getter
public class BasicScheduleResetCommand {

	/** The execution id. */
	// 実行ID
	private final String executionId;
	
	/** The company id. */
	// 会社ID
	private final String companyId;
	
	/** The employee id. */
	// 社員ID
	private final String employeeId;
	
	/** The reset atr. */
	// 再設定区分
	private final ResetAtr resetAtr;
	
	/** The confirm. */
	// 作成時に確定済みにする
	private final Boolean confirm;
	
	/** The re create atr. */
	// 再作成区分
	private final int reCreateAtr;
	
	// 会社共通のマスタキャッシュ
	private final Object companySetting;

	/** The work type code. */
	// 勤務種類コード
	@Setter
	private String workTypeCode;
	
	/** The working code. */
	// 就業時間帯コード
	@Setter
	private String workingCode;
	
	private BasicScheduleResetCommand(
			ScheduleCreatorExecutionCommand command,
			Object companySetting,
			ScheduleCreator scheduleCreator,
			ScheduleCreateContent content) {

		this.companyId = command.getCompanyId();
		this.confirm = content.getConfirm();
		this.employeeId = scheduleCreator.getEmployeeId();
		this.executionId = command.getExecutionId();
		this.reCreateAtr = content.getReCreateContent().getReCreateAtr().value;
		this.resetAtr = content.getReCreateContent().getResetAtr();
		this.companySetting = companySetting;
	}
	
	public static BasicScheduleResetCommand create(
			ScheduleCreatorExecutionCommand command,
			Object companySetting,
			ScheduleCreator scheduleCreator,
			ScheduleCreateContent content) {
		
		return new BasicScheduleResetCommand(command, companySetting, scheduleCreator, content);
	}
	
}
