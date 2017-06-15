/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.employment.statutory.worktime.shared;

import java.util.Set;
import java.util.stream.Collectors;

import lombok.Data;
import nts.uk.ctx.at.shared.dom.common.AttendanceTime;
import nts.uk.ctx.at.shared.dom.employment.statutory.worktime.shared.Monthly;
import nts.uk.ctx.at.shared.dom.employment.statutory.worktime.shared.WorkingTimeSetting;

/**
 * The Class WorkingTimeSettingDto.
 */
@Data
public class WorkingTimeSettingDto {

	/** The daily. */
	private int daily;

	/** The monthly. */
	private Set<MonthlyDto> monthly;

	/** The weekly. */
	private int weekly;

	/**
	 * From domain.
	 *
	 * @param domain the domain
	 * @return the working time setting dto
	 */
	public static WorkingTimeSettingDto fromDomain(WorkingTimeSetting domain) {
		WorkingTimeSettingDto dto = new WorkingTimeSettingDto();
		dto.setDaily(domain.getDaily().v());
		dto.setWeekly(domain.getWeekly().v());
		dto.setMonthly(MonthlyDto.fromDomain(domain.getMonthly()));
		return dto;
	}

	/**
	 * To domain.
	 *
	 * @param dto the dto
	 * @return the working time setting
	 */
	public static WorkingTimeSetting toDomain(WorkingTimeSettingDto dto) {
		Set<Monthly> monthly = dto.getMonthly().stream().map(item -> {
			return new Monthly(new AttendanceTime(item.getTime()), java.time.Month.of(item.getMonth()));
		}).collect(Collectors.toSet());
		WorkingTimeSetting domain = new WorkingTimeSetting();
		domain.setDaily(new AttendanceTime(dto.getDaily()));
		domain.setWeekly(new AttendanceTime(dto.getWeekly()));
		domain.setMonthly(monthly);
		return domain;
	}
}
