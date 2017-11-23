/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.app.command.executionlog.internal;

import lombok.Getter;
import lombok.Setter;

/**
 * The Class WorkTypeGetterCommand.
 */
// 「パラメータ」 実行ID; 会社ID; 社員ID; 営業日カレンダーの参照先; 基本勤務の参照先; 就業時間帯の参照先
@Getter
@Setter
public class WorkTypeGetterCommand {

	/** The employee id. */
	// 社員ID
	private String employeeId;
	
	/** The base getter. */
	//  実行ID ;会社ID ;年月日
	private ScheduleErrorLogGeterCommand baseGetter;
	
	/** The reference business day calendar. */
	// 営業日カレンダーの参照先
	private int referenceBusinessDayCalendar;
		
	/** The reference basic work. */
	// 基本勤務の参照先
	private int referenceBasicWork;
	
	/** The reference working hours. */
	// 就業時間帯の参照先
	private int referenceWorkingHours;
	
	/**
	 * To basic work setting.
	 *
	 * @return the basic work setting getter command
	 */
	public BasicWorkSettingGetterCommand toBasicWorkSetting(){
		BasicWorkSettingGetterCommand command = new BasicWorkSettingGetterCommand();
		command.setEmployeeId(this.employeeId);
		command.setBaseGetter(this.baseGetter);
		command.setReferenceBusinessDayCalendar(this.referenceBusinessDayCalendar);
		command.setReferenceBasicWork(this.referenceBasicWork);
		return command;
	}
	
	
	/**
	 * To work time employment status.
	 *
	 * @return the work time by emp status getter command
	 */
	public WorkTimeConvertCommand toWorkTimeConvert() {
		WorkTimeConvertCommand command = new WorkTimeConvertCommand();
		command.setBaseGetter(this.baseGetter);
		command.setEmployeeId(this.employeeId);
		command.setReferenceWorkingHours(this.referenceWorkingHours);
		return command;
	}
	
	/**
	 * To work time.
	 *
	 * @return the work time getter command
	 */
	public WorkTimeGetterCommand toWorkTime() {
		WorkTimeGetterCommand command = new WorkTimeGetterCommand();
		command.setBaseGetter(this.baseGetter);
		command.setEmployeeId(this.employeeId);
		command.setReferenceBasicWork(this.referenceBasicWork);
		command.setReferenceBusinessDayCalendar(this.referenceBusinessDayCalendar);
		command.setReferenceWorkingHours(this.referenceWorkingHours);
		return command;
	}
	
	/**
	 * To work type employment status.
	 *
	 * @return the work type by emp status getter command
	 */
	public WorkTypeByEmpStatusGetterCommand toWorkTypeEmploymentStatus() {
		WorkTypeByEmpStatusGetterCommand command = new WorkTypeByEmpStatusGetterCommand();
		command.setBaseGetter(this.baseGetter);
		command.setEmployeeId(this.employeeId);
		command.setReferenceWorkingHours(this.referenceWorkingHours);
		return command;
	}
		
}
