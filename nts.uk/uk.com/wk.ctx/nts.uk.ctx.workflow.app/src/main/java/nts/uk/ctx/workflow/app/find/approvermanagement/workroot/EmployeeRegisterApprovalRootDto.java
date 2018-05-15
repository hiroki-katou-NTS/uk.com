package nts.uk.ctx.workflow.app.find.approvermanagement.workroot;

import java.util.List;

import lombok.Data;
import nts.arc.time.GeneralDate;
@Data
public class EmployeeRegisterApprovalRootDto {
	public GeneralDate baseDate;
	public List<String> lstEmpIds;
	public int rootAtr;
	public List<Integer> lstApps;
}
