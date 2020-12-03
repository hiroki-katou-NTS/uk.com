package nts.uk.ctx.at.function.dom.alarm.alarmlist;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.function.dom.alarm.AlarmCategory;
/**
 *カテゴリ別期間 
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PeriodByAlarmCategory {
	
	AlarmCategory category;
	
	String name;
	
	GeneralDate startDate;
	
	GeneralDate endDate;
	
	int period36Agreement;
	
	public List<GeneralDate> getListDate() {
		List<GeneralDate> result = new ArrayList<GeneralDate>();
		GeneralDate date = GeneralDate.localDate(startDate.localDate());
		while (date.beforeOrEquals(endDate)) {
			result.add(date);
			date = date.addDays(1);
		}
		return result;
	}
	
}
