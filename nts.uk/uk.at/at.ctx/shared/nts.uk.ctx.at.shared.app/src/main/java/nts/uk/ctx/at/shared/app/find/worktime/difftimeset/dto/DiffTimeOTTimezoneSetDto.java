/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.find.worktime.difftimeset.dto;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.app.find.worktime.common.dto.TimeZoneRoundingDto;
import nts.uk.ctx.at.shared.dom.worktime.common.EmTimezoneNo;
import nts.uk.ctx.at.shared.dom.worktime.common.OTFrameNo;
import nts.uk.ctx.at.shared.dom.worktime.common.SettlementOrder;
import nts.uk.ctx.at.shared.dom.worktime.common.TimeZoneRounding;
import nts.uk.ctx.at.shared.dom.worktime.difftimeset.DiffTimeOTTimezoneSetMemento;

/**
 * The Class DiffTimeOTTimezoneSet.
 */
@Getter
@Setter
public class DiffTimeOTTimezoneSetDto implements DiffTimeOTTimezoneSetMemento {

	/** The work timezone no. */
	private Integer workTimezoneNo;

	/** The restraint time use. */
	private boolean restraintTimeUse;

	/** The early OT use. */
	private boolean earlyOTUse;

	/** The timezone. */
	private TimeZoneRoundingDto timezone;

	/** The OT frame no. */
	private Integer oTFrameNo;

	/** The legal O tframe no. */
	private Integer legalOTframeNo;

	/** The settlement order. */
	private Integer settlementOrder;

	/** The is update start time. */
	private boolean isUpdateStartTime;

	@Override
	public void setWorkTimezoneNo(EmTimezoneNo workTimezoneNo) {
		this.workTimezoneNo = workTimezoneNo.v();
	}

	@Override
	public void setRestraintTimeUse(boolean restraintTimeUse) {
		this.restraintTimeUse = restraintTimeUse;
	}

	@Override
	public void setEarlyOTUse(boolean earlyOTUse) {
		this.earlyOTUse = earlyOTUse;
	}

	@Override
	public void setTimezone(TimeZoneRounding timezone) {
		timezone.saveToMemento(this.timezone);
	}

	@Override
	public void setOTFrameNo(OTFrameNo oTFrameNo) {
		this.oTFrameNo = oTFrameNo.v();
	}

	@Override
	public void setLegalOTframeNo(OTFrameNo legalOTframeNo) {
		this.legalOTframeNo = legalOTframeNo.v();
	}

	@Override
	public void setSettlementOrder(SettlementOrder settlementOrder) {
		this.settlementOrder = settlementOrder.v();
	}

	@Override
	public void setIsUpdateStartTime(boolean isUpdateStartTime) {
		this.isUpdateStartTime = isUpdateStartTime;
	}

}
