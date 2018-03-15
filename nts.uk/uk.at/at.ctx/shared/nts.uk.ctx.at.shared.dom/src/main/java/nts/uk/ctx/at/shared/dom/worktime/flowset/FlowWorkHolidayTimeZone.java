/******************************************************************
 * Copyright (c) 2018 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.flowset;

import lombok.Getter;
import nts.uk.ctx.at.shared.dom.worktime.common.BreakFrameNo;
import nts.uk.ctx.at.shared.dom.worktime.service.WorkTimeDomainObject;

/**
 * The Class FlowWorkHolidayTimeZone.
 */
//流動休出時間帯

/**
 * Gets the flow time setting.
 *
 * @return the flow time setting
 */
@Getter
public class FlowWorkHolidayTimeZone extends WorkTimeDomainObject {

	/** The worktime no. */
	// 就業時間帯NO
	private Integer worktimeNo;

	/** The use in legal break restrict time. */
	// 法定内休出を拘束時間として扱う
	private boolean useInLegalBreakRestrictTime;

	/** The in legal break frame no. */
	// 法定内休出枠NO
	private BreakFrameNo inLegalBreakFrameNo;

	/** The use out legal break restrict time. */
	// 法定外休出を拘束時間として扱う
	private boolean useOutLegalBreakRestrictTime;

	/** The out legal break frame no. */
	// 法定外休出枠NO
	private BreakFrameNo outLegalBreakFrameNo;

	/** The use out legal pub hol restrict time. */
	// 法定外祝日を拘束時間として扱う
	private boolean useOutLegalPubHolRestrictTime;

	/** The out legal pub hol frame no. */
	// 法定外祝日枠NO
	private BreakFrameNo outLegalPubHolFrameNo;

	/** The flow time setting. */
	// 流動時間設定
	private FlowTimeSetting flowTimeSetting;

	/**
	 * Instantiates a new flow work holiday time zone.
	 *
	 * @param memento
	 *            the memento
	 */
	public FlowWorkHolidayTimeZone(FlWorkHdTzGetMemento memento) {
		this.worktimeNo = memento.getWorktimeNo();
		this.useInLegalBreakRestrictTime = memento.getUseInLegalBreakRestrictTime();
		this.inLegalBreakFrameNo = memento.getInLegalBreakFrameNo();
		this.useOutLegalBreakRestrictTime = memento.getUseOutLegalBreakRestrictTime();
		this.outLegalBreakFrameNo = memento.getOutLegalBreakFrameNo();
		this.useOutLegalPubHolRestrictTime = memento.getUseOutLegalPubHolRestrictTime();
		this.outLegalPubHolFrameNo = memento.getOutLegalPubHolFrameNo();
		this.flowTimeSetting = memento.getFlowTimeSetting();
	}

	/**
	 * Save to memento.
	 *
	 * @param memento
	 *            the memento
	 */
	public void saveToMemento(FlWorkHdTzSetMemento memento) {
		memento.setWorktimeNo(this.worktimeNo);
		memento.setUseInLegalBreakRestrictTime(this.useInLegalBreakRestrictTime);
		memento.setInLegalBreakFrameNo(this.inLegalBreakFrameNo);
		memento.setUseOutLegalBreakRestrictTime(this.useOutLegalBreakRestrictTime);
		memento.setOutLegalBreakFrameNo(this.outLegalBreakFrameNo);
		memento.setUseOutLegalPubHolRestrictTime(this.useOutLegalPubHolRestrictTime);
		memento.setOutLegalPubHolFrameNo(this.outLegalPubHolFrameNo);
		memento.setFlowTimeSetting(this.flowTimeSetting);
	}
}
