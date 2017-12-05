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

public class DiffTimeDayOffWorkTimezoneDto {

	/** The rest timezone. */
	private DiffTimeRestTimezoneDto restTimezone;

	/** The work timezone. */
	private List<DayOffTimezoneSettingDto> workTimezone;

	public DiffTimeDayOffWorkTimezone toDomain() {
		return new DiffTimeDayOffWorkTimezone(new DiffTimeDayOffWorkTimezoneImpl(this));
	}

	public class DiffTimeDayOffWorkTimezoneImpl implements DiffTimeDayOffWorkTimezoneGetMemento {

		private DiffTimeDayOffWorkTimezoneDto dto;

		public DiffTimeDayOffWorkTimezoneImpl(DiffTimeDayOffWorkTimezoneDto diffTimeDayOffWorkTimezoneDto) {
			this.dto = diffTimeDayOffWorkTimezoneDto;
		}

		@Override
		public DiffTimeRestTimezone getRestTimezone() {
			return this.dto.restTimezone.toDomain();
		}

		@Override
		public List<DayOffTimezoneSetting> getWorkTimezone() {
			return this.dto.workTimezone.stream().map(item -> {
				return item.toDomain();
			}).collect(Collectors.toList());
		}

	}
}
