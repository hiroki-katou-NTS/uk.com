package nts.uk.ctx.at.record.app.command.dailyperform.breaktime;

import lombok.Builder;
import lombok.Getter;
import nts.arc.time.GeneralDate;

@Getter
@Builder
public class RemoveBreakTimeByWorkTypeChangeCommand {

	private String employeeId;
	
	private GeneralDate workingDate;
	
	private String workTypeCode;
}
