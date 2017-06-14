/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.employment.statutory.worktime.shared;

import java.time.Month;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import lombok.Data;
import nts.uk.ctx.at.shared.dom.common.AttendanceTime;
import nts.uk.ctx.at.shared.dom.employment.statutory.worktime.shared.FlexSetting;
import nts.uk.ctx.at.shared.dom.employment.statutory.worktime.shared.Monthly;
import nts.uk.ctx.at.shared.dom.employment.statutory.worktime.shared.WorkingTimeSetting;

/**
 * The Class FlexSettingDto.
 */
@Data
public class FlexSettingDto {

	/** The specified setting. */
	private FlexDaily flexDaily;

	/** The statutory setting. */
	private Set<FlexMonth> flexMonthly;

	/**
	 * From domain.
	 *
	 * @param domain
	 *            the domain
	 * @return the flex setting dto
	 */
	public static FlexSettingDto fromDomain(FlexSetting domain) {
		WorkingTimeSettingDto specifiedSetting = WorkingTimeSettingDto.fromDomain(domain.getSpecifiedSetting());
		WorkingTimeSettingDto statutorySetting = WorkingTimeSettingDto.fromDomain(domain.getStatutorySetting());

		Set<FlexMonth> flexMonthly = new HashSet<>();

		// Set specifiedSetting.
		specifiedSetting.getMonthly().forEach(item -> {
			FlexMonth fm = new FlexMonth();
			fm.setMonth(item.getMonth());
			fm.setSpecifiedTime(item.getTime());
			flexMonthly.add(new FlexMonth());
		});

		// Set statutorySetting
		statutorySetting.getMonthly().forEach(item -> {
			FlexMonth fm = flexMonthly.stream().filter(i -> i.getMonth() == item.getMonth()).findFirst().get();
			flexMonthly.remove(fm);
			fm.setStatutoryTime(item.getTime());
			flexMonthly.add(fm);
		});
		//TODO code nghich.
		/*for(int i=1; i<13; i++) {
			FlexMonth fm = new FlexMonth();
			fm.setMonth(i);
			fm.setSpecifiedTime(specifiedSetting.getMonthly().get(i));
			fm.setStatutoryTime(statutorySetting.getMonthly());
		}*/

		FlexDaily flexDaily = new FlexDaily();
		flexDaily.setSpecifiedTime(specifiedSetting.getDaily());
		flexDaily.setStatutoryTime(statutorySetting.getDaily());

		FlexSettingDto dto = new FlexSettingDto();
		dto.setFlexDaily(flexDaily);
		dto.setFlexMonthly(flexMonthly);
		return dto;
	}

	public static FlexSetting toDomain(FlexSettingDto dto) {
		AttendanceTime speDaily = new AttendanceTime(dto.getFlexDaily().getSpecifiedTime());
		AttendanceTime staDaily = new AttendanceTime(dto.getFlexDaily().getStatutoryTime());
		Set<Monthly> speMonthly = new HashSet<Monthly>();
		Set<Monthly> staMonthly = new HashSet<Monthly>();

		dto.getFlexMonthly().forEach(item -> {
			Month m = java.time.Month.of(item.getMonth());
			Monthly spe = new Monthly(new AttendanceTime(item.getSpecifiedTime()), m);
			Monthly sta = new Monthly(new AttendanceTime(item.getStatutoryTime()), m);
			speMonthly.add(spe);
			staMonthly.add(sta);
		});

		WorkingTimeSetting specifiedSetting = new WorkingTimeSetting();
		specifiedSetting.setDaily(speDaily);
		specifiedSetting.setMonthly(speMonthly);
		specifiedSetting.setWeekly(new AttendanceTime(0));//default.

		WorkingTimeSetting statutorySetting = new WorkingTimeSetting();
		statutorySetting.setDaily(staDaily);
		statutorySetting.setMonthly(staMonthly);
		statutorySetting.setWeekly(new AttendanceTime(0));//default.

		FlexSetting domain = new FlexSetting();
		domain.setSpecifiedSetting(specifiedSetting);
		domain.setStatutorySetting(statutorySetting);
		return domain;
	}
}
