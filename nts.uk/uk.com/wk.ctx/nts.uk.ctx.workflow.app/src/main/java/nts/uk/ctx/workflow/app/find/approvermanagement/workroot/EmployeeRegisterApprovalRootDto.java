package nts.uk.ctx.workflow.app.find.approvermanagement.workroot;

import java.util.List;

import lombok.Data;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.service.registerapproval.AppTypes;
@Data
public class EmployeeRegisterApprovalRootDto {
	public GeneralDate baseDate;
	public List<String> lstEmpIds;
	public List<AppTypes> lstApps;
}
