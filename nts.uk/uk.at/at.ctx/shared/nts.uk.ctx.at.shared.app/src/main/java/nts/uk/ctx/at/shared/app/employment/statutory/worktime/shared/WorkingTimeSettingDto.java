/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.employment.statutory.worktime.shared;

import java.util.List;
import java.util.stream.Collectors;

import lombok.Data;
import nts.uk.ctx.at.shared.dom.common.DailyTime;
import nts.uk.ctx.at.shared.dom.common.MonthlyTime;
import nts.uk.ctx.at.shared.dom.common.WeeklyTime;
import nts.uk.ctx.at.shared.dom.employment.statutory.worktime.shared.Monthly;
import nts.uk.ctx.at.shared.dom.employment.statutory.worktime.shared.WorkingTimeSetting;

/**
 * The Class WorkingTimeSettingDto.
 */

/**
 * Instantiates a new working time setting dto.
 */
@Data
public class WorkingTimeSettingDto {

	/** The daily. */
	private Long daily;

	/** The monthly. */
	private List<MonthlyDto> monthly;

	/** The weekly. */
	private Long weekly;

	/**
	 * From domain.
	 *
	 * @param domain the domain
	 * @return the working time setting dto
	 */
	public static WorkingTimeSettingDto fromDomain(WorkingTimeSetting domain) {
		WorkingTimeSettingDto dto = new WorkingTimeSettingDto();
		dto.setDaily((long)domain.getDaily().minutes());
		dto.setWeekly((long)domain.getWeekly().minutes());
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
			return new Monthly(MonthlyTime.ofMinutes(item.getTime()), java.time.Month.of(item.getMonth()));
		}).collect(Collectors.toList());
		WorkingTimeSetting domain = new WorkingTimeSetting();
		domain.setDaily(DailyTime.ofMinutes(dto.getDaily()));
		domain.setWeekly(WeeklyTime.ofMinutes(dto.getWeekly()));
		domain.setMonthly(monthly);
		return domain;
	}
}
