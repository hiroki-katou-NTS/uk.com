package nts.uk.ctx.at.record.pub.workrecord.erroralarm;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.time.GeneralDate;

@Getter
@AllArgsConstructor
public class DailyPerformanceErrorExport {

	private GeneralDate date;
	
	private boolean error;
}
