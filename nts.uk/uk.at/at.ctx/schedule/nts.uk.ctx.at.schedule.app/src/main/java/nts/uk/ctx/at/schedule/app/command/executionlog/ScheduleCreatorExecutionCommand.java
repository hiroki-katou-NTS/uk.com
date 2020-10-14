/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.app.command.executionlog;

import java.util.List;
import java.util.concurrent.CountDownLatch;

import lombok.Getter;
import lombok.Setter;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.schedule.app.command.executionlog.internal.ScheduleErrorLogGeterCommand;
import nts.uk.ctx.at.schedule.dom.executionlog.CreationMethodClassification;
import nts.uk.ctx.at.schedule.dom.executionlog.ScheduleCreateContent;
import nts.uk.ctx.at.schedule.dom.executionlog.ScheduleExecutionLog;

/**
 * The Class ScheduleCreatorExecutionCommand.
 */
public class ScheduleCreatorExecutionCommand {
	
	/** The employee id. */
	private String employeeId;
	
	/** The execution id. */
	private String executionId;
	
	/** The company id. */
	private String companyId;
	
	/** The content. */
	private ScheduleCreateContent content;
	
	/** 作成方法区分 - đang để tạm*/
	private CreationMethodClassification classification;
	
	/** The is confirm. */
	private Boolean confirm; 
	
	/** The is delete befor insert. */
//	private Boolean isDeleteBeforInsert;

	private ScheduleExecutionLog scheduleExecutionLog;

	private PersonalSchedule personalSchedule;

	private boolean isAutomatic;

	private List<String> employeeIds;
	
	@Getter
	@Setter
	private CountDownLatch countDownLatch;
	
	@Getter
	@Setter
	private Boolean isExForKBT = false;
	
	@Setter
	@Getter
	private Object companySetting;
	
	// 「更新処理自動実行」.実行種別
	private Boolean isReExecution; 
	
	// 「更新処理自動実行」.再実行条件.異動者を再作成する
	private Boolean recreateTransfer;
	
	// 「更新処理自動実行」.実行種別.休職者・休業者を再作成
 
	public String getEmployeeId() {
		return employeeId;
	}



	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}



	public String getExecutionId() {
		return executionId;
	}



	public void setExecutionId(String executionId) {
		this.executionId = executionId;
	}



	public CreationMethodClassification getClassification() {
		return classification;
	}



	public void setClassification(CreationMethodClassification classification) {
		this.classification = classification;
	}
	
	public String getCompanyId() {
		return companyId;
	}



	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}


	public ScheduleCreateContent getContent() {
		return content;
	}



	public void setContent(ScheduleCreateContent content) {
		this.content = content;
	}



	public Boolean getConfirm() {
		return confirm;
	}



	public void setConfirm(Boolean confirm) {
		this.confirm = confirm;
	}


//	public Boolean getIsDeleteBeforInsert() {
//		return isDeleteBeforInsert;
//	}



//	public void setIsDeleteBeforInsert(Boolean isDeleteBeforInsert) {
//		this.isDeleteBeforInsert = isDeleteBeforInsert;
//	}



	public ScheduleExecutionLog getScheduleExecutionLog() {
		return scheduleExecutionLog;
	}



	public void setScheduleExecutionLog(ScheduleExecutionLog scheduleExecutionLog) {
		this.scheduleExecutionLog = scheduleExecutionLog;
	}



	public PersonalSchedule getPersonalSchedule() {
		return personalSchedule;
	}



	public void setPersonalSchedule(PersonalSchedule personalSchedule) {
		this.personalSchedule = personalSchedule;
	}



	public boolean isAutomatic() {
		return isAutomatic;
	}



	public void setAutomatic(boolean isAutomatic) {
		this.isAutomatic = isAutomatic;
	}



	public List<String> getEmployeeIds() {
		return employeeIds;
	}



	public void setEmployeeIds(List<String> employeeIds) {
		this.employeeIds = employeeIds;
	}

	public void setIsReExecution(Boolean isReExecution) {
		this.isReExecution = isReExecution;
	}
	
	public boolean getIsReExecution() {
		return this.isReExecution;
	}

	public void setRecreateTransfer(Boolean recreateTransfer) {
		this.recreateTransfer = recreateTransfer;
	}
	
	public boolean getRecreateTransfer() {
		return this.recreateTransfer;
	}

	/**
	 * To base command.
	 *
	 * @return the schedule error log geter command
	 */
	public ScheduleErrorLogGeterCommand toBaseCommand(GeneralDate date) {
		ScheduleErrorLogGeterCommand command = new ScheduleErrorLogGeterCommand();
		command.setCompanyId(this.companyId);
		command.setExecutionId(this.executionId);
		command.setToDate(date);
		return command;
	}
}
