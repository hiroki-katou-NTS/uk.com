package nts.uk.ctx.workflow.dom.approvermanagement.workroot.service.unregisterapproval;

import java.util.List;

import javax.ejb.Stateless;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.service.output.EmployeeUnregisterOutput;

@Stateless
public class EmployeeUnregisterApprovalRootImp implements  EmployeeUnregisterApprovalRoot{

	@Override
	public List<EmployeeUnregisterOutput> lstEmployeeUnregister(String companyId, GeneralDate baseDate) {
		// TODO Auto-generated method stub
		return null;
	}

}
