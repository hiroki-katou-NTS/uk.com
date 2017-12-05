/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.command.worktime.difftimeset.dto;

import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.worktime.common.InstantRounding;
import nts.uk.ctx.at.shared.dom.worktime.difftimeset.EmTimezoneChangeExtent;
import nts.uk.ctx.at.shared.dom.worktime.difftimeset.EmTimezoneChangeExtentGetMemento;

public class EmTimezoneChangeExtentDto {

	/** The ahead change. */
	private Integer aheadChange;

	/** The unit. */
	private InstantRounding unit;

	/** The behind change. */
	private Integer behindChange;

	public EmTimezoneChangeExtent toDomain() {
		return new EmTimezoneChangeExtent(new EmTimezoneChangeExtentImpl(this));
	}

	public class EmTimezoneChangeExtentImpl implements EmTimezoneChangeExtentGetMemento {

		private EmTimezoneChangeExtentDto dto;

		public EmTimezoneChangeExtentImpl(EmTimezoneChangeExtentDto emTimezoneChangeExtentDto) {
			this.dto = emTimezoneChangeExtentDto;
		}

		@Override
		public AttendanceTime getAheadChange() {
			return new AttendanceTime(this.dto.aheadChange);
		}

		@Override
		public InstantRounding getUnit() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public AttendanceTime getBehindChange() {
			return new AttendanceTime(this.dto.behindChange);
		}
	}
}
