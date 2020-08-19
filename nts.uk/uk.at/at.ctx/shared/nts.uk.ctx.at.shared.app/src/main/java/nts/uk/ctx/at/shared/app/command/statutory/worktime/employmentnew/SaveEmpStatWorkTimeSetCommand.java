/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.command.statutory.worktime.employmentnew;

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
import nts.uk.ctx.at.shared.dom.statutory.worktime.monunit.MonthlyWorkTimeSetEmp;
import nts.uk.ctx.at.shared.dom.statutory.worktime.week.DailyUnit;
import nts.uk.ctx.at.shared.dom.statutory.worktime.week.WeekStart;
import nts.uk.ctx.at.shared.dom.statutory.worktime.week.WeeklyUnit;
import nts.uk.ctx.at.shared.dom.statutory.worktime.week.defor.DeforLaborTimeEmp;
import nts.uk.ctx.at.shared.dom.statutory.worktime.week.regular.RegularLaborTimeEmp;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.EmploymentCode;

/**
 * The Class SaveComStatWorkTimeSetCommand.
 */
@Getter
@Setter
@NoArgsConstructor
public class SaveEmpStatWorkTimeSetCommand{

	/** The year. */
	private int year;
	
	/** The employee id. */
	private String employmentCode;

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

	public List<MonthlyWorkTimeSetEmp> regular(String cid) {
		return normalSetting
				.getStatutorySetting().stream().map(c -> {
					return MonthlyWorkTimeSetEmp.of(cid, new EmploymentCode(employmentCode), LaborWorkTypeAttr.REGULAR_LABOR, 
							YearMonth.of(year, c.getMonth()), 
							MonthlyLaborTime.of(new MonthlyEstimateTime(c.getMonthlyTime())));
				})
				.collect(Collectors.toList());
	}
	
	public List<MonthlyWorkTimeSetEmp> defor(String cid) {
		return deforLaborSetting
				.getStatutorySetting().stream().map(c -> {
					return MonthlyWorkTimeSetEmp.of(cid, new EmploymentCode(employmentCode), LaborWorkTypeAttr.DEFOR_LABOR, 
							YearMonth.of(year, c.getMonth()), 
							MonthlyLaborTime.of(new MonthlyEstimateTime(c.getMonthlyTime())));
				})
				.collect(Collectors.toList());
	}
	
	public List<MonthlyWorkTimeSetEmp> flex(String cid) {
		List<MonthlyWorkTimeSetEmp> flex = new ArrayList<>();
		
		for (int i = 0; i <= 12; i++ ) {
			
			val sta = find(flexSetting.getStatutorySetting(), i);
			val spe = find(flexSetting.getSpecifiedSetting(), i);
			val wat = find(flexSetting.getWeekAveSetting(), i);
			
			if (sta.isPresent() || spe.isPresent() || wat.isPresent()) {
				flex.add(MonthlyWorkTimeSetEmp.of(cid, new EmploymentCode(employmentCode),
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

	public RegularLaborTimeEmp regurlarLabor(String cid) {
		
		return RegularLaborTimeEmp.of(cid, new EmploymentCode(employmentCode),
					new WeeklyUnit(new WeeklyTime(regularLaborTime.getWeeklyTime().getTime()), 
									EnumAdaptor.valueOf(regularLaborTime.getWeeklyTime().getStart(), WeekStart.class)),
					new DailyUnit(new TimeOfDay(regularLaborTime.getDailyTime().getDailyTime())));
	}
	
	public DeforLaborTimeEmp deforLabor(String cid) {
		
		return DeforLaborTimeEmp.of(cid, new EmploymentCode(employmentCode),
					new WeeklyUnit(new WeeklyTime(transLaborTime.getWeeklyTime().getTime()), 
									EnumAdaptor.valueOf(transLaborTime.getWeeklyTime().getStart(), WeekStart.class)),
					new DailyUnit(new TimeOfDay(transLaborTime.getDailyTime().getDailyTime())));
	}
}
