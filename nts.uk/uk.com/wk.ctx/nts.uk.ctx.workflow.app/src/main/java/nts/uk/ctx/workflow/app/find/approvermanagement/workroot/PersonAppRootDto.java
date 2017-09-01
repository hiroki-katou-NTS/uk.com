package nts.uk.ctx.workflow.app.find.approvermanagement.workroot;

import java.util.List;

import lombok.Value;
@Value
public class PersonAppRootDto {
	private PsApprovalRootDto person;
	private List<ApprovalPhaseDto> lstAppPhase;
}
