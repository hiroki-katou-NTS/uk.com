/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.command.worktime.difftimeset.dto;

import java.util.List;
import java.util.stream.Collectors;

import nts.uk.ctx.at.shared.app.command.worktime.common.dto.StampReflectTimezoneDto;
import nts.uk.ctx.at.shared.dom.worktime.common.StampReflectTimezone;
import nts.uk.ctx.at.shared.dom.worktime.difftimeset.DiffTimeStampReflectGetMemento;
import nts.uk.ctx.at.shared.dom.worktime.difftimeset.DiffTimeWorkStampReflectTimezone;

public class DiffTimeWorkStampReflectTimezoneDto {

	/** The stamp reflect timezone. */
	private List<StampReflectTimezoneDto> stampReflectTimezone;

	/** The is update start time. */
	private boolean isUpdateStartTime;

	/**
	 * To domain.
	 *
	 * @return the diff time work stamp reflect timezone
	 */
	public DiffTimeWorkStampReflectTimezone toDomain() {
		return new DiffTimeWorkStampReflectTimezone(new DiffTimeWorkStampReflectTimezoneImpl(this));
	}

	/**
	 * The Class DiffTimeWorkStampReflectTimezoneImpl.
	 */
	public class DiffTimeWorkStampReflectTimezoneImpl implements DiffTimeStampReflectGetMemento {

		/** The dto. */
		private DiffTimeWorkStampReflectTimezoneDto dto;

		/**
		 * Instantiates a new diff time work stamp reflect timezone impl.
		 *
		 * @param diffTimeWorkStampReflectTimezoneDto
		 *            the diff time work stamp reflect timezone dto
		 */
		public DiffTimeWorkStampReflectTimezoneImpl(
				DiffTimeWorkStampReflectTimezoneDto diffTimeWorkStampReflectTimezoneDto) {
			this.dto = diffTimeWorkStampReflectTimezoneDto;
		}

		@Override
		public List<StampReflectTimezone> getStampReflectTimezone() {
			return this.dto.stampReflectTimezone.stream().map(item -> {
				return new StampReflectTimezone(item);
			}).collect(Collectors.toList());
		}

		@Override
		public boolean isIsUpdateStartTime() {
			return this.dto.isUpdateStartTime;
		}

	}
}
