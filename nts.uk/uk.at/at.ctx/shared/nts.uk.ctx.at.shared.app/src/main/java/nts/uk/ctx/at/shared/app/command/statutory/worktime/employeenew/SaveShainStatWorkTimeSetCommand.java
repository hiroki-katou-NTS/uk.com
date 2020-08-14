/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.command.statutory.worktime.employeenew;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.val;
import nts.arc.enums.EnumAdaptor;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.shared.app.command.statutory.worktime.common.DeforLaborSettingDto;
import nts.uk.ctx.at.shared.app.command.statutory.worktime.common.FlexSettingDto;
import nts.uk.ctx.at.shared.app.command.statutory.worktime.common.MonthlyUnitDto;
import nts.uk.ctx.at.shared.app.command.statutory.worktime.common.NormalSettingDto;
import nts.uk.ctx.at.shared.app.command.statutory.worktime.common.WorkingTimeSettingDto;
import nts.uk.ctx.at.shared.dom.common.MonthlyEstimateTime;
import nts.uk.ctx.at.shared.dom.common.TimeOfDay;
import nts.uk.ctx.at.shared.dom.common.WeeklyTime;
import nts.uk.ctx.at.shared.dom.statutory.worktime.monunit.MonthlyLaborTime;
import nts.uk.ctx.at.shared.dom.statutory.worktime.monunit.MonthlyWorkTimeSet.LaborWorkTypeAttr;
import nts.uk.ctx.at.shared.dom.statutory.worktime.monunit.MonthlyWorkTimeSetSha;
import nts.uk.ctx.at.shared.dom.statutory.worktime.week.DailyUnit;
import nts.uk.ctx.at.shared.dom.statutory.worktime.week.WeekStart;
import nts.uk.ctx.at.shared.dom.statutory.worktime.week.WeeklyUnit;
import nts.uk.ctx.at.shared.dom.statutory.worktime.week.defor.DeforLaborTimeSha;
import nts.uk.ctx.at.shared.dom.statutory.worktime.week.regular.RegularLaborTimeSha;

/**
 * The Class SaveComStatWorkTimeSetCommand.
 */
@Getter
@Setter
@NoArgsConstructor
public class SaveShainStatWorkTimeSetCommand{

	/** The year. */
	private int year;
	
	/** The employee id. */
	private String employeeId;

	/** The normal setting. */
	private NormalSettingDto normalSetting;

	/** The flex setting. */
	private FlexSettingDto flexSetting;

	/** The defor labor setting. */
	private DeforLaborSettingDto deforLaborSetting;

	/** The regular labor time. */
	private WorkingTimeSettingDto regularLaborTime;

	/** The trans labor time. */
	private WorkingTimeSettingDto transLaborTime;

	public List<MonthlyWorkTimeSetSha> regular(String cid) {
		return normalSetting
				.getStatutorySetting().stream().map(c -> {
					return MonthlyWorkTimeSetSha.of(cid, employeeId, LaborWorkTypeAttr.REGULAR_LABOR, 
							YearMonth.of(year, c.getMonth()), 
							MonthlyLaborTime.of(new MonthlyEstimateTime(c.getMonthlyTime())));
				})
				.collect(Collectors.toList());
	}
	
	public List<MonthlyWorkTimeSetSha> defor(String cid) {
		return deforLaborSetting
				.getStatutorySetting().stream().map(c -> {
					return MonthlyWorkTimeSetSha.of(cid, employeeId, LaborWorkTypeAttr.DEFOR_LABOR, 
							YearMonth.of(year, c.getMonth()), 
							MonthlyLaborTime.of(new MonthlyEstimateTime(c.getMonthlyTime())));
				})
				.collect(Collectors.toList());
	}
	
	public List<MonthlyWorkTimeSetSha> flex(String cid) {
		List<MonthlyWorkTimeSetSha> flex = new ArrayList<>();
		
		for (int i = 0; i <= 12; i++ ) {
			
			val sta = find(flexSetting.getStatutorySetting(), i);
			val spe = find(flexSetting.getSpecifiedSetting(), i);
			val wat = find(flexSetting.getWeekAveSetting(), i);
			
			if (sta.isPresent() || spe.isPresent() || wat.isPresent()) {
				flex.add(MonthlyWorkTimeSetSha.of(cid, employeeId,
											LaborWorkTypeAttr.FLEX, 
											YearMonth.of(year, i), 
											MonthlyLaborTime.of(new MonthlyEstimateTime(0))));
			}
		}
		
		return flex;
	}
	
	private Optional<MonthlyUnitDto> find(List<MonthlyUnitDto> s, int m) {
		
		return s.stream().filter(f -> f.getMonth() == m).findFirst();
	}

	public RegularLaborTimeSha regurlarLabor(String cid) {
		
		return RegularLaborTimeSha.of(cid, employeeId,
					new WeeklyUnit(new WeeklyTime(regularLaborTime.getWeeklyTime().getTime()), 
									EnumAdaptor.valueOf(regularLaborTime.getWeeklyTime().getStart(), WeekStart.class)),
					new DailyUnit(new TimeOfDay(regularLaborTime.getDailyTime().getDailyTime())));
	}
	
	public DeforLaborTimeSha deforLabor(String cid) {
		
		return DeforLaborTimeSha.of(cid, employeeId,
					new WeeklyUnit(new WeeklyTime(transLaborTime.getWeeklyTime().getTime()), 
									EnumAdaptor.valueOf(transLaborTime.getWeeklyTime().getStart(), WeekStart.class)),
					new DailyUnit(new TimeOfDay(transLaborTime.getDailyTime().getDailyTime())));
	}
}
