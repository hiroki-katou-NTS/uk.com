package nts.uk.ctx.workflow.app.find.approvermanagement.workroot;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.arc.time.GeneralDate;
@Data
@AllArgsConstructor
public class MasterApproverRootDto {
	private GeneralDate baseDate;
	private boolean chkCompany;
	private boolean chkWorkplace;
	private boolean chkPerson;
}
