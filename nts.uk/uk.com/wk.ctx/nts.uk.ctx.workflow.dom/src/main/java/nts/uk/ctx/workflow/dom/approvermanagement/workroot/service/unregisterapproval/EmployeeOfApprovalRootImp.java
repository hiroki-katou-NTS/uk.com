package nts.uk.ctx.workflow.dom.approvermanagement.workroot.service.unregisterapproval;

import java.util.List;

import javax.ejb.Stateless;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.service.output.ApprovalRootCommonOutput;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.service.output.EmployeeUnregisterOutput;
@Stateless
public class EmployeeOfApprovalRootImp implements EmployeeOfApprovalRoot{

	@Override
	public List<EmployeeUnregisterOutput> lstEmpApprovalRoot(String companyId, 
			List<ApprovalRootCommonOutput> lstRootInfor,
			EmployeeUnregisterOutput empInfor, 
			int rootType, 
			int appType, 
			GeneralDate baseDate) {
		//check ドメインモデル「会社別就業承認ルート」「職場別就業承認ルート」「個人別就業承認ルート」
		boolean isExist = false;
		for(ApprovalRootCommonOutput item : lstRootInfor) {
			if(item.getApprovalId().equals(empInfor.getSId())) {
				isExist = true;
				break;
			}
		}
		//lstRootInfor.stream().anyMatch(x -> x.getApprovalId().equals(empInfor.getSId()))
		if(isExist) {
			
		}
		
		
		return null;
	}

}
