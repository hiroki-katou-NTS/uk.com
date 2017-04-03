package nts.uk.ctx.basic.app.command.organization.position;

import lombok.Getter;
import lombok.Setter;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.basic.dom.organization.position.JobHistory;


@Getter
@Setter
public class DeleteHistoryCommand {


	private String historyId;
	private String startDate;
	private String oldStartDate;
}
