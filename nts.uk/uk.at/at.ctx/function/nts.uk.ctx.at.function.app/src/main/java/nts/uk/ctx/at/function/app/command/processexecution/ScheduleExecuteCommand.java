package nts.uk.ctx.at.function.app.command.processexecution;

import lombok.Data;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDateTime;
//import nts.arc.time.calendar.period.DatePeriod;

@Data
@NoArgsConstructor
public class ScheduleExecuteCommand {
	/* 会社ID */
	private String companyId;
	
	/* コード */
	private String execItemCd;
	
	private String scheduleId;
	
	private GeneralDateTime nextDate;
	
	private String companyCd;
	
	@Data
	public static class ForBatchServer {
		private ScheduleExecuteCommand command;
		private String contexts;
	}
}
