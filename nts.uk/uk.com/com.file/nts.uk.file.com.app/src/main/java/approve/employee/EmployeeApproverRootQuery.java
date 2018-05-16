package approve.employee;

import java.util.List;

import lombok.Value;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.service.registerapproval.AppTypes;

@Value
public class EmployeeApproverRootQuery {
	private GeneralDate baseDate;
	private List<EmployeeQuery> lstEmpIds;
	//list AppType print
	private List<AppTypes> lstApps;
}
