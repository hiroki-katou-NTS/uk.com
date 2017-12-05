/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.command.worktime.difftimeset.dto;

import nts.uk.ctx.at.shared.dom.worktime.common.EmTimezoneNo;
import nts.uk.ctx.at.shared.dom.worktime.common.OTFrameNo;
import nts.uk.ctx.at.shared.dom.worktime.common.SettlementOrder;
import nts.uk.ctx.at.shared.dom.worktime.common.TimeZoneRounding;
import nts.uk.ctx.at.shared.dom.worktime.difftimeset.DiffTimeOTTimezoneGetMemento;
import nts.uk.ctx.at.shared.dom.worktime.difftimeset.DiffTimeOTTimezoneSet;

public class DiffTimeOTTimezoneSetDto {

	private boolean isUpdateStartTime;
	
	public DiffTimeOTTimezoneSet toDomain() {
		return new DiffTimeOTTimezoneSet(new DiffTimeOTTimezoneSetImpl(this));
	}

	public class DiffTimeOTTimezoneSetImpl implements DiffTimeOTTimezoneGetMemento {

		/** The dto. */
		private DiffTimeOTTimezoneSetDto dto;

		public DiffTimeOTTimezoneSetImpl(DiffTimeOTTimezoneSetDto diffTimeOTTimezoneSetDto) {
			this.dto = diffTimeOTTimezoneSetDto;
		}

		@Override
		public EmTimezoneNo getWorkTimezoneNo() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public boolean getRestraintTimeUse() {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean getEarlyOTUse() {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public TimeZoneRounding getTimezone() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public OTFrameNo getOTFrameNo() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public OTFrameNo getLegalOTframeNo() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public SettlementOrder getSettlementOrder() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public boolean isIsUpdateStartTime() {
			return this.dto.isUpdateStartTime;
		}

	}
}
