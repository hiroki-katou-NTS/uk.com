/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.employment.statutory.worktime.shared;

import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import lombok.Data;
import nts.uk.ctx.at.shared.dom.common.AttendanceTime;
import nts.uk.ctx.at.shared.dom.employment.statutory.worktime.shared.FlexSetting;
import nts.uk.ctx.at.shared.dom.employment.statutory.worktime.shared.Monthly;
import nts.uk.ctx.at.shared.dom.employment.statutory.worktime.shared.WorkingTimeSetting;

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
	private static List<FlexMonth> getFlexMonthly(FlexSetting domain) {
		List<FlexMonth> flexMonthly = new ArrayList<FlexMonth>();

		Map<Month, Double> statutoryMonthly = domain.getStatutorySetting().getMonthly().stream()
				.collect(Collectors.toMap(Monthly::getMonth, x -> x.getTime().minutes()));

		domain.getSpecifiedSetting().getMonthly().forEach(item -> {
			FlexMonth fm = new FlexMonth();
			fm.setMonth(item.getMonth().getValue());
			fm.setSpecifiedTime(item.getTime().v() / 60);
			flexMonthly.add(fm);
		});

		flexMonthly.forEach(month -> {
			month.setStatutoryTime(statutoryMonthly.get(Month.of(month.getMonth())).longValue());
		});

		return flexMonthly;
	}

	/**
	 * Gets the flex daily.
	 *
	 * @param domain the domain
	 * @return the flex daily
	 */
	private static FlexDaily getFlexDaily(FlexSetting domain) {
		FlexDaily flexDaily = new FlexDaily();
		flexDaily.setSpecifiedTime(domain.getSpecifiedSetting().getDaily().v() / 60);
		flexDaily.setStatutoryTime(domain.getStatutorySetting().getDaily().v() / 60);
		return flexDaily;
	}

	/**
	 * To domain.
	 *
	 * @param dto the dto
	 * @return the flex setting
	 */
	public static FlexSetting toDomain(FlexSettingDto dto) {
		AttendanceTime speDaily = new AttendanceTime(dto.getFlexDaily().getSpecifiedTime() * 60);
		AttendanceTime staDaily = new AttendanceTime(dto.getFlexDaily().getStatutoryTime() * 60);
		List<Monthly> speMonthly = new ArrayList<Monthly>();
		List<Monthly> staMonthly = new ArrayList<Monthly>();

		dto.getFlexMonthly().forEach(item -> {
			Month m = java.time.Month.of(item.getMonth());
			Monthly spe = new Monthly(new AttendanceTime(item.getSpecifiedTime() * 60), m);
			Monthly sta = new Monthly(new AttendanceTime(item.getStatutoryTime() * 60), m);
			speMonthly.add(spe);
			staMonthly.add(sta);
		});

		WorkingTimeSetting specifiedSetting = new WorkingTimeSetting();
		specifiedSetting.setDaily(speDaily);
		specifiedSetting.setMonthly(speMonthly);
		specifiedSetting.setWeekly(new AttendanceTime(0L));// default.

		WorkingTimeSetting statutorySetting = new WorkingTimeSetting();
		statutorySetting.setDaily(staDaily);
		statutorySetting.setMonthly(staMonthly);
		statutorySetting.setWeekly(new AttendanceTime(0L));// default.

		FlexSetting domain = new FlexSetting();
		domain.setSpecifiedSetting(specifiedSetting);
		domain.setStatutorySetting(statutorySetting);
		return domain;
	}
}
