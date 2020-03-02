package approve.employee;

import java.util.List;

import lombok.Data;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.service.registerapproval.AppTypes;

@Data
public class EmployeeApproverRootQuery {
	private int sysAtr;
	
	private GeneralDate baseDate;
	
	private List<EmployeeQuery> lstEmpIds;
	
	private List<AppTypes> lstApps;
}
