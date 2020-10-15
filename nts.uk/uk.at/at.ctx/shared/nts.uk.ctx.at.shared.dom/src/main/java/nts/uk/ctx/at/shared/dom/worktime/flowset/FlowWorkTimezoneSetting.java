/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.flowset;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeOfExistMinus;
import nts.uk.ctx.at.shared.dom.common.timerounding.TimeRoundingSetting;
import nts.uk.ctx.at.shared.dom.worktime.service.WorkTimeDomainObject;

/**
 * The Class FlowWorkTimezoneSetting.
 */
// 流動勤務時間帯設定
@Getter
@NoArgsConstructor
public class FlowWorkTimezoneSetting extends WorkTimeDomainObject implements Cloneable{

	/** The work time rounding. */
	// 就業時間丸め
	private TimeRoundingSetting workTimeRounding;

	/** The lst OT timezone. */
	// 残業時間帯
	private List<FlowOTTimezone> lstOTTimezone;

	/**
	 * Instantiates a new fl wtz setting.
	 *
	 * @param memento the memento
	 */
	public FlowWorkTimezoneSetting(FlWtzSettingGetMemento memento) {
		this.workTimeRounding = memento.getWorkTimeRounding();
		this.lstOTTimezone = memento.getLstOTTimezone();
	}

	/**
	 * Save to memento.
	 *
	 * @param memento the memento
	 */
	public void saveToMemento(FlWtzSettingSetMemento memento) {
		memento.setWorkTimeRounding(this.workTimeRounding);
		memento.setLstOTTimezone(this.lstOTTimezone);
	}

	/* (non-Javadoc)
	 * @see nts.arc.layer.dom.DomainObject#validate()
	 */
	@Override
	public void validate() {
		this.validateOverlapElapsedTime();
		super.validate();
	}
	
	/**
	 * Validate overlap elapsed time.
	 */
	private void validateOverlapElapsedTime() {
		// Validate flowRestSets.flowPassageTime must not be duplicated
		Set<AttendanceTime> setFlowPassageTime = this.lstOTTimezone.stream()
				.map(flowOTTimezone -> flowOTTimezone.getFlowTimeSetting().getElapsedTime())
				.collect(Collectors.toSet());
		// If Set size < List size => there're duplicated value
		if (setFlowPassageTime.size() < this.lstOTTimezone.size()) {
			this.bundledBusinessExceptions.addMessage("Msg_869");
		}
	}
	
	/**
	 * 残業枠Noに一致する流動残業時間帯を取得する
	 * @param workNo
	 * @return
	 */
	public Optional<FlowOTTimezone> getMatchWorkNoOverTimeWorkSheet(int overTimeFrameNo) {
		List<FlowOTTimezone> timeSheet = this.lstOTTimezone.stream().filter(tc -> tc.getOTFrameNo().v().intValue() == overTimeFrameNo).collect(Collectors.toList());
		if(timeSheet.size()>1) {
			throw new RuntimeException("Exist duplicate overTimeFrameNo : " + overTimeFrameNo);
		}
		if(timeSheet.isEmpty()) {
			return Optional.empty();
		}
		return Optional.of(timeSheet.get(0));
	}
	
	/**
	 * Correct default data.
	 */
	public void correctDefaultData() { 
		this.lstOTTimezone.forEach(item -> item.correctDefaultData());
	}
	
	@Override
	public FlowWorkTimezoneSetting clone() {
		FlowWorkTimezoneSetting cloned = new FlowWorkTimezoneSetting();
		try {
			cloned.workTimeRounding = this.workTimeRounding.clone();
			cloned.lstOTTimezone = this.lstOTTimezone.stream().map(c -> c.clone()).collect(Collectors.toList());
		}
		catch (Exception e){
			throw new RuntimeException("FlowWorkTimezoneSetting clone error.");
		}
		return cloned;
	}
	
	/**
	 * 残業時間帯を変動させる
	 * @param fluctuationTime 変動させる時間
	 */
	public void fluctuationElapsedTimeInLstOTTimezone(AttendanceTimeOfExistMinus fluctuationTime) {
		for(int i=0; i<this.lstOTTimezone.size(); i++) {
			this.lstOTTimezone.get(i).getFlowTimeSetting().fluctuationElapsedTimeNegativeToZero(fluctuationTime);
		}
	}
}
