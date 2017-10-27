/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.find.statutory.worktime.shared;

import java.util.List;
import java.util.stream.Collectors;

import lombok.Data;
import nts.uk.ctx.at.shared.dom.common.DailyTime;
import nts.uk.ctx.at.shared.dom.common.MonthlyTime;
import nts.uk.ctx.at.shared.dom.common.WeeklyTime;
import nts.uk.ctx.at.shared.dom.statutory.worktime.shared.Monthly;
import nts.uk.ctx.at.shared.dom.statutory.worktime.shared.WorkingTimeSetting;

/**
 * The Class WorkingTimeSettingDto.
 */

/**
 * Instantiates a new working time setting dto.
 */
@Data
public class WorkingTimeSettingDto {

	/** The daily. */
	private int daily;

	/** The monthly. */
	private List<MonthlyDto> monthly;

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
		dto.setDaily(domain.getDaily().valueAsMinutes());
		dto.setWeekly(domain.getWeekly().valueAsMinutes());
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
		List<Monthly> monthly = dto.getMonthly().stream().map(item -> {
			return new Monthly(new MonthlyTime(item.getTime()), java.time.Month.of(item.getMonth()));
		}).collect(Collectors.toList());
		WorkingTimeSetting domain = new WorkingTimeSetting();
		domain.setDaily(new DailyTime(dto.getDaily()));
		domain.setWeekly(new WeeklyTime(dto.getWeekly()));
		domain.setMonthly(monthly);
		return domain;
	}
}
