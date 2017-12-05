/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.command.worktime.difftimeset.dto;

import java.util.List;

import nts.uk.ctx.at.shared.dom.worktime.common.EmTimeZoneSet;
import nts.uk.ctx.at.shared.dom.worktime.difftimeset.DiffTimeOTTimezoneSet;
import nts.uk.ctx.at.shared.dom.worktime.difftimeset.DiffTimezoneSetting;
import nts.uk.ctx.at.shared.dom.worktime.difftimeset.DiffTimezoneSettingGetMemento;

public class DiffTimezoneSettingDto {

	/** The employment timezone. */
	private List<EmTimeZoneSet> employmentTimezone;

	/** The OT timezone. */
	private List<DiffTimeOTTimezoneSetDto> OTTimezone;

	public DiffTimezoneSetting toDomain() {
		return new DiffTimezoneSetting(new DiffTimezoneSettingImpl(this));
	}

	public class DiffTimezoneSettingImpl implements DiffTimezoneSettingGetMemento {

		/** The dto. */
		private DiffTimezoneSettingDto dto;

		public DiffTimezoneSettingImpl(DiffTimezoneSettingDto diffTimezoneSettingDto) {
			this.dto = diffTimezoneSettingDto;
		}

		@Override
		public List<EmTimeZoneSet> getEmploymentTimezone() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public List<DiffTimeOTTimezoneSet> getOTTimezone() {
			// TODO Auto-generated method stub
			return null;
		}

	}
}
