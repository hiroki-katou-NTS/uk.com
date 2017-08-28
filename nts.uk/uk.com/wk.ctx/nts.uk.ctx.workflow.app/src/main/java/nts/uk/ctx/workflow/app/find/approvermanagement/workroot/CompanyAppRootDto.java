package nts.uk.ctx.workflow.app.find.approvermanagement.workroot;

import java.util.List;

import lombok.Value;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.CompanyApprovalRoot;
@Value
public class CompanyAppRootDto {
	private CompanyApprovalRoot company;
	private List<ApprovalPhaseDto> lstAppPhase;
}
