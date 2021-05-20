/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.flowset;

import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.worktime.service.WorkTimeDomainObject;
import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 * The Class FlowCalculateSet.
 */
// 流動計算設定
@Getter
@NoArgsConstructor
public class FlowCalculateSet extends WorkTimeDomainObject implements Cloneable{
	
	/** The calc start time set. */
	// 計算開始時刻を決める設定
	private PrePlanWorkTimeCalcMethod calcStartTimeSet;

	/**
	 * Instantiates a new flow calculate set.
	 *
	 * @param memento the memento
	 */
	public FlowCalculateSet(FlowCalculateGetMemento memento) {
		this.calcStartTimeSet = memento.getCalcStartTimeSet();
	}

	/**
	 * Save to memento.
	 *
	 * @param memento the memento
	 */
	public void saveToMemento(FlowCalculateSetMemento memento) {
		memento.setCalcStartTimeSet(this.calcStartTimeSet);
	}
	
	@Override
	public FlowCalculateSet clone() {
		FlowCalculateSet cloned = new FlowCalculateSet();
		try {
			cloned.calcStartTimeSet = PrePlanWorkTimeCalcMethod.valueOf(this.calcStartTimeSet.value);
		}
		catch (Exception e){
			throw new RuntimeException("FlowCalculateSet clone error.");
		}
		return cloned;
	}
	
	/**
	 * 予定開始時刻から計算するか判断する
	 * @param recordStart 出勤時刻
	 * @param scheduleStart 予定開始時刻
	 * @return true：予定開始時刻から計算する　false：予定開始時刻から計算しない
	 */
	public boolean isCalcFromScheduleStartTime(TimeWithDayAttr recordStart, TimeWithDayAttr scheduleStart) {
		if(!this.calcStartTimeSet.isCalcFromPlanStartTime()) {
			return false; //予定開始時刻から計算しない
		}
		if(recordStart.greaterThanOrEqualTo(scheduleStart)) {
			return false; //出勤時刻>=予定開始時刻
		}
		//計算する
		return true;
	}
}
