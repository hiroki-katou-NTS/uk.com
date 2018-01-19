/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.command.worktime.flowset.dto;

import java.math.BigDecimal;

import lombok.Value;
import nts.uk.ctx.at.shared.dom.ot.frame.OvertimeWorkFrameNo;
import nts.uk.ctx.at.shared.dom.worktime.common.SettlementOrder;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowOTTimezoneGetMemento;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowTimeSetting;

/**
 * The Class FlOTTimezoneDto.
 */
@Value
public class FlOTTimezoneDto implements FlowOTTimezoneGetMemento {

	/** The worktime no. */
	private Integer worktimeNo;

	/** The restrict time. */
	private Boolean restrictTime;

	/** The OT frame no. */
	private BigDecimal overtimeFrameNo;

	/** The flow time setting. */
	private FlTimeSettingDto flowTimeSetting;

	/** The in legal OT frame no. */
	private BigDecimal inLegalOTFrameNo;

	/** The settlement order. */
	private Integer settlementOrder;

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.flowset.FlOTTimezoneGetMemento#
	 * getWorktimeNo()
	 */
	@Override
	public Integer getWorktimeNo() {
		return this.worktimeNo;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.flowset.FlOTTimezoneGetMemento#
	 * getRestrictTime()
	 */
	@Override
	public boolean getRestrictTime() {
		return this.restrictTime;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.flowset.FlOTTimezoneGetMemento#
	 * getOTFrameNo()
	 */
	@Override
	public OvertimeWorkFrameNo getOTFrameNo() {
		return new OvertimeWorkFrameNo(this.overtimeFrameNo);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.flowset.FlOTTimezoneGetMemento#
	 * getFlowTimeSetting()
	 */
	@Override
	public FlowTimeSetting getFlowTimeSetting() {
		return new FlowTimeSetting(this.flowTimeSetting);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.flowset.FlOTTimezoneGetMemento#
	 * getInLegalOTFrameNo()
	 */
	@Override
	public OvertimeWorkFrameNo getInLegalOTFrameNo() {
		return new OvertimeWorkFrameNo(this.inLegalOTFrameNo);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.flowset.FlOTTimezoneGetMemento#
	 * getSettlementOrder()
	 */
	@Override
	public SettlementOrder getSettlementOrder() {
		return new SettlementOrder(this.settlementOrder);
	}
}
