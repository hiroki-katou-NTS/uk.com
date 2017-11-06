package nts.uk.ctx.workflow.app.find.approvermanagement.workroot;

import lombok.Value;
import nts.arc.time.GeneralDate;
@Value
public class MasterApproverRootDto {
	private GeneralDate baseDate;
	private boolean chkCompany;
	private boolean chkWorkplace;
	private boolean chkPerson;
}
