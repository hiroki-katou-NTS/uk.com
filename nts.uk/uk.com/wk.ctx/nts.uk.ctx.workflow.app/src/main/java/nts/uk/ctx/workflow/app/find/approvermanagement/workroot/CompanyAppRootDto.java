package nts.uk.ctx.workflow.app.find.approvermanagement.workroot;

import java.util.List;

import lombok.Value;
@Value
public class CompanyAppRootDto {
	private ComApprovalRootDto company;
	private List<ApprovalPhaseDto> lstAppPhase;
}
