/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.dom.executionlog;

import lombok.Getter;
import nts.arc.layer.dom.DomainObject;

/**
 * The Class ResetAtr.
 */
// 再設定区分
@Getter
public class ResetAtr extends DomainObject {

	// 勤務開始・終了時刻を再設定
	private Boolean resetWorkingHours;

	// 休憩開始・終了時刻を再設定
	private Boolean resetStartEndTime;

	// マスタ情報を再設定
	private Boolean resetMasterInfo;

	// 申し送り時間を再設定
	private Boolean resetTimeAssignment;

	public ResetAtr() {
	}
	
	/**
	 * To domain.
	 *
	 * @param memento
	 *            the memento
	 * @return the reset atr
	 */
	public ResetAtr(ScheduleCreateContentGetMemento memento) {
		//TODO Sua domain: スケジュール作成内容 se tiep tuc khi co tai lieu moi cua man ksc001
//		this.resetMasterInfo = memento.getResetMasterInfo();
//		this.resetWorkingHours = memento.getResetWorkingHours();
//		this.resetStartEndTime = memento.getResetStartEndTime();
//		this.resetTimeAssignment = memento.getResetTimeAssignment();
	}

	/**
	 * Save to memento.
	 *
	 * @param memento
	 *            the memento
	 */
	public void saveToMemento(ScheduleCreateContentSetMemento memento) {
		//TODO Sua domain: スケジュール作成内容 se tiep tuc khi co tai lieu moi cua man ksc001
//		memento.setResetMasterInfo(this.resetMasterInfo);
//		memento.setResetWorkingHours(this.resetWorkingHours);
//		memento.setResetTimeAssignment(this.resetTimeAssignment);
//		memento.setResetStartEndTime(this.resetStartEndTime);
	}

	public void setResetWorkingHours(Boolean resetWorkingHours) {
		this.resetWorkingHours = resetWorkingHours;
	}

	public void setResetStartEndTime(Boolean resetStartEndTime) {
		this.resetStartEndTime = resetStartEndTime;
	}

	public void setResetMasterInfo(Boolean resetMasterInfo) {
		this.resetMasterInfo = resetMasterInfo;
	}

	public void setResetTimeAssignment(Boolean resetTimeAssignment) {
		this.resetTimeAssignment = resetTimeAssignment;
	}
	
}
