package nts.uk.ctx.workflow.dom.approvermanagement.workroot.service.registerapproval;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.ApplicationType;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.CompanyApprovalRoot;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.CompanyApprovalRootRepository;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.EmploymentRootAtr;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.PersonApprovalRoot;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.PersonApprovalRootRepository;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.WorkplaceApprovalRoot;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.WorkplaceApprovalRootRepository;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.service.output.WpApproverAsAppOutput;

public class EmployeeRegisterApprovalRootImpl implements EmployeeRegisterApprovalRoot {
	@Inject
	private CompanyApprovalRootRepository comRootRepository;
	@Inject
	private WorkplaceApprovalRootRepository wpRootRepository;
	@Inject
	private PersonApprovalRootRepository psRootRepository;
	@Override
	public Map<String, WpApproverAsAppOutput> lstEmps(String companyID, GeneralDate baseDate, List<String> lstEmpIds,
			EmploymentRootAtr rootAtr, List<ApplicationType> lstApps) {
		
		Map<String, WpApproverAsAppOutput> appOutput = new HashMap<>();
		
		//ドメインモデル「会社別就業承認ルート」を取得する(lấy dữ liệu domain 「会社別就業承認ルート」)
		List<CompanyApprovalRoot> lstComs = comRootRepository.findByBaseDateOfCommon(companyID, baseDate); 
		//ドメインモデル「職場別就業承認ルート」を取得する(lấy dữ liệu domain 「職場別就業承認ルート」)
		List<WorkplaceApprovalRoot> lstWps = wpRootRepository.findAllByBaseDate(companyID, baseDate);
		//ドメインモデル「個人別就業承認ルート」を取得する(lấy dữ liệu domain「個人別就業承認ルート」)
		List<PersonApprovalRoot> lstPss = psRootRepository.findAllByBaseDate(companyID, baseDate);
		//選択する対象社員リストを先頭から最後までループする(loop list nhan vien da chon tu dau den cuoi)
		for(String empId: lstEmpIds) {
			//選択する申請対象をループする(loop theo loại don xin da chon)
			for(ApplicationType appType: lstApps) {
				//ループ中の承認ルート対象が共通ルート が false の場合(loại đơn xin đang xử lý loop : 共通ルート  = false)
				if(rootAtr != EmploymentRootAtr.COMMON) {
					
				}
			}
		}
		
		
		// TODO Auto-generated method stub
		return null;
	}

}
