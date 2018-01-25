/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.find.worktime.flowset.dto;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.ot.frame.OvertimeWorkFrameNo;
import nts.uk.ctx.at.shared.dom.worktime.common.SettlementOrder;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowOTTimezoneSetMemento;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowTimeSetting;

/**
 * The Class FlowOTTimezoneDto.
 */
@Getter
@Setter
public class FlOTTimezoneDto implements FlowOTTimezoneSetMemento {

	/** The worktime no. */
	private Integer worktimeNo;

	/** The restrict time. */
	private Boolean restrictTime;

	/** The OT frame no. */
	private BigDecimal otFrameNo;

	/** The flow time setting. */
	private FlTimeSettingDto flowTimeSetting;

	/** The in legal OT frame no. */
	private BigDecimal inLegalOTFrameNo;

	/** The settlement order. */
	private Integer settlementOrder;

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.flowset.FlowOTTimezoneSetMemento#
	 * setRestrictTime(boolean)
	 */
	@Override
	public void setRestrictTime(boolean val) {
		this.restrictTime = val;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.flowset.FlowOTTimezoneSetMemento#
	 * setOTFrameNo(nts.uk.ctx.at.shared.dom.ot.frame.OvertimeWorkFrameNo)
	 */
	@Override
	public void setOTFrameNo(OvertimeWorkFrameNo no) {
		this.otFrameNo = no.v();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.flowset.FlowOTTimezoneSetMemento#
	 * setFlowTimeSetting(nts.uk.ctx.at.shared.dom.worktime.flowset.
	 * FlowTimeSetting)
	 */
	@Override
	public void setFlowTimeSetting(FlowTimeSetting ftSet) {
		if (ftSet != null) {
			this.flowTimeSetting = new FlTimeSettingDto();
			ftSet.saveToMemento(this.flowTimeSetting);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.flowset.FlowOTTimezoneSetMemento#
	 * setInLegalOTFrameNo(nts.uk.ctx.at.shared.dom.ot.frame.
	 * OvertimeWorkFrameNo)
	 */
	@Override
	public void setInLegalOTFrameNo(OvertimeWorkFrameNo no) {
		this.inLegalOTFrameNo = no.v();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.flowset.FlowOTTimezoneSetMemento#
	 * setSettlementOrder(nts.uk.ctx.at.shared.dom.worktime.fixedset.
	 * SettlementOrder)
	 */
	@Override
	public void setSettlementOrder(SettlementOrder od) {
		this.settlementOrder = od.v();
	}
}
