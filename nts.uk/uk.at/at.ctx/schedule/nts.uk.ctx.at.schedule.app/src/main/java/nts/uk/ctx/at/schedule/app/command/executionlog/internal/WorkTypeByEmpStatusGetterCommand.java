/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.app.command.executionlog.internal;

import lombok.Getter;
import lombok.Setter;

/**
 * The Class WorkTypeByEmpStatusGetterCommand.
 */
@Getter
@Setter
// 「入力パラメータ」 会社ID; 実行ID; 社員ID; 年月日; 就業時間帯の参照先; 勤務種類コード; 就業時間帯コード
public class WorkTypeByEmpStatusGetterCommand {

	/** The employee id. */
	// 社員ID
	private String employeeId;
	
	/** The base getter. */
	//  実行ID ;会社ID ;年月日
	private ScheduleErrorLogGeterCommand baseGetter;
			
	// 就業時間帯の参照先
	private int referenceWorkingHours;
	
	/** The work type code. */
	// 勤務種類コード
	private String workTypeCode;
	
	/** The working code. */
	// 就業時間帯コード
	private String workingCode;
	
	/**
	 * To worktime.
	 *
	 * @return the work time by emp status getter command
	 */
	public WorkTimeConvertCommand toWorktimeConvert(){
		WorkTimeConvertCommand command = new WorkTimeConvertCommand();
		command.setBaseGetter(this.baseGetter);
		command.setEmployeeId(this.employeeId);
		command.setReferenceWorkingHours(this.referenceWorkingHours);
		command.setWorkingCode(this.workingCode);
		command.setWorkTypeCode(this.workTypeCode);
		return command;
	}
}
