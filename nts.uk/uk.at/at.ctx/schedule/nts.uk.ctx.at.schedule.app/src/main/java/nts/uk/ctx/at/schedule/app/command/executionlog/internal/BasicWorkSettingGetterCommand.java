/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.app.command.executionlog.internal;

import lombok.Getter;
import lombok.Setter;

/**
 * The Class BasicWorkSettingGetterCommand.
 */
// 「入力パラメータ」 ;実行ID ;会社ID ;社員ID ;稼働日区分 ;基本勤務の参照先; 営業日カレンダーの参照先
@Getter
@Setter
public class BasicWorkSettingGetterCommand {
	
	/** The employee id. */
	// 社員ID
	private String employeeId;
	
	/** The base getter. */
	//  実行ID ;会社ID ;年月日
	private ScheduleErrorLogGeterCommand baseGetter;
	
	/** The reference business day calendar. */
	// 営業日カレンダーの参照先
	private int referenceBusinessDayCalendar;
	
	/** The workday division. */
	// 稼働日区分
	private int workdayDivision;
	
	/** The reference basic work. */
	// 基本勤務の参照先
	private int referenceBasicWork;
	
	
	/**
	 * To command work place.
	 *
	 * @return the work day attr by workplace geter command
	 */
	public WorkdayAttrByWorkplaceGeterCommand toCommandWorkplace(){
		WorkdayAttrByWorkplaceGeterCommand command = new WorkdayAttrByWorkplaceGeterCommand();
		command.setBaseGetter(this.baseGetter);
		command.setEmployeeId(this.employeeId);
		return command;
	}
	
	/**
	 * To command classification.
	 *
	 * @return the work day attr by class geter command
	 */
	public WorkdayAttrByClassGetterCommand toCommandClassification(){
		WorkdayAttrByClassGetterCommand command = new WorkdayAttrByClassGetterCommand();
		command.setBaseGetter(this.baseGetter);
		command.setEmployeeId(this.employeeId);
		return command;
	}
	
	/**
	 * To basic workplace.
	 *
	 * @return the basic work setting by workplace getter command
	 */
	public BasicWorkSettingByWorkplaceGetterCommand toBasicWorkplace(){
		BasicWorkSettingByWorkplaceGetterCommand command = new BasicWorkSettingByWorkplaceGetterCommand();
		command.setBaseGetter(this.baseGetter);
		command.setEmployeeId(this.employeeId);
		command.setWorkdayDivision(this.workdayDivision);
		return command;
	}
	
	/**
	 * To basic classification.
	 *
	 * @return the basic work setting by classification getter command
	 */
	public BasicWorkSettingByClassificationGetterCommand toBasicClassification(){
		BasicWorkSettingByClassificationGetterCommand command = new BasicWorkSettingByClassificationGetterCommand();
		command.setBaseGetter(this.baseGetter);
		command.setEmployeeId(this.employeeId);
		command.setWorkdayDivision(this.workdayDivision);
		return command;
	}
	
	
}
