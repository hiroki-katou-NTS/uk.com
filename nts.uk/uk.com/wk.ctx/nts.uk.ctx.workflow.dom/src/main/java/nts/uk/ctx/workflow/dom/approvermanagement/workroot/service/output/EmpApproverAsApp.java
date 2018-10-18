package nts.uk.ctx.workflow.dom.approvermanagement.workroot.service.output;

import java.util.List;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.service.registerapproval.AppTypes;
import nts.uk.ctx.workflow.dom.service.output.ErrorFlag;

@Data
@AllArgsConstructor
public class EmpApproverAsApp {
	/** 社員の情報 */
	private EmployeeApproverOutput employeeInfor;
	/**key: appType, values: list phase */
	private Map<AppTypes, List<ApproverAsAppInfor>> mapAppType;
	/** list AppTypes */
	private List<AppTypes> lstAppTypes;	
}
