/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.find.worktime.common.dto;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.worktime.common.EmTimezoneNo;
import nts.uk.ctx.at.shared.dom.worktime.common.OTFrameNo;
import nts.uk.ctx.at.shared.dom.worktime.common.OverTimeOfTimeZoneSetSetMemento;
import nts.uk.ctx.at.shared.dom.worktime.common.SettlementOrder;
import nts.uk.ctx.at.shared.dom.worktime.common.TimeZoneRounding;

/**
 * The Class OverTimeOfTimeZoneSetDto.
 */
@Getter
@Setter
public class OverTimeOfTimeZoneSetDto implements OverTimeOfTimeZoneSetSetMemento{
	
	/** The work timezone no. */
	private Integer workTimezoneNo;

	/** The restraint time use. */
	private boolean restraintTimeUse;

	/** The early OT use. */
	private boolean earlyOTUse;

	/** The timezone. */
	private TimeZoneRoundingDto timezone;

	/** The OT frame no. */
	private Integer otFrameNo;

	/** The legal O tframe no. */
	private Integer legalOTframeNo;

	/** The settlement order. */
	private Integer settlementOrder;

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.fixedset.
	 * OverTimeOfTimeZoneSetSetMemento#setWorkTimezoneNo(nts.uk.ctx.at.shared.
	 * dom.worktime.fixedset.EmTimezoneNo)
	 */
	@Override
	public void setWorkTimezoneNo(EmTimezoneNo workTimezoneNo) {
		this.workTimezoneNo = workTimezoneNo.v();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.fixedset.
	 * OverTimeOfTimeZoneSetSetMemento#setRestraintTimeUse(boolean)
	 */
	@Override
	public void setRestraintTimeUse(boolean restraintTimeUse) {
		this.restraintTimeUse = restraintTimeUse;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.fixedset.
	 * OverTimeOfTimeZoneSetSetMemento#setEarlyOTUse(boolean)
	 */
	@Override
	public void setEarlyOTUse(boolean earlyOTUse) {
		this.earlyOTUse = earlyOTUse;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.fixedset.
	 * OverTimeOfTimeZoneSetSetMemento#setTimezone(nts.uk.ctx.at.shared.dom.
	 * worktime.fixedset.TimeZoneRounding)
	 */
	@Override
	public void setTimezone(TimeZoneRounding timezone) {
		if (timezone != null) {
			this.timezone = new TimeZoneRoundingDto();
			timezone.saveToMemento(this.timezone);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.fixedset.
	 * OverTimeOfTimeZoneSetSetMemento#setOTFrameNo(nts.uk.ctx.at.shared.dom.
	 * worktime.fixedset.OTFrameNo)
	 */
	@Override
	public void setOTFrameNo(OTFrameNo OTFrameNo) {
		this.otFrameNo = OTFrameNo.v();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.fixedset.
	 * OverTimeOfTimeZoneSetSetMemento#setLegalOTframeNo(nts.uk.ctx.at.shared.
	 * dom.worktime.fixedset.OTFrameNo)
	 */
	@Override
	public void setLegalOTframeNo(OTFrameNo legalOTframeNo) {
		this.legalOTframeNo = legalOTframeNo.v();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.fixedset.
	 * OverTimeOfTimeZoneSetSetMemento#setSettlementOrder(nts.uk.ctx.at.shared.
	 * dom.worktime.fixedset.SettlementOrder)
	 */
	@Override
	public void setSettlementOrder(SettlementOrder settlementOrder) {
		this.settlementOrder = settlementOrder.v(); 
	}
}
