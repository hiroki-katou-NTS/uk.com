/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.command.worktime.flowset.dto;

import java.math.BigDecimal;

import lombok.Value;
import nts.uk.ctx.at.shared.dom.worktime.common.BreakFrameNo;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowTimeSetting;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlWorkHdTzGetMemento;

/**
 * The Class FlWorkHdTimeZoneDto.
 */
@Value
public class FlWorkHdTimeZoneDto implements FlWorkHdTzGetMemento {

	/** The worktime no. */
	private Integer worktimeNo;

	/** The use in legal break restrict time. */
	private boolean useInLegalBreakRestrictTime;

	/** The in legal break frame no. */
	private BigDecimal inLegalBreakFrameNo;

	/** The use out legal break restrict time. */
	private boolean useOutLegalBreakRestrictTime;

	/** The out legal break frame no. */
	private BigDecimal outLegalBreakFrameNo;

	/** The use out legal pub hol restrict time. */
	private boolean useOutLegalPubHolRestrictTime;

	/** The out legal pub hol frame no. */
	private BigDecimal outLegalPubHolFrameNo;

	/** The flow time setting. */
	private FlTimeSettingDto flowTimeSetting;

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.flowset.FlWorkHdTzGetMemento#
	 * getWorktimeNo()
	 */
	@Override
	public Integer getWorktimeNo() {
		return this.worktimeNo;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.flowset.FlWorkHdTzGetMemento#
	 * getUseInLegalBreakRestrictTime()
	 */
	@Override
	public boolean getUseInLegalBreakRestrictTime() {
		return this.useInLegalBreakRestrictTime;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.flowset.FlWorkHdTzGetMemento#
	 * getInLegalBreakFrameNo()
	 */
	@Override
	public BreakFrameNo getInLegalBreakFrameNo() {
		return new BreakFrameNo(this.inLegalBreakFrameNo);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.flowset.FlWorkHdTzGetMemento#
	 * getUseOutLegalBreakRestrictTime()
	 */
	@Override
	public boolean getUseOutLegalBreakRestrictTime() {
		return this.useOutLegalBreakRestrictTime;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.flowset.FlWorkHdTzGetMemento#
	 * getOutLegalBreakFrameNo()
	 */
	@Override
	public BreakFrameNo getOutLegalBreakFrameNo() {
		return new BreakFrameNo(this.outLegalBreakFrameNo);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.flowset.FlWorkHdTzGetMemento#
	 * getUseOutLegalPubHolRestrictTime()
	 */
	@Override
	public boolean getUseOutLegalPubHolRestrictTime() {
		return this.useOutLegalPubHolRestrictTime;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.flowset.FlWorkHdTzGetMemento#
	 * getOutLegalPubHolFrameNo()
	 */
	@Override
	public BreakFrameNo getOutLegalPubHolFrameNo() {
		return new BreakFrameNo(this.outLegalPubHolFrameNo);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.flowset.FlWorkHdTzGetMemento#
	 * getFlowTimeSetting()
	 */
	@Override
	public FlowTimeSetting getFlowTimeSetting() {
		return new FlowTimeSetting(this.flowTimeSetting);
	}

}
