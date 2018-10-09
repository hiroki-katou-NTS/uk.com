package nts.uk.ctx.at.function.dom.alarm.alarmlist;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PeriodByAlarmCategory {
	
	int category;
	
	String name;
	
	GeneralDate startDate;
	
	GeneralDate endDate;
	
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
