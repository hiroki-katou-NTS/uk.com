/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.find.worktime.difftimeset.dto;

import lombok.Getter;
import nts.uk.ctx.at.shared.dom.worktime.common.EmTimezoneNo;
import nts.uk.ctx.at.shared.dom.worktime.common.OTFrameNo;
import nts.uk.ctx.at.shared.dom.worktime.common.SettlementOrder;
import nts.uk.ctx.at.shared.dom.worktime.common.TimeZoneRounding;
import nts.uk.ctx.at.shared.dom.worktime.difftimeset.DiffTimeOTTimezoneSetMemento;

/**
 * The Class DiffTimeOTTimezoneSet.
 */
@Getter
public class DiffTimeOTTimezoneSetDto implements DiffTimeOTTimezoneSetMemento {

	/** The is update start time. */
	private boolean isUpdateStartTime;

	@Override
	public void setWorkTimezoneNo(EmTimezoneNo workTimezoneNo) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setRestraintTimeUse(boolean restraintTimeUse) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setEarlyOTUse(boolean earlyOTUse) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setTimezone(TimeZoneRounding timezone) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setOTFrameNo(OTFrameNo OTFrameNo) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setLegalOTframeNo(OTFrameNo legalOTframeNo) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setSettlementOrder(SettlementOrder settlementOrder) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setIsUpdateStartTime(boolean isUpdateStartTime) {
		// TODO Auto-generated method stub
		
	}

}
