package nts.uk.ctx.workflow.app.find.approvermanagement.workroot;

import java.util.List;

import lombok.Value;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.WorkplaceApprovalRoot;
@Value
public class WorkPlaceAppRootDto {
	private WorkplaceApprovalRoot workplace;
	private List<ApprovalPhaseDto> lstAppPhase;
}
