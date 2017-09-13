/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.find.statutory.worktime.shared;

import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import lombok.Data;
import nts.uk.ctx.at.shared.dom.common.DailyTime;
import nts.uk.ctx.at.shared.dom.common.MonthlyTime;
import nts.uk.ctx.at.shared.dom.common.WeeklyTime;
import nts.uk.ctx.at.shared.dom.statutory.worktime.shared.FlexSetting;
import nts.uk.ctx.at.shared.dom.statutory.worktime.shared.Monthly;
import nts.uk.ctx.at.shared.dom.statutory.worktime.shared.WorkingTimeSetting;

/**
 * The Class FlexSettingDto.
 */

/**
 * Instantiates a new flex setting dto.
 */
@Data
public class FlexSettingDto {

	/** The flex daily. */
	private FlexDaily flexDaily;

	/** The flex monthly. */
	private List<FlexMonth> flexMonthly;

	/**
	 * From domain.
	 *
	 * @param domain the domain
	 * @return the flex setting dto
	 */
	public static FlexSettingDto fromDomain(FlexSetting domain) {
		FlexSettingDto dto = new FlexSettingDto();
		dto.setFlexDaily(getFlexDaily(domain));
		dto.setFlexMonthly(getFlexMonthly(domain));
		return dto;
	}

	/**
	 * Gets the flex monthly.
	 *
	 * @param domain the domain
	 * @return the flex monthly
	 */
	// Get flexMonthly dto from domain.
	private static List<FlexMonth> getFlexMonthly(FlexSetting domain) {
		List<FlexMonth> flexMonthly = new ArrayList<FlexMonth>();

		// Convert to map.
		Map<Month, Integer> statutoryMonthly = domain.getStatutorySetting().getMonthly().stream()
				.collect(Collectors.toMap(Monthly::getMonth, x -> x.getTime().valueAsMinutes()));

		// Set specified work time.
		domain.getSpecifiedSetting().getMonthly().forEach(item -> {
			FlexMonth fm = new FlexMonth();
			fm.setMonth(item.getMonth().getValue());
			fm.setSpecifiedTime(item.getTime().valueAsMinutes());
			flexMonthly.add(fm);
		});

		// Set statutory work time.
		flexMonthly.forEach(month -> {
			month.setStatutoryTime(statutoryMonthly.get(Month.of(month.getMonth())));
		});

		return flexMonthly;
	}

	/**
	 * Gets the flex daily.
	 *
	 * @param domain the domain
	 * @return the flex daily
	 */
	// Get flexDaily dto from domain.
	private static FlexDaily getFlexDaily(FlexSetting domain) {
		FlexDaily flexDaily = new FlexDaily();
		flexDaily.setSpecifiedTime(domain.getSpecifiedSetting().getDaily().valueAsMinutes());
		flexDaily.setStatutoryTime(domain.getStatutorySetting().getDaily().valueAsMinutes());
		return flexDaily;
	}

	/**
	 * To domain.
	 *
	 * @param dto the dto
	 * @return the flex setting
	 */
	public static FlexSetting toDomain(FlexSettingDto dto) {
		DailyTime speDaily = new DailyTime(dto.getFlexDaily().getSpecifiedTime());
		DailyTime staDaily = new DailyTime(dto.getFlexDaily().getStatutoryTime());
		List<Monthly> speMonthly = new ArrayList<Monthly>();
		List<Monthly> staMonthly = new ArrayList<Monthly>();

		dto.getFlexMonthly().forEach(item -> {
			Month m = java.time.Month.of(item.getMonth());
			Monthly spe = new Monthly(new MonthlyTime(item.getSpecifiedTime()), m);
			Monthly sta = new Monthly(new MonthlyTime(item.getStatutoryTime()), m);
			speMonthly.add(spe);
			staMonthly.add(sta);
		});

		WorkingTimeSetting specifiedSetting = new WorkingTimeSetting();
		specifiedSetting.setDaily(speDaily);
		specifiedSetting.setMonthly(speMonthly);
		specifiedSetting.setWeekly(WeeklyTime.defaultValue());

		WorkingTimeSetting statutorySetting = new WorkingTimeSetting();
		statutorySetting.setDaily(staDaily);
		statutorySetting.setMonthly(staMonthly);
		statutorySetting.setWeekly(WeeklyTime.defaultValue());

		FlexSetting domain = new FlexSetting();
		domain.setSpecifiedSetting(specifiedSetting);
		domain.setStatutorySetting(statutorySetting);
		return domain;
	}
}
