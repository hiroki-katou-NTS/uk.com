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

	/** The reset master info. */
	// マスタ情報再設定
	private Boolean resetMasterInfo;

	/** The reset absent holiday business. */
	// 休職休業再設定
	private Boolean resetAbsentHolidayBusines;

	/** The reset working hours. */
	// 就業時間帯再設定
	private Boolean resetWorkingHours;

	/** The reset time assignment. */
	// 申し送り時間再設定
	private Boolean resetTimeAssignment;

	/** The reset direct line bounce. */
	// 直行直帰再設定
	private Boolean resetDirectLineBounce;

	/** The reset time child care. */
	// 育児介護時間再設定
	private Boolean resetTimeChildCare;

	/**
	 * To domain.
	 *
	 * @param memento the memento
	 * @return the reset atr
	 */
	public ResetAtr(ScheduleCreateContentGetMemento memento) {
		this.resetMasterInfo = memento.getResetMasterInfo();
		this.resetAbsentHolidayBusines = memento.getResetAbsentHolidayBusines();
		this.resetWorkingHours = memento.getResetWorkingHours();
		this.resetTimeAssignment = memento.getResetTimeAssignment();
		this.resetDirectLineBounce = memento.getResetDirectLineBounce();
		this.resetTimeChildCare = memento.getResetTimeChildCare();
	}

	/**
	 * Save to memento.
	 *
	 * @param memento the memento
	 */
	public void saveToMemento(ScheduleCreateContentSetMemento memento) {
		memento.setResetMasterInfo(this.resetMasterInfo);
		memento.setResetAbsentHolidayBusines(this.resetAbsentHolidayBusines);
		memento.setResetWorkingHours(this.resetWorkingHours);
		memento.setResetTimeAssignment(this.resetTimeAssignment);
		memento.setResetDirectLineBounce(this.resetDirectLineBounce);
		memento.setResetTimeChildCare(this.resetTimeChildCare);
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

	public ResetAtr() {
	}
	
	

}
