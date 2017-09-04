package nts.uk.ctx.workflow.app.find.approvermanagement.workroot;

import java.util.List;

import lombok.Value;
@Value
public class WorkPlaceAppRootDto {
	private WpApprovalRootDto workplace;
	private List<ApprovalPhaseDto> lstAppPhase;
}
