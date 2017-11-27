/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.app.command.executionlog.internal;

import lombok.Getter;
import lombok.Setter;

/**
 * The Class WorkTimeGetterCommand.
 */
// 「パラメータ」 実行ID;会社ID ;社員ID; ループ中の対象日;営業日カレンダーの参照先; 基本勤務の参照先 ; 就業時間帯の参照先; 取得した勤務種類コード
@Getter
@Setter
public class WorkTimeGetterCommand {

	/** The employee id. */
	// 社員ID
	private String employeeId;
	
	/** The base getter. */
	//  実行ID ;会社ID ;年月日
	private ScheduleErrorLogGeterCommand baseGetter;
	
	// 就業時間帯の参照先
	private int referenceWorkingHours;
	
	/** The reference business day calendar. */
	// 営業日カレンダーの参照先
	private int referenceBusinessDayCalendar;
	
	/** The work type code. */
	// 取得した勤務種類コード
	private String workTypeCode;
	
	/** The reference basic work. */
	// 基本勤務の参照先
	private int referenceBasicWork;
	
	/**
	 * To basic work setting.
	 *
	 * @return the basic work setting getter command
	 */
	public BasicWorkSettingGetterCommand toBasicWorkSetting(){
		BasicWorkSettingGetterCommand command = new BasicWorkSettingGetterCommand();
		command.setBaseGetter(this.baseGetter);
		command.setEmployeeId(this.employeeId);
		command.setReferenceBasicWork(this.referenceBasicWork);
		command.setReferenceBusinessDayCalendar(this.referenceBusinessDayCalendar);
		return command;
	}
	
	/**
	 * To work time zone.
	 *
	 * @return the work time zone getter command
	 */
	public WorkTimeZoneGetterCommand toWorkTimeZone(){
		WorkTimeZoneGetterCommand command = new WorkTimeZoneGetterCommand();
		command.setBaseGetter(this.baseGetter);
		command.setEmployeeId(this.employeeId);
		command.setReferenceWorkingHours(this.referenceWorkingHours);
		command.setWorkTypeCode(this.workTypeCode);
		return command;
	}
}
