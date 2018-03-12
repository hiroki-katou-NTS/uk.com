package nts.uk.ctx.at.function.dom.alarm.alarmlist;

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
}
