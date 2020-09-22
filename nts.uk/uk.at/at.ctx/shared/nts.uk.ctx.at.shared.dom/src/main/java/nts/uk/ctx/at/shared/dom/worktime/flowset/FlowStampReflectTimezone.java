/******************************************************************
 * Copyright (c) 2018 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.flowset;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.apache.commons.lang3.tuple.ImmutablePair;

import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.temporarytime.WorkNo;
import nts.uk.ctx.at.shared.dom.worktime.common.GoLeavingWorkAtr;
import nts.uk.ctx.at.shared.dom.worktime.common.StampReflectTimezone;
import nts.uk.ctx.at.shared.dom.worktime.service.WorkTimeDomainObject;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.ScreenMode;

/**
 * The Class FlowStampReflectTimezone.
 */
// 流動打刻反映時間帯
@Getter
@NoArgsConstructor
public class FlowStampReflectTimezone extends WorkTimeDomainObject implements Cloneable{

	/** The two times work reflect basic time. */
	// ２回目勤務の反映基準時間
	private ReflectReferenceTwoWorkTime twoTimesWorkReflectBasicTime;

	/** The stamp reflect timezones. */
	// 打刻反映時間帯
	private List<StampReflectTimezone> stampReflectTimezones;

	/**
	 * Instantiates a new flow stamp reflect timezone.
	 *
	 * @param memento
	 *            the memento
	 */
	public FlowStampReflectTimezone(FlowStampReflectTzGetMemento memento) {
		this.twoTimesWorkReflectBasicTime = memento.getTwoTimesWorkReflectBasicTime();
		this.stampReflectTimezones = memento.getStampReflectTimezone();
	}

	/**
	 * Save to memento.
	 *
	 * @param memento
	 *            the memento
	 */
	public void saveToMemento(FlowStampReflectTzSetMemento memento) {
		memento.setTwoTimesWorkReflectBasicTime(this.twoTimesWorkReflectBasicTime);
		memento.setStampReflectTimezone(this.stampReflectTimezones);
	}

	/**
	 * Correct data.
	 *
	 * @param screenMode
	 *            the screen mode
	 * @param oldDomain
	 *            the old domain
	 */
	public void correctData(ScreenMode screenMode, FlowStampReflectTimezone oldDomain) {
		Map<Entry<WorkNo, GoLeavingWorkAtr>, StampReflectTimezone> mapStampReflectTimezone = oldDomain
				.getStampReflectTimezones().stream()
				.collect(Collectors.toMap(
						item -> new ImmutablePair<WorkNo, GoLeavingWorkAtr>(item.getWorkNo(), item.getClassification()),
						Function.identity()));
		this.stampReflectTimezones.forEach(item -> item.correctData(screenMode, mapStampReflectTimezone
				.get(new ImmutablePair<WorkNo, GoLeavingWorkAtr>(item.getWorkNo(), item.getClassification()))));
	}

	/**
	 * Correct default data.
	 *
	 * @param screenMode
	 *            the screen mode
	 */
	public void correctDefaultData(ScreenMode screenMode) {
		this.stampReflectTimezones.forEach(item -> item.correctDefaultData(screenMode));
	}
	
	@Override
	public FlowStampReflectTimezone clone() {
		FlowStampReflectTimezone cloned = new FlowStampReflectTimezone();
		try {
			cloned.twoTimesWorkReflectBasicTime = new ReflectReferenceTwoWorkTime(this.twoTimesWorkReflectBasicTime.v());
			cloned.stampReflectTimezones = this.stampReflectTimezones.stream().map(c -> c.clone()).collect(Collectors.toList());
		}
		catch (Exception e){
			throw new RuntimeException("FlowStampReflectTimezone clone error.");
		}
		return cloned;
	}
}
