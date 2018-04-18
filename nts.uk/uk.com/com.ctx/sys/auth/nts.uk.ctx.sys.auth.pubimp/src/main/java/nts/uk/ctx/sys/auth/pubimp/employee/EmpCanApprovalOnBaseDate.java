package nts.uk.ctx.sys.auth.pubimp.employee;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.sys.auth.dom.algorithm.CanApprovalOnBaseDateService;

public class EmpCanApprovalOnBaseDate implements CanApprovalOnBaseDateService {

	@Override
	public boolean canApprovalOnBaseDate(String companyId, String employeeID, GeneralDate date) {
		// TODO Auto-generated method stub
		return false;
	}

}
