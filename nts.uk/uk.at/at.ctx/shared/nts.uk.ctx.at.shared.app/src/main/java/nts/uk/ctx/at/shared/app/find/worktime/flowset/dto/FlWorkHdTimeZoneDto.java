/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.find.worktime.flowset.dto;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.worktime.common.BreakFrameNo;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowTimeSetting;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlWorkHdTzSetMemento;

/**
 * The Class FlowWorkHolidayTimeZoneDto.
 */

@Getter
@Setter
public class FlWorkHdTimeZoneDto implements FlWorkHdTzSetMemento {

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
	 * @see nts.uk.ctx.at.shared.dom.worktime.flowset.
	 * FlowWorkHolidayTimeZoneSetMemento#setInLegalBreakFrameNo(nts.uk.ctx.at.
	 * shared.dom.worktime.fixedset.BreakFrameNo)
	 */
	@Override
	public void setInLegalBreakFrameNo(BreakFrameNo brNo) {
		this.inLegalBreakFrameNo = brNo.v();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.flowset.
	 * FlowWorkHolidayTimeZoneSetMemento#setOutLegalBreakFrameNo(nts.uk.ctx.at.
	 * shared.dom.worktime.fixedset.BreakFrameNo)
	 */
	@Override
	public void setOutLegalBreakFrameNo(BreakFrameNo brNo) {
		this.outLegalBreakFrameNo = brNo.v();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.flowset.
	 * FlowWorkHolidayTimeZoneSetMemento#setOutLegalPubHolFrameNo(nts.uk.ctx.at.
	 * shared.dom.worktime.fixedset.BreakFrameNo)
	 */
	@Override
	public void setOutLegalPubHolFrameNo(BreakFrameNo no) {
		this.outLegalPubHolFrameNo = no.v();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.flowset.
	 * FlowWorkHolidayTimeZoneSetMemento#setFlowTimeSetting(nts.uk.ctx.at.shared
	 * .dom.worktime.flowset.FlowTimeSetting)
	 */
	@Override
	public void setFlowTimeSetting(FlowTimeSetting ftSet) {
		ftSet.saveToMemento(this.flowTimeSetting);
	}
}
