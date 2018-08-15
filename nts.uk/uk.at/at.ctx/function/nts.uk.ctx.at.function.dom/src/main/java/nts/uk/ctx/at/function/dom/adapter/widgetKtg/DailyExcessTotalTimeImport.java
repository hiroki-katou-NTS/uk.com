package nts.uk.ctx.at.function.dom.adapter.widgetKtg;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.arc.time.GeneralDate;

@AllArgsConstructor
@Data
public class DailyExcessTotalTimeImport {

	private GeneralDate date;
	
	private AttendanceTimeImport overTime;
	
	private AttendanceTimeImport holidayWorkTime;
	
	private AttendanceTimeImport flexOverTime;
	
	private AttendanceTimeImport excessMidNightTime;
}
