package nts.uk.ctx.workflow.app.command.agent;

import lombok.Value;
import nts.arc.time.GeneralDate;

@Value
public class DeleteAgentCommand {
	
	private String companyId;
	
	private String employeeId;
	
	private GeneralDate startDate;
	
}
