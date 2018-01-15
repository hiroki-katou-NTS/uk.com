/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.command.worktime.difftimeset.dto;

import nts.uk.ctx.at.shared.app.command.worktime.common.dto.InstantRoundingDto;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.worktime.common.InstantRounding;
import nts.uk.ctx.at.shared.dom.worktime.difftimeset.EmTimezoneChangeExtent;
import nts.uk.ctx.at.shared.dom.worktime.difftimeset.EmTimezoneChangeExtentGetMemento;

/**
 * The Class EmTimezoneChangeExtentDto.
 */
public class EmTimezoneChangeExtentDto {

	/** The ahead change. */
	private Integer aheadChange;

	/** The unit. */
	private InstantRoundingDto unit;

	/** The behind change. */
	private Integer behindChange;

	/**
	 * To domain.
	 *
	 * @return the em timezone change extent
	 */
	public EmTimezoneChangeExtent toDomain() {
		return new EmTimezoneChangeExtent(new EmTimezoneChangeExtentImpl(this));
	}

	/**
	 * The Class EmTimezoneChangeExtentImpl.
	 */
	public class EmTimezoneChangeExtentImpl implements EmTimezoneChangeExtentGetMemento {

		/** The dto. */
		private EmTimezoneChangeExtentDto dto;

		/**
		 * Instantiates a new em timezone change extent impl.
		 *
		 * @param emTimezoneChangeExtentDto the em timezone change extent dto
		 */
		public EmTimezoneChangeExtentImpl(EmTimezoneChangeExtentDto emTimezoneChangeExtentDto) {
			this.dto = emTimezoneChangeExtentDto;
		}

		@Override
		public AttendanceTime getAheadChange() {
			return new AttendanceTime(this.dto.aheadChange);
		}

		@Override
		public InstantRounding getUnit() {
			return new InstantRounding(this.dto.unit);
		}

		@Override
		public AttendanceTime getBehindChange() {
			return new AttendanceTime(this.dto.behindChange);
		}
	}
}
