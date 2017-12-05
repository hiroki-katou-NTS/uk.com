/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.command.worktime.difftimeset.dto;

import nts.uk.ctx.at.shared.dom.worktime.difftimeset.DiffTimeDeductTimezone;
import nts.uk.ctx.at.shared.dom.worktime.difftimeset.DiffTimeDeductTimezoneGetMemento;
import nts.uk.shr.com.time.TimeWithDayAttr;

public class DiffTimeDeductTimezoneDto {

	/** The is update start time. */
	private boolean isUpdateStartTime;

	public DiffTimeDeductTimezone toDomain() {
		return new DiffTimeDeductTimezone(new DiffTimeDeductTimezoneImpl(this));
	}

	public class DiffTimeDeductTimezoneImpl implements DiffTimeDeductTimezoneGetMemento {

		private DiffTimeDeductTimezoneDto dto;

		public DiffTimeDeductTimezoneImpl(DiffTimeDeductTimezoneDto diffTimeDeductTimezoneDto) {
			this.dto = diffTimeDeductTimezoneDto;
		}

		@Override
		public TimeWithDayAttr getStart() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public TimeWithDayAttr getEnd() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public boolean isIsUpdateStartTime() {
			return this.dto.isUpdateStartTime;
		}

	}
}
