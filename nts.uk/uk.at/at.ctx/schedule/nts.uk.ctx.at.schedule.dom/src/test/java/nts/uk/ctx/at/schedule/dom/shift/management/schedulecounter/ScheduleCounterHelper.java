package nts.uk.ctx.at.schedule.dom.shift.management.schedulecounter;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import nts.uk.ctx.at.schedule.dom.shift.management.schedulecounter.laborcostandtime.LaborCostAndTime;
import nts.uk.shr.com.enumcommon.NotUseAtr;

public class ScheduleCounterHelper {
	
	public static OnePersonCounter createPersonCounter_timeCounting_allNot () {
		Map<OnePersonCounterCategory, NotUseAtr> map = new HashMap<>();
		map.put(OnePersonCounterCategory.MONTHLY_EXPECTED_SALARY, NotUseAtr.USE);
		map.put(OnePersonCounterCategory.CUMULATIVE_ESTIMATED_SALARY, NotUseAtr.NOT_USE);
		map.put(OnePersonCounterCategory.STANDARD_WORKING_HOURS_COMPARISON, NotUseAtr.USE);
		map.put(OnePersonCounterCategory.WORKING_HOURS, NotUseAtr.NOT_USE);
		map.put(OnePersonCounterCategory.NIGHT_SHIFT_HOURS, NotUseAtr.USE);
		map.put(OnePersonCounterCategory.WEEKS_HOLIDAY_DAYS, NotUseAtr.NOT_USE);
		map.put(OnePersonCounterCategory.ATTENDANCE_HOLIDAY_DAYS, NotUseAtr.USE);
		
		map.put(OnePersonCounterCategory.TIMES_COUNTING_1, NotUseAtr.NOT_USE);
		map.put(OnePersonCounterCategory.TIMES_COUNTING_2, NotUseAtr.NOT_USE);
		map.put(OnePersonCounterCategory.TIMES_COUNTING_3, NotUseAtr.NOT_USE);
		
		return new OnePersonCounter(map);
	}
	
	public static OnePersonCounter createPersonCounter_timeCounting_allUse () {
		Map<OnePersonCounterCategory, NotUseAtr> map = new HashMap<>();
		
		map.put(OnePersonCounterCategory.MONTHLY_EXPECTED_SALARY, NotUseAtr.USE);
		map.put(OnePersonCounterCategory.CUMULATIVE_ESTIMATED_SALARY, NotUseAtr.NOT_USE);
		map.put(OnePersonCounterCategory.STANDARD_WORKING_HOURS_COMPARISON, NotUseAtr.USE);
		map.put(OnePersonCounterCategory.WORKING_HOURS, NotUseAtr.NOT_USE);
		map.put(OnePersonCounterCategory.NIGHT_SHIFT_HOURS, NotUseAtr.USE);
		map.put(OnePersonCounterCategory.WEEKS_HOLIDAY_DAYS, NotUseAtr.NOT_USE);
		map.put(OnePersonCounterCategory.ATTENDANCE_HOLIDAY_DAYS, NotUseAtr.USE);
		
		map.put(OnePersonCounterCategory.TIMES_COUNTING_1, NotUseAtr.USE);
		map.put(OnePersonCounterCategory.TIMES_COUNTING_2, NotUseAtr.USE);
		map.put(OnePersonCounterCategory.TIMES_COUNTING_3, NotUseAtr.USE);
		
		return new OnePersonCounter(map);
	}
	
	public static WorkplaceCounter createWorkplaceCounter_all_notUse () {
		Map<WorkplaceCounterCategory, NotUseAtr> map = new HashMap<>();
		
		map.put(WorkplaceCounterCategory.LABOR_COSTS_AND_TIME, NotUseAtr.NOT_USE);
		map.put(WorkplaceCounterCategory.EXTERNAL_BUDGET, NotUseAtr.NOT_USE);
		map.put(WorkplaceCounterCategory.TIMES_COUNTING, NotUseAtr.NOT_USE);
		map.put(WorkplaceCounterCategory.WORKTIME_PEOPLE, NotUseAtr.NOT_USE);
		map.put(WorkplaceCounterCategory.TIMEZONE_PEOPLE, NotUseAtr.NOT_USE);
		map.put(WorkplaceCounterCategory.EMPLOYMENT_PEOPLE, NotUseAtr.NOT_USE);
		map.put(WorkplaceCounterCategory.CLASSIFICATION_PEOPLE, NotUseAtr.NOT_USE);
		map.put(WorkplaceCounterCategory.POSITION_PEOPLE, NotUseAtr.NOT_USE);
		
		return new WorkplaceCounter(map);
	}
	
	public static WorkplaceCounter createWorkplaceCounter_allUse () {
		Map<WorkplaceCounterCategory, NotUseAtr> map = new HashMap<>();
		
		map.put(WorkplaceCounterCategory.LABOR_COSTS_AND_TIME, NotUseAtr.USE);
		map.put(WorkplaceCounterCategory.EXTERNAL_BUDGET, NotUseAtr.USE);
		map.put(WorkplaceCounterCategory.TIMES_COUNTING, NotUseAtr.USE);
		map.put(WorkplaceCounterCategory.WORKTIME_PEOPLE, NotUseAtr.USE);
		map.put(WorkplaceCounterCategory.TIMEZONE_PEOPLE, NotUseAtr.USE);
		map.put(WorkplaceCounterCategory.EMPLOYMENT_PEOPLE, NotUseAtr.USE);
		map.put(WorkplaceCounterCategory.CLASSIFICATION_PEOPLE, NotUseAtr.USE);
		map.put(WorkplaceCounterCategory.POSITION_PEOPLE, NotUseAtr.USE);
		
		return new WorkplaceCounter(map);
	}
	
	public static LaborCostAndTime createLaborCostAndTime_with_budget(boolean hasBudget) {
		return new LaborCostAndTime(
				NotUseAtr.USE, 
				NotUseAtr.USE, 
				NotUseAtr.USE, 
				hasBudget ? Optional.of(NotUseAtr.USE) : Optional.empty());
	}
	
}
