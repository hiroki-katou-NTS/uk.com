/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.flowset;

import java.util.List;

import lombok.Getter;
import nts.uk.ctx.at.shared.dom.worktime.common.StampReflectTimezone;
import nts.uk.ctx.at.shared.dom.worktime.service.WorkTimeDomainObject;

/**
 * The Class FlowStampReflectTimezone.
 */
// 流動打刻反映時間帯
@Getter
public class FlowStampReflectTimezone extends WorkTimeDomainObject {

	 /** The two times work reflect basic time. */
	// ２回目勤務の反映基準時間
 	private ReflectReferenceTwoWorkTime twoTimesWorkReflectBasicTime;

	/** The stamp reflect timezone. */
	// 打刻反映時間帯
	private List<StampReflectTimezone> stampReflectTimezones;

	/**
	 * Instantiates a new flow stamp reflect timezone.
	 *
	 * @param memento the memento
	 */
	public FlowStampReflectTimezone(FlowStampReflectTzGetMemento memento) {
		this.twoTimesWorkReflectBasicTime = memento.getTwoTimesWorkReflectBasicTime();
		this.stampReflectTimezones = memento.getStampReflectTimezone();
	}

	/**
	 * Save to memento.
	 *
	 * @param memento the memento
	 */
	public void saveToMemento(FlowStampReflectTzSetMemento memento) {
		memento.setTwoTimesWorkReflectBasicTime(this.twoTimesWorkReflectBasicTime);
		memento.setStampReflectTimezone(this.stampReflectTimezones);
	}
}
