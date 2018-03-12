/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.command.worktime.difftimeset.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import lombok.Getter;
import lombok.Setter;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.shared.app.command.worktime.common.dto.EmTimeZoneSetDto;
import nts.uk.ctx.at.shared.dom.worktime.common.EmTimeZoneSet;
import nts.uk.ctx.at.shared.dom.worktime.difftimeset.DiffTimeOTTimezoneSet;
import nts.uk.ctx.at.shared.dom.worktime.difftimeset.DiffTimezoneSetting;
import nts.uk.ctx.at.shared.dom.worktime.difftimeset.DiffTimezoneSettingGetMemento;

@Getter
@Setter
public class DiffTimezoneSettingDto {

	/** The employment timezone. */
	private List<EmTimeZoneSetDto> employmentTimezones;

	/** The OT timezone. */
	private List<DiffTimeOTTimezoneSetDto> lstOtTimezone;

	/**
	 * To domain.
	 *
	 * @return the diff timezone setting
	 */
	public DiffTimezoneSetting toDomain() {
		return new DiffTimezoneSetting(new DiffTimezoneSettingImpl(this));
	}

	/**
	 * The Class DiffTimezoneSettingImpl.
	 */
	public class DiffTimezoneSettingImpl implements DiffTimezoneSettingGetMemento {

		/** The dto. */
		private DiffTimezoneSettingDto dto;
		
		private int otTimezoneNo = 0;
		
		private int workTimezoneNo = 0; 

		/**
		 * Instantiates a new diff timezone setting impl.
		 *
		 * @param diffTimezoneSettingDto the diff timezone setting dto
		 */
		public DiffTimezoneSettingImpl(DiffTimezoneSettingDto diffTimezoneSettingDto) {
			this.dto = diffTimezoneSettingDto;
		}

		@Override
		public List<EmTimeZoneSet> getEmploymentTimezones() {
			
			this.dto.employmentTimezones = this.dto.employmentTimezones.stream()
					.sorted((item1, item2) -> item1.getTimezone().getStart().compareTo(item2.getTimezone().getStart()))
					.collect(Collectors.toList());
			
			workTimezoneNo = 0;
			this.dto.employmentTimezones.forEach(item -> {
				workTimezoneNo++;
				item.setEmploymentTimeFrameNo(workTimezoneNo);
			});
			
			return this.dto.employmentTimezones.stream().map(item -> {
				return new EmTimeZoneSet(item);
					}).collect(Collectors.toList());
		}

		@Override
		public List<DiffTimeOTTimezoneSet> getOTTimezones() {
			if (CollectionUtil.isEmpty(this.dto.lstOtTimezone)) {
				return new ArrayList<>();
			}

			this.dto.lstOtTimezone = this.dto.lstOtTimezone.stream().sorted((timezone1, timezone2) -> timezone1
					.getTimezone().getStart().compareTo(timezone2.getTimezone().getStart()))
					.collect(Collectors.toList());

			otTimezoneNo = 0;
			this.dto.lstOtTimezone.forEach(timezone -> {
				otTimezoneNo++;
				timezone.setWorkTimezoneNo(otTimezoneNo);
			});

			return this.dto.lstOtTimezone.stream().map(timezone -> timezone.toDomain()).collect(Collectors.toList());
		}

	}
}
