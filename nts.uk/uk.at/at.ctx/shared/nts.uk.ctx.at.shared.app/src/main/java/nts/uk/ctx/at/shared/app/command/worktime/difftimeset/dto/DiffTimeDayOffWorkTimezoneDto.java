/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.command.worktime.difftimeset.dto;

import java.util.List;
import java.util.stream.Collectors;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.app.command.worktime.common.dto.HDWorkTimeSheetSettingDto;
import nts.uk.ctx.at.shared.dom.worktime.common.HDWorkTimeSheetSetting;
import nts.uk.ctx.at.shared.dom.worktime.difftimeset.DiffTimeDayOffWorkTimezone;
import nts.uk.ctx.at.shared.dom.worktime.difftimeset.DiffTimeDayOffWorkTimezoneGetMemento;
import nts.uk.ctx.at.shared.dom.worktime.difftimeset.DiffTimeRestTimezone;

/**
 * The Class DiffTimeDayOffWorkTimezoneDto.
 */
@Getter
@Setter
public class DiffTimeDayOffWorkTimezoneDto {

	/** The rest timezone. */
	private DiffTimeRestTimezoneDto restTimezone;

	/** The work timezone. */
	private List<HDWorkTimeSheetSettingDto> workTimezones;

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
		
		/** The work time no. */
		private int workTimeNo = 0;

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
			if(this.dto.restTimezone ==null)
			{
				return null;
			}
			return this.dto.restTimezone.toDomain();
		}

		@Override
		public List<HDWorkTimeSheetSetting> getWorkTimezones() {
			if(this.dto.workTimezones == null)
			{
				return null;
			}
			workTimeNo = 0;
			return this.dto.workTimezones.stream().sorted((timezone1, timezon2) -> timezone1.getTimezone().getStart()
					.compareTo(timezon2.getTimezone().getStart())).map(item -> {
						item.setWorkTimeNo(++workTimeNo);
						return new HDWorkTimeSheetSetting(item);
					}).collect(Collectors.toList());
		}

	}
}
