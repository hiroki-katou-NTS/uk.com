/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.command.statutory.worktime.workplacenew;

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
import nts.uk.ctx.at.shared.app.command.statutory.worktime.common.MonthlyUnitDto;
import nts.uk.ctx.at.shared.app.command.statutory.worktime.common.WkpDeforLaborSettingDto;
import nts.uk.ctx.at.shared.app.command.statutory.worktime.common.WkpFlexSettingDto;
import nts.uk.ctx.at.shared.app.command.statutory.worktime.common.WkpNormalSettingDto;
import nts.uk.ctx.at.shared.app.command.statutory.worktime.common.WkpWorkingTimeSettingDto;
import nts.uk.ctx.at.shared.dom.common.MonthlyEstimateTime;
import nts.uk.ctx.at.shared.dom.common.TimeOfDay;
import nts.uk.ctx.at.shared.dom.common.WeeklyTime;
import nts.uk.ctx.at.shared.dom.statutory.worktime.monunit.MonthlyLaborTime;
import nts.uk.ctx.at.shared.dom.statutory.worktime.monunit.MonthlyWorkTimeSet.LaborWorkTypeAttr;
import nts.uk.ctx.at.shared.dom.statutory.worktime.monunit.MonthlyWorkTimeSetWkp;
import nts.uk.ctx.at.shared.dom.statutory.worktime.week.DailyUnit;
import nts.uk.ctx.at.shared.dom.statutory.worktime.week.WeekStart;
import nts.uk.ctx.at.shared.dom.statutory.worktime.week.WeeklyUnit;
import nts.uk.ctx.at.shared.dom.statutory.worktime.week.defor.DeforLaborTimeWkp;
import nts.uk.ctx.at.shared.dom.statutory.worktime.week.regular.RegularLaborTimeWkp;

/**
 * The Class SaveComStatWorkTimeSetCommand.
 */
@Getter
@Setter
@NoArgsConstructor
public class SaveWkpStatWorkTimeSetCommand{

	/** The year. */
	private int year;
	
	/** The employee id. */
	private String workplaceId;

	/** The normal setting. */
	private WkpNormalSettingDto normalSetting;

	/** The flex setting. */
	private WkpFlexSettingDto flexSetting;

	/** The defor labor setting. */
	private WkpDeforLaborSettingDto deforLaborSetting;

	/** The regular labor time. */
	private WkpWorkingTimeSettingDto regularLaborTime;

	/** The trans labor time. */
	private WkpWorkingTimeSettingDto transLaborTime;

	public List<MonthlyWorkTimeSetWkp> regular(String cid) {
		return normalSetting
				.getStatutorySetting().stream().map(c -> {
					return MonthlyWorkTimeSetWkp.of(cid, workplaceId, LaborWorkTypeAttr.REGULAR_LABOR, 
							YearMonth.of(year, c.getMonth()), 
							MonthlyLaborTime.of(new MonthlyEstimateTime(c.getMonthlyTime())));
				})
				.collect(Collectors.toList());
	}
	
	public List<MonthlyWorkTimeSetWkp> defor(String cid) {
		return deforLaborSetting
				.getStatutorySetting().stream().map(c -> {
					return MonthlyWorkTimeSetWkp.of(cid, workplaceId, LaborWorkTypeAttr.DEFOR_LABOR, 
							YearMonth.of(year, c.getMonth()), 
							MonthlyLaborTime.of(new MonthlyEstimateTime(c.getMonthlyTime())));
				})
				.collect(Collectors.toList());
	}
	
	public List<MonthlyWorkTimeSetWkp> flex(String cid) {
		List<MonthlyWorkTimeSetWkp> flex = new ArrayList<>();
		
		for (int i = 0; i <= 12; i++ ) {
			
			val sta = find(flexSetting.getStatutorySetting(), i);
			val spe = find(flexSetting.getSpecifiedSetting(), i);
			val wat = find(flexSetting.getWeekAveSetting(), i);
			
			if (sta.isPresent() || spe.isPresent() || wat.isPresent()) {
				flex.add(MonthlyWorkTimeSetWkp.of(cid, workplaceId,
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

	public RegularLaborTimeWkp regurlarLabor(String cid) {
		
		return RegularLaborTimeWkp.of(cid, workplaceId,
					new WeeklyUnit(new WeeklyTime(regularLaborTime.getWeeklyTime().getTime()), 
									EnumAdaptor.valueOf(regularLaborTime.getWeeklyTime().getStart(), WeekStart.class)),
					new DailyUnit(new TimeOfDay(regularLaborTime.getDailyTime().getDailyTime())));
	}
	
	public DeforLaborTimeWkp deforLabor(String cid) {
		
		return DeforLaborTimeWkp.of(cid, workplaceId,
					new WeeklyUnit(new WeeklyTime(transLaborTime.getWeeklyTime().getTime()), 
									EnumAdaptor.valueOf(transLaborTime.getWeeklyTime().getStart(), WeekStart.class)),
					new DailyUnit(new TimeOfDay(transLaborTime.getDailyTime().getDailyTime())));
	}
}
