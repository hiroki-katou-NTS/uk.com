package nts.uk.ctx.workflow.dom.approvermanagement.workroot.service;

import java.util.List;

import javax.ejb.Stateless;

import nts.arc.time.GeneralDate;
@Stateless
public class EmployeeOfApprovalRootImp implements EmployeeOfApprovalRoot{

	@Override
	public List<EmployeeUnregisterDto> lstEmpApprovalRoot(String companyId, 
			List<ApprovalRootCommonDto> lstRootInfor,
			EmployeeUnregisterDto empInfor, 
			int rootType, 
			int appType, 
			GeneralDate baseDate) {
		//check ドメインモデル「会社別就業承認ルート」「職場別就業承認ルート」「個人別就業承認ルート」
		boolean isExist = false;
		for(ApprovalRootCommonDto item : lstRootInfor) {
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
