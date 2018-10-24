/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.app.command.executionlog.internal;

import lombok.Getter;
import lombok.Setter;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.schedule.app.command.executionlog.ScheduleCreatorExecutionCommand;
import nts.uk.ctx.at.schedule.dom.executionlog.ResetAtr;
import nts.uk.ctx.at.schedule.dom.executionlog.ScheduleCreateContent;
import nts.uk.ctx.at.schedule.dom.executionlog.ScheduleCreator;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

/**
 * The Class BasicScheduleResetCommand.
 */

@Getter
@Setter
public class BasicScheduleResetCommand {

	/** The execution id. */
	// 実行ID
	private String executionId;
	
	/** The company id. */
	// 会社ID
	private String companyId;
	
	/** The target start date. */
	// 対象開始日
	private GeneralDate targetStartDate;
	
	/** The target end date. */
	// 対象終了日
	private GeneralDate targetEndDate;
	
	/** The employee id. */
	// 社員ID
	private String employeeId;
	
	/** The reset atr. */
	// 再設定区分
	private ResetAtr resetAtr;
	
	/** The confirm. */
	// 作成時に確定済みにする
	private Boolean confirm;
	
	/** The re create atr. */
	// 再作成区分
	private int reCreateAtr;

	/** The work type code. */
	// 勤務種類コード
	private String workTypeCode;
	
	/** The working code. */
	// 就業時間帯コード
	private String workingCode;
	
	// 会社共通のマスタキャッシュ
	private Object companySetting;
	
	public static BasicScheduleResetCommand create(
			ScheduleCreatorExecutionCommand command,
			DatePeriod period,
			Object companySetting,
			ScheduleCreator scheduleCreator,
			ScheduleCreateContent content) {
		BasicScheduleResetCommand commandReset = new BasicScheduleResetCommand();
		commandReset.setCompanyId(command.getCompanyId());
		commandReset.setConfirm(content.getConfirm());
		commandReset.setEmployeeId(scheduleCreator.getEmployeeId());
		commandReset.setExecutionId(command.getExecutionId());
		commandReset.setReCreateAtr(content.getReCreateContent().getReCreateAtr().value);
		commandReset.setResetAtr(content.getReCreateContent().getResetAtr());
		commandReset.setTargetStartDate(period.start());
		commandReset.setTargetEndDate(period.end());
		commandReset.setCompanySetting(companySetting);
		return commandReset;
	}
	
}
