/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.command.worktime.difftimeset.dto;

import nts.uk.ctx.at.shared.app.command.worktime.common.dto.DeductionTimeDto;
import nts.uk.ctx.at.shared.dom.worktime.difftimeset.DiffTimeDeductTimezone;
import nts.uk.ctx.at.shared.dom.worktime.difftimeset.DiffTimeDeductTimezoneGetMemento;
import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 * The Class DiffTimeDeductTimezoneDto.
 */
public class DiffTimeDeductTimezoneDto extends DeductionTimeDto {

	/** The is update start time. */
	private boolean isUpdateStartTime;

	/**
	 * To domain.
	 *
	 * @return the diff time deduct timezone
	 */
	public DiffTimeDeductTimezone toDomain() {
		return new DiffTimeDeductTimezone(new DiffTimeDeductTimezoneImpl(this));
	}

	/**
	 * The Class DiffTimeDeductTimezoneImpl.
	 */
	public class DiffTimeDeductTimezoneImpl implements DiffTimeDeductTimezoneGetMemento {

		/** The dto. */
		private DiffTimeDeductTimezoneDto dto;

		/**
		 * Instantiates a new diff time deduct timezone impl.
		 *
		 * @param diffTimeDeductTimezoneDto the diff time deduct timezone dto
		 */
		public DiffTimeDeductTimezoneImpl(DiffTimeDeductTimezoneDto diffTimeDeductTimezoneDto) {
			this.dto = diffTimeDeductTimezoneDto;
		}

		@Override
		public TimeWithDayAttr getStart() {
			return this.dto.getStart();
		}

		@Override
		public TimeWithDayAttr getEnd() {
			return this.dto.getEnd();
		}

		@Override
		public boolean isIsUpdateStartTime() {
			return this.dto.isUpdateStartTime;
		}

	}
}
