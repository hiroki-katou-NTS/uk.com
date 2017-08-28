package nts.uk.ctx.workflow.app.find.approvermanagement.workroot;

import java.util.List;

import lombok.Value;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.PersonApprovalRoot;
@Value
public class PersonAppRootDto {
	private PersonApprovalRoot person;
	private List<ApprovalPhaseDto> lstAppPhase;
}
