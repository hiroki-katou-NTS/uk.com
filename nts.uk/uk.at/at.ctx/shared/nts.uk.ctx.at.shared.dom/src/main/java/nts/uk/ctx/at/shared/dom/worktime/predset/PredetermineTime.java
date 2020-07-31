/******************************************************************
 * Copyright (c) 2018 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.predset;

import java.util.Optional;
import java.io.Serializable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeOfExistMinus;
import nts.uk.ctx.at.shared.dom.worktime.service.WorkTimeDomainObject;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.ScreenMode;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeDivision;

/**
 * The Class PredetermineTime.
 */
@Getter
@NoArgsConstructor
// 所定時間
public class PredetermineTime extends WorkTimeDomainObject implements Cloneable, Serializable{

	/** Serializable */
	private static final long serialVersionUID = 1L;

	/** The add time. */
	// 就業加算時間
	private BreakDownTimeDay addTime;

	/** The pred time. */
	// 所定時間
	private BreakDownTimeDay predTime;

	/**
	 * Instantiates a new predetermine time.
	 *
	 * @param addTime
	 *            the add time
	 * @param predTime
	 *            the pred time
	 */
	public PredetermineTime(BreakDownTimeDay addTime, BreakDownTimeDay predTime) {
		super();
		this.addTime = addTime;
		this.predTime = predTime;
	}

	/**
	 * Instantiates a new predetermine time.
	 *
	 * @param memento
	 *            the memento
	 */
	public PredetermineTime(PredetermineTimeGetMemento memento) {
		this.addTime = memento.getAddTime();
		this.predTime = memento.getPredTime();
	}

	/**
	 * Save to memento.
	 *
	 * @param memento
	 *            the memento
	 */
	public void saveToMemento(PredetermineTimeSetMemento memento) {
		memento.setAddTime(this.addTime);
		memento.setPredTime(this.predTime);
	}

	/**
	 * Correct data.
	 *
	 * @param screenMode
	 *            the screen mode
	 * @param workTimeType
	 *            the work time type
	 * @param oldDomain
	 *            the old domain
	 */
	public void correctData(ScreenMode screenMode, WorkTimeDivision workTimeType, PredetermineTime oldDomain) {
		if (screenMode == ScreenMode.SIMPLE) {
			// Simple mode
		}
	}

	/**
	 * Correct default data.
	 *
	 * @param screenMode the screen mode
	 * @param workTimeType the work time type
	 */
	public void correctDefaultData(ScreenMode screenMode, WorkTimeDivision workTimeType) {
		if (screenMode == ScreenMode.SIMPLE) {
			// Simple mode
		} 		
	}
	
	/**
	 * 所定時間を変動させる（流動勤務）
	 * @param fluctuationTime 変動させる時間
	 */
	public void fluctuationPredeterminedTimeForFlow(AttendanceTimeOfExistMinus fluctuationTime) {
		this.predTime.setAllTime(
				Optional.of(this.predTime.getOneDay().addMinutes(fluctuationTime.v())),
				Optional.of(this.predTime.getMorning().addMinutes(fluctuationTime.v())),
				Optional.of(this.predTime.getAfternoon().addMinutes(fluctuationTime.v())));
		this.addTime.setAllTime(
				Optional.of(this.addTime.getOneDay().addMinutes(fluctuationTime.v())),
				Optional.empty(),
				Optional.empty());
		
		this.predTime.negativeToZero();
		this.addTime.negativeToZero();
	}
	
	@Override
	public PredetermineTime clone() {
		PredetermineTime cloned = new PredetermineTime();
		try {
			cloned.addTime = this.addTime.clone();
			cloned.predTime = this.predTime.clone();
		}
		catch (Exception e){
			throw new RuntimeException("PredetemineTimeSetting clone error.");
		}
		return cloned;
	}

}
