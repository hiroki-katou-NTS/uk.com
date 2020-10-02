package nts.uk.ctx.at.schedule.dom.shift.management.schedulecounter;

import java.util.HashMap;
import java.util.Map;

import nts.uk.shr.com.enumcommon.NotUseAtr;

public class WorkplaceCounterRegisterHelper {
	
	public static WorkplaceCounter createObject_all_notUse () {
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
	
	public static WorkplaceCounter createObject_allUse () {
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

}
