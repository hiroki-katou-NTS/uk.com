/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.command.worktime.difftimeset.dto;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.app.command.worktime.common.dto.OverTimeOfTimeZoneSetDto;
import nts.uk.ctx.at.shared.dom.worktime.common.EmTimezoneNo;
import nts.uk.ctx.at.shared.dom.worktime.common.OTFrameNo;
import nts.uk.ctx.at.shared.dom.worktime.common.SettlementOrder;
import nts.uk.ctx.at.shared.dom.worktime.common.TimeZoneRounding;
import nts.uk.ctx.at.shared.dom.worktime.difftimeset.DiffTimeOTTimezoneGetMemento;
import nts.uk.ctx.at.shared.dom.worktime.difftimeset.DiffTimeOTTimezoneSet;

/**
 * The Class DiffTimeOTTimezoneSetDto.
 */
@Getter
@Setter
public class DiffTimeOTTimezoneSetDto extends OverTimeOfTimeZoneSetDto {

	/** The is update start time. */
	private boolean updateStartTime;

	/**
	 * To domain.
	 *
	 * @return the diff time OT timezone set
	 */
	public DiffTimeOTTimezoneSet toDomain() {
		return new DiffTimeOTTimezoneSet(new DiffTimeOTTimezoneSetImpl(this));
	}

	/**
	 * The Class DiffTimeOTTimezoneSetImpl.
	 */
	public class DiffTimeOTTimezoneSetImpl implements DiffTimeOTTimezoneGetMemento {

		/** The dto. */
		private DiffTimeOTTimezoneSetDto dto;

		/**
		 * Instantiates a new diff time OT timezone set impl.
		 *
		 * @param diffTimeOTTimezoneSetDto the diff time OT timezone set dto
		 */
		public DiffTimeOTTimezoneSetImpl(DiffTimeOTTimezoneSetDto diffTimeOTTimezoneSetDto) {
			this.dto = diffTimeOTTimezoneSetDto;
		}

		@Override
		public EmTimezoneNo getWorkTimezoneNo() {
			return this.dto.getWorkTimezoneNo();
		}

		@Override
		public boolean getRestraintTimeUse() {
			return this.dto.getRestraintTimeUse();
		}

		@Override
		public boolean getEarlyOTUse() {
			return this.dto.getEarlyOTUse();
		}

		@Override
		public TimeZoneRounding getTimezone() {
			return this.dto.getTimezone();
		}

		@Override
		public OTFrameNo getOTFrameNo() {
			return this.dto.getOTFrameNo();
		}

		@Override
		public OTFrameNo getLegalOTframeNo() {
			return this.dto.getLegalOTframeNo();
		}

		@Override
		public SettlementOrder getSettlementOrder() {
			return this.dto.getSettlementOrder();
		}

		@Override
		public boolean isIsUpdateStartTime() {
			return this.dto.isUpdateStartTime();
		}

	}
}
