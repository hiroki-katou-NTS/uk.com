/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.command.worktime.difftimeset.dto;

import java.util.List;
import java.util.stream.Collectors;

import nts.uk.ctx.at.shared.dom.worktime.difftimeset.DayOffTimezoneSetting;
import nts.uk.ctx.at.shared.dom.worktime.difftimeset.DiffTimeDayOffWorkTimezone;
import nts.uk.ctx.at.shared.dom.worktime.difftimeset.DiffTimeDayOffWorkTimezoneGetMemento;
import nts.uk.ctx.at.shared.dom.worktime.difftimeset.DiffTimeRestTimezone;

/**
 * The Class DiffTimeDayOffWorkTimezoneDto.
 */
public class DiffTimeDayOffWorkTimezoneDto {

	/** The rest timezone. */
	private DiffTimeRestTimezoneDto restTimezone;

	/** The work timezone. */
	private List<DayOffTimezoneSettingDto> workTimezones;

	/**
	 * To domain.
	 *
	 * @return the diff time day off work timezone
	 */
	public DiffTimeDayOffWorkTimezone toDomain() {
		return new DiffTimeDayOffWorkTimezone(new DiffTimeDayOffWorkTimezoneImpl(this));
	}

	/**
	 * The Class DiffTimeDayOffWorkTimezoneImpl.
	 */
	public class DiffTimeDayOffWorkTimezoneImpl implements DiffTimeDayOffWorkTimezoneGetMemento {

		/** The dto. */
		private DiffTimeDayOffWorkTimezoneDto dto;

		/**
		 * Instantiates a new diff time day off work timezone impl.
		 *
		 * @param diffTimeDayOffWorkTimezoneDto the diff time day off work timezone dto
		 */
		public DiffTimeDayOffWorkTimezoneImpl(DiffTimeDayOffWorkTimezoneDto diffTimeDayOffWorkTimezoneDto) {
			this.dto = diffTimeDayOffWorkTimezoneDto;
		}

		@Override
		public DiffTimeRestTimezone getRestTimezone() {
			return this.dto.restTimezone.toDomain();
		}

		@Override
		public List<DayOffTimezoneSetting> getWorkTimezones() {
			return this.dto.workTimezones.stream().map(item -> {
				return item.toDomain();
			}).collect(Collectors.toList());
		}

	}
}
