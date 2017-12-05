/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.command.worktime.difftimeset.dto;

import nts.uk.ctx.at.shared.dom.worktime.common.StampReflectTimezone;
import nts.uk.ctx.at.shared.dom.worktime.difftimeset.DiffTimeStampReflectGetMemento;
import nts.uk.ctx.at.shared.dom.worktime.difftimeset.DiffTimeWorkStampReflectTimezone;

public class DiffTimeWorkStampReflectTimezoneDto {

	/** The stamp reflect timezone. */
	//TODO 
//	private StampReflectTimezoneDto stampReflectTimezone;

	/** The is update start time. */
	private boolean isUpdateStartTime;

	public DiffTimeWorkStampReflectTimezone toDomain() {
		return new DiffTimeWorkStampReflectTimezone(new DiffTimeWorkStampReflectTimezoneImpl(this));
	}

	public class DiffTimeWorkStampReflectTimezoneImpl implements DiffTimeStampReflectGetMemento {

		private DiffTimeWorkStampReflectTimezoneDto dto;

		public DiffTimeWorkStampReflectTimezoneImpl(
				DiffTimeWorkStampReflectTimezoneDto diffTimeWorkStampReflectTimezoneDto) {
			this.dto = diffTimeWorkStampReflectTimezoneDto;
		}

		@Override
		public StampReflectTimezone getStampReflectTimezone() {
//			return this.dto.stampReflectTimezone.toDomain();
			return null;
		}

		@Override
		public boolean isIsUpdateStartTime() {
			return this.dto.isUpdateStartTime;
		}

	}
}
