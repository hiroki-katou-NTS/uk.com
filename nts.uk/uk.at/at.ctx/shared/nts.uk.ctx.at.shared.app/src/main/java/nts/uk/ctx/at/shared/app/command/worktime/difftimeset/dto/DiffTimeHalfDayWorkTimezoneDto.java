/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.command.worktime.difftimeset.dto;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.worktime.common.AmPmAtr;
import nts.uk.ctx.at.shared.dom.worktime.difftimeset.DiffTimeHalfDayGetMemento;
import nts.uk.ctx.at.shared.dom.worktime.difftimeset.DiffTimeHalfDayWorkTimezone;
import nts.uk.ctx.at.shared.dom.worktime.difftimeset.DiffTimeRestTimezone;
import nts.uk.ctx.at.shared.dom.worktime.difftimeset.DiffTimezoneSetting;

/**
 * The Class DiffTimeHalfDayWorkTimezoneDto.
 */

@Getter
@Setter
public class DiffTimeHalfDayWorkTimezoneDto {

	/** The rest timezone. */
	private DiffTimeRestTimezoneDto restTimezone;

	/** The work timezone. */
	private DiffTimezoneSettingDto workTimezone;

	/** The am pm atr. */
	private Integer amPmAtr;

	/**
	 * To domain.
	 *
	 * @return the diff time half day work timezone
	 */
	public DiffTimeHalfDayWorkTimezone toDomain() {
		return new DiffTimeHalfDayWorkTimezone(new DiffTimeHalfDayWorkTimezoneImpl(this));
	}

	/**
	 * The Class DiffTimeHalfDayWorkTimezoneImpl.
	 */
	public class DiffTimeHalfDayWorkTimezoneImpl implements DiffTimeHalfDayGetMemento {

		/** The dto. */
		private DiffTimeHalfDayWorkTimezoneDto dto;

		public DiffTimeHalfDayWorkTimezoneImpl(DiffTimeHalfDayWorkTimezoneDto diffTimeHalfDayWorkTimezoneDto) {
			this.dto = diffTimeHalfDayWorkTimezoneDto;
		}

		@Override
		public DiffTimeRestTimezone getRestTimezone() {
			return this.dto.restTimezone == null ? null : this.dto.restTimezone.toDomain();
		}

		@Override
		public DiffTimezoneSetting getWorkTimezone() {
			return this.dto.workTimezone == null ? null : this.dto.workTimezone.toDomain();
		}

		@Override
		public AmPmAtr getAmPmAtr() {
			return this.dto.amPmAtr == null ? null : AmPmAtr.valueOf(this.dto.amPmAtr);
		}

	}
}
