package nts.uk.ctx.workflow.app.command.agent;

import lombok.Value;
/**
 * 
 * @author phongtq
 *
 */

@Value
public class DeleteAgentCommand {
	
	private String companyId;
	
	private String employeeId;
	
	private String requestId;
	
}
