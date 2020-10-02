package nts.uk.ctx.at.schedule.dom.shift.management.schedulecounter;

import java.util.HashMap;
import java.util.Map;

import nts.uk.shr.com.enumcommon.NotUseAtr;

public class OnePersonCounterRegisterHelper {
	
	public static OnePersonCounter createObject_timeCounting_allNot () {
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
	
	public static OnePersonCounter createObject_timeCounting_allUse () {
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

}
