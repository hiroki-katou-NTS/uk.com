package nts.uk.ctx.at.function.dom.alarm.alarmlist;

import java.util.Collections;
import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;

@Data
@NoArgsConstructor
public class PeriodByAlarmCategory {
	
	int category;
	
	String name;
	
	GeneralDate startDate;
	
	GeneralDate endDate;
	
	public List<GeneralDate> getListDate() {
		List<GeneralDate> result = Collections.emptyList();
		for(GeneralDate date = GeneralDate.localDate(startDate.localDate()); date.before(endDate); date.addDays(1)) {
			result.add(date);
		}
		return result;
	}
	
}
